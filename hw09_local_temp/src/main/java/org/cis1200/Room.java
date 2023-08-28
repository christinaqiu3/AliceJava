package org.cis1200;

import javax.swing.*;

public class Room extends JPanel {
    private int numEnemies;
    private int numTreasure;
    private int rmLvl;

    public static Monster monster;

    // 0 = empty. 1 = enemy. 2 = player. 3 = treasure. 4 = door

    private int[][] room = new int[5][5];

    public Room() {
        createRoom(1);
    }

    public Room(int i) {
        createRoom(i);
    }

    public Room(java.util.List<String> p) {
        this.rmLvl = Integer.parseInt(p.remove(0));
        this.numTreasure = Integer.parseInt(p.remove(0));
        this.numEnemies = Integer.parseInt(p.remove(0));
        DungeonBoard.status.setText(p.remove(0));
        DungeonBoard.status4.setText(p.remove(0));
        DungeonBoard.status5.setText(p.remove(0));
        DungeonBoard.status6.setText(p.remove(0));
        DungeonBoard.statusS.setText(p.remove(0));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                room[i][j] = Integer.parseInt(p.remove(0));
            }
        }

    }

    public void createRoom(int i) {
        DungeonBoard.status.setText("Running...");
        DungeonBoard.status4.setText("" + DungeonBoard.player.getNumKills());
        DungeonBoard.status5.setText("" + i);

        room = new int[][] {
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 2 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 }
        };
        this.rmLvl = i;
        switch (i) {
            case 1:
                numEnemies = (int) (Math.random() * 3);
                numTreasure = (int) (Math.random() * 2);
                break;
            case 2:
                numEnemies = (int) (Math.random() * 3) + 3;
                numTreasure = (int) (Math.random() * 3);
                break;
            case 3:
                numEnemies = (int) (Math.random() * 3) + 6;
                numTreasure = (int) (Math.random() * 4);
                break;
            case 4:
                numEnemies = (int) (Math.random() * 3) + 9;
                numTreasure = (int) (Math.random() * 5);
                break;
            case 5:
                numEnemies = (int) (Math.random() * 3) + 12;
                numTreasure = (int) (Math.random() * 6);
                break;
            case 6:
                room = new int[][] {
                    { 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 2 },
                    { 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0 }
                };
                break;
            default:
                break;

        }

        int countE = 0;
        int countT = 0;
        while (countE < numEnemies) {
            int row = (int) (Math.random() * 5);
            int col = (int) (Math.random() * 5);
            if (room[row][col] == 0) {
                room[row][col] = 1;
                countE++;
            }
        }
        while (countT < numTreasure) {
            int row = (int) (Math.random() * 5);
            int col = (int) (Math.random() * 5);
            if (room[row][col] == 0) {
                room[row][col] = 3;
                countT++;
            }
        }
        if (i > 5) {
            DungeonBoard.status5.setText("END");
        }

    }

    public int getRmLvl() {
        return rmLvl;
    }

    public void playerMove(int row, int col) {
        int rp = DungeonBoard.player.getPosx();
        int cp = DungeonBoard.player.getPosy();
        int rn = DungeonBoard.player.getPosx() + row;
        int cn = DungeonBoard.player.getPosy() + col;
        if (rn >= 0 && rn < 5 && cn >= 0 && cn < 5) {
            switch (room[rn][cn]) {
                case 0:
                    room[rn][cn] = 2;
                    room[rp][cp] = 0;
                    DungeonBoard.player.setPosx(rn);
                    DungeonBoard.player.setPosy(cn);
                    DungeonBoard.status.setText("Running...");
                    break;
                case 1:
                    monster = new Monster();
                    room[rn][cn] = 2;
                    room[rp][cp] = 0;
                    DungeonBoard.player.setPosx(rn);
                    DungeonBoard.player.setPosy(cn);
                    DungeonBoard.battlemode = true;
                    DungeonBoard.status
                            .setText(
                                    monster.getLinkedText() + "Press " + monster.getLinkedAttack()
                                            + " to attack!"
                            );
                    break;
                case 3:
                    DungeonBoard.status
                            .setText(DungeonBoard.player.getTreasure((int) (Math.random() * 4)));
                    room[rn][cn] = 2;
                    room[rp][cp] = 0;
                    DungeonBoard.status6.setText("" + DungeonBoard.player.getHealth());
                    DungeonBoard.player.setPosx(rn);
                    DungeonBoard.player.setPosy(cn);
                    break;
                case 4:
                    DungeonBoard.statusS.setText("You have fallen into the next room!");
                    DungeonBoard.status5.setText("" + getRmLvl() + 1);
                    DungeonBoard.room = new Room(rmLvl + 1);
                    DungeonBoard.player.setPosx(2);
                    DungeonBoard.player.setPosy(4);
                    break;
                default:
                    break;
            }
        }
    }

    public int getTreasure() {
        return numTreasure;
    }

    public int getNumEnemies() {
        return numEnemies;
    }

    public int[][] getRoom() {
        return room.clone();
    }

    public int getCell(int r, int c) {
        return room[r][c];
    }

}
