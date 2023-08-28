package org.cis1200;

public abstract class Creature {
    private int health;
    private int attackDmg;
    private int posx;
    private int posy;

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttackDmg(int attackDmg) {
        this.attackDmg = attackDmg;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackDmg() {
        return attackDmg;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public String damage() {
        return "this will be overridden";
    }
}
