package org.cis1200;

public class Monster extends Creature {

    private String linkedAttack;
    private String linkedText;

    public Monster() {
        setHealth((int) (Math.random() * 15));
        setAttackDmg((int) (Math.random() * 4) + 1);
        switch ((int) (Math.random() * 3)) {
            case 0:
                linkedAttack = "A";
                linkedText = "Mad Hatter";
                break;
            case 1:
                linkedAttack = "B";
                linkedText = "White Bunny";
                break;
            case 2:
                linkedAttack = "C";
                linkedText = "Cheshire Cat";
                break;
            default:
                break;
        }
    }

    public String damage() {
        setHealth(Math.max(0, getHealth() - DungeonBoard.player.getAttackDmg()));
        if (getHealth() == 0) {
            return "The " + linkedText + " is now dead!";
        }
        return "The " + linkedText + " now has " + getHealth() + " health!";
    }

    public String getLinkedAttack() {
        return linkedAttack;
    }

    public String getLinkedText() {
        return "You have met the " + linkedText + "! ";
    }

    public String getMonster() {
        return linkedText;
    }

}
