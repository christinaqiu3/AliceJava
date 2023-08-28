package org.cis1200;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestDungeon {
    private DungeonBoard dg;

    @BeforeEach
    public void setUp() {
        final JLabel status = new JLabel("Running...");
        final JLabel status4 = new JLabel("0"); // kills
        final JLabel status5 = new JLabel("1");
        final JLabel status6 = new JLabel("30");
        final JLabel statusS = new JLabel("Started");
        dg = new DungeonBoard(status, status4, status5, status6, statusS);
    }

    @Test
    public void testPlayerDmg() {
        DungeonBoard.player = new Player();
        DungeonBoard.room = new Room();
        Room.monster = new Monster();
        Room.monster.setAttackDmg(5);
        assertEquals(25, Integer.parseInt(DungeonBoard.player.damage()));
    }

    @Test
    public void testPlayerDmgNegative() {
        DungeonBoard.player = new Player();
        DungeonBoard.room = new Room();
        Room.monster = new Monster();
        Room.monster.setAttackDmg(5);
        DungeonBoard.player.setHealth(3);
        assertEquals(0, Integer.parseInt(DungeonBoard.player.damage()));
    }

    @Test
    public void testPlayerUseNoPotion() {
        DungeonBoard.player = new Player();
        assertEquals("You have no health potions to use!", DungeonBoard.player.usePotion());
    }

    @Test
    public void testPlayerUsePotion() {
        DungeonBoard.player = new Player();
        DungeonBoard.player.getTreasure(2);
        assertEquals(
                "You used a health potion, you now have 35 health!", DungeonBoard.player.usePotion()
        );
        assertEquals("You have no health potions to use!", DungeonBoard.player.usePotion());
    }

    @Test
    public void testPlayerGetWeapon() {
        DungeonBoard.player = new Player();
        DungeonBoard.player.setAttackDmg(17);
        assertEquals(
                "You got a stronger weapon, you will now deal 20 damage!",
                DungeonBoard.player.getTreasure(1)
        );
    }

    @Test
    public void testPlayerGetTrap() {
        DungeonBoard.player = new Player();
        assertEquals(
                "You fell into a trap! You now have 29 health!",
                DungeonBoard.player.getTreasure(3)
        );
    }

    @Test
    public void testPlayerGetNothing() {
        DungeonBoard.player = new Player();
        assertEquals(
                "You got nothing!",
                DungeonBoard.player.getTreasure(0)
        );
    }

    @Test
    public void testMonsterDmg() {
        DungeonBoard.player = new Player();
        DungeonBoard.room = new Room();
        Room.monster = new Monster();
        Room.monster.setHealth(10);
        DungeonBoard.player.setAttackDmg(1);
        assertEquals("The enemy now has 9 health!", Room.monster.damage());
    }

    @Test
    public void testMonsterDmgDead() {
        DungeonBoard.player = new Player();
        DungeonBoard.room = new Room();
        Room.monster = new Monster();
        DungeonBoard.player.setAttackDmg(100);
        assertEquals("The enemy is now dead!", Room.monster.damage());
    }

    @Test
    public void testTickLose() {
        DungeonBoard.player = new Player();
        DungeonBoard.player.setHealth(0);
        DungeonBoard.playing = true;
        dg.tick();
        assertEquals("You lose!", DungeonBoard.status.getText());
    }

    @Test
    public void testTickWin() {
        DungeonBoard.player = new Player();
        DungeonBoard.room = new Room(6);
        DungeonBoard.playing = true;
        dg.tick();
        assertEquals("You win!", DungeonBoard.status.getText());
    }

    @Test
    public void testSaving() {
        DungeonBoard.room = new Room(1);
        dg.quickSave();
        DungeonBoard dgCopy = dg;
        DungeonBoard.room = new Room(6);
        dg.loadSave();
        assertEquals(dgCopy, dg);
    }

    @Test
    public void testReset() {
        DungeonBoard.room = new Room(6);
        dg.reset();
        assertEquals(1, DungeonBoard.room.getRmLvl());
    }

    @Test
    public void testNextRoom() {
        DungeonBoard.room = new Room(2);
        DungeonBoard.player = new Player();
        DungeonBoard.room.playerMove(0, -4);
        assertEquals(3, DungeonBoard.room.getRmLvl());
        assertEquals("You have fallen into the next room!", DungeonBoard.statusS.getText());
    }

    @Test
    public void testNumTreasures() {
        DungeonBoard.room = new Room(2);
        assertTrue(DungeonBoard.room.getTreasure() >= 0 && DungeonBoard.room.getTreasure() <= 2);
    }

}
