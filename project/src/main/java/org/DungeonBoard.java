import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.*;

public class DungeonBoard extends JPanel {

    public static Player player;
    public static Room room;
    public static boolean battlemode = false;
    public static boolean playing = false; // whether the game is running
    public static JLabel status; // Current status text, i.e. "Running..."
    public static JLabel status4; // kill counter
    public static JLabel status5; // lvl
    public static JLabel status6; // health
    public static JLabel statusS; // second label

    // Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 500;

    public DungeonBoard(
            JLabel status, JLabel status4, JLabel status5, JLabel status6, JLabel statusS
    ) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(100, e -> tick());
        timer.start();

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && !battlemode && playing) {
                    room.playerMove(-1, 0);
                    whenMoved();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !battlemode && playing) {
                    room.playerMove(1, 0);
                    whenMoved();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !battlemode && playing) {
                    room.playerMove(0, 1);
                    whenMoved();
                } else if (e.getKeyCode() == KeyEvent.VK_UP && !battlemode && playing) {
                    room.playerMove(0, -1);
                    whenMoved();
                } else if (e.getKeyCode() == KeyEvent.VK_A && battlemode && playing) {
                    if (Room.monster.getLinkedAttack().equals("A")
                            && Room.monster.getHealth() > 0) {
                        statusS.setText(Room.monster.damage());
                    }
                    whenAttack(Room.monster.getLinkedAttack().equals("A"));
                } else if (e.getKeyCode() == KeyEvent.VK_B && battlemode && playing) {
                    if (Room.monster.getLinkedAttack().equals("B")
                            && Room.monster.getHealth() > 0) {
                        statusS.setText(Room.monster.damage());
                    }
                    whenAttack(Room.monster.getLinkedAttack().equals("B"));
                } else if (e.getKeyCode() == KeyEvent.VK_C && battlemode && playing) {
                    if (Room.monster.getLinkedAttack().equals("C")
                            && Room.monster.getHealth() > 0) {
                        statusS.setText(Room.monster.damage());
                    }
                    whenAttack(Room.monster.getLinkedAttack().equals("C"));
                } else if (e.getKeyCode() == KeyEvent.VK_H && !battlemode && playing) {
                    statusS.setText(player.usePotion());
                    status6.setText("" + player.getHealth());
                }

            }

        });
        DungeonBoard.status = status;
        DungeonBoard.status4 = status4;
        DungeonBoard.status5 = status5;
        DungeonBoard.status6 = status6;
        DungeonBoard.statusS = statusS;

    }

    void whenMoved() {
        if (player.getPosx() != 2 || player.getPosy() != 4) {
            statusS.setText(" ");
        }
    }

    void whenAttack(boolean b) {
        if (Room.monster.getHealth() > 0) {
            status6.setText(player.damage());
            if (b) {
                statusS.setText(
                        statusS.getText() +
                                " You were damaged!" //-" + Room.monster.getAttackDmg() + " health!
                );
            } else {
                statusS.setText(
                        " You were damaged!" //-" + Room.monster.getAttackDmg() + " health!
                );
            }
        } else {
            status4.setText("" + player.increaseKills());
            battlemode = false;
            status.setText("You can continue moving forward");
        }
    }

    public void reset() {
        player = new Player();
        room = new Room();

        playing = true;
        battlemode = false;
        status.setText("Running...");
        statusS.setText("Reseted");
        status4.setText("0");
        status5.setText("1");
        status6.setText("30");

        // Makes sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    String filePath = "files/save.csv";

    public void quickSave() {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;

        java.util.List<String> stringsToWrite = new ArrayList<>();
        stringsToWrite.add("" + player.getHealth());
        stringsToWrite.add("" + player.getAttackDmg());
        stringsToWrite.add("" + player.getPosx());
        stringsToWrite.add("" + player.getPosy());
        stringsToWrite.add("" + player.getNumPotions());
        stringsToWrite.add("" + player.getNumKills());

        stringsToWrite.add("" + room.getRmLvl());
        stringsToWrite.add("" + room.getTreasure());
        stringsToWrite.add("" + room.getNumEnemies());
        stringsToWrite.add("" + status.getText());
        stringsToWrite.add("" + status4.getText());
        stringsToWrite.add("" + status5.getText());
        stringsToWrite.add("" + status6.getText());
        stringsToWrite.add("" + statusS.getText());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                stringsToWrite.add("" + room.getRoom()[i][j]);
            }
        }
        stringsToWrite.add("" + battlemode);
        stringsToWrite.add("" + playing);

        try {
            bw = new BufferedWriter(new FileWriter(file));

            for (String s : stringsToWrite) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("error");
        }
        requestFocusInWindow();
    }

    public void loadSave() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            java.util.List<String> arr = new ArrayList<>();

            for (int i = 0; i < 41; i++) {
                arr.add(br.readLine());
            }

            player = new Player(arr);
            room = new Room(arr);
            battlemode = Boolean.parseBoolean(arr.remove(0));
            playing = Boolean.parseBoolean(arr.remove(0));
        } catch (IOException e) {
            System.out.println("error");
        }
        requestFocusInWindow();

    }

    public void tick() {

        if (playing) {

            // check for the game end conditions
            if (player.getHealth() <= 0) {
                playing = false;
                status.setText("You lose!");
            } else if (room.getRmLvl() > 5) {
                playing = false;
                status.setText("You win!");
            }

            // update the display
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        Color lp = new Color(160, 119, 202);
        Color dp = new Color(121, 65, 164);
        Color ddp = new Color(61, 18, 93);
        Color red = new Color(0, 0, 0);
        Color gold = new Color(239, 172, 50);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0) {
                    g.setColor(lp);
                } else {
                    g.setColor(dp);
                }
                g.fillRect(i * 100, j * 100, 100, 100);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int state = room.getCell(j, i);
                int l = 40;
                switch (state) {
                    case 0:
                        break;
                    case 1:
                        g.setColor(red);
                        g.fillOval(30 + 100 * j, 30 + 100 * i, l, l);
                        break;
                    case 2:
                        g.setColor(Color.white);
                        g.fillOval(30 + 100 * j, 30 + 100 * i, l, l);
                        break;
                    case 3:
                        g.setColor(gold);
                        g.fillRect(30 + 100 * j, 30 + 100 * i, l, l);
                        break;
                    case 4:
                        g.setColor(ddp);
                        g.fillOval(15 + 100 * j, 15 + 100 * i, 70, 70);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}