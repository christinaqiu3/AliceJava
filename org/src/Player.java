public class Player extends Creature {

    private int numPotions;

    private int numKills;

    public Player() {
        setHealth(30);
        setAttackDmg(3);

        setPosx(2);
        setPosy(4);
        numPotions = 0;
        numKills = 0;
    }

    public Player(java.util.List<String> p) {
        setHealth(Integer.parseInt(p.remove(0)));
        System.out.println(this.getHealth());
        setAttackDmg(Integer.parseInt(p.remove(0)));
        setPosx(Integer.parseInt(p.remove(0)));
        setPosy(Integer.parseInt(p.remove(0)));
        numPotions = Integer.parseInt(p.remove(0));
        numKills = Integer.parseInt(p.remove(0));
    }

    public String getTreasure(int i) {
        switch (i) {
            case 0:
                return "You got nothing!";
            case 1:
                setAttackDmg(getAttackDmg() + 3);
                return "You got a stronger weapon, you will now deal " + getAttackDmg()
                        + " damage!";
            case 2:
                numPotions += 1;
                return "You got a health potion, you now have " + numPotions + " potions!";
            case 3:
                setHealth(getHealth() - 1);
                return "You fell into a trap! -1 health!";
            default:
                break;
        }
        return "null";
    }

    public String usePotion() {
        if (numPotions > 0) {
            setHealth(getHealth() + 5);
            numPotions--;
            return "You used a health potion, +5 health!";
        } else {
            return "You have no health potions to use!";
        }
    }

    public String damage() {
        setHealth(Math.max(getHealth() - Room.monster.getAttackDmg(), 0));
        return "" + getHealth();
    }

    public int getNumPotions() {
        return numPotions;
    }

    public int getNumKills() {
        return numKills;
    }

    public int increaseKills() {
        numKills = getNumKills() + 1;
        return numKills;
    }
}
