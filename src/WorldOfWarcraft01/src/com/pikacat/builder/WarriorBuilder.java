package com.pikacat.builder;

import com.pikacat.warrior.Warrior;

public abstract class WarriorBuilder {
    private int life;
    private int attack;
    public abstract Warrior buildWarrior();

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
