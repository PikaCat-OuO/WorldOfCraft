package com.pikacat.builder;

import com.pikacat.GameObject;
import com.pikacat.warrior.Warrior;

// 基于建造者模式的武士建造者
public abstract class WarriorBuilder extends GameObject{
    private int life;
    private int attack;
    private Warrior warrior;

    public WarriorBuilder(String name) {
        this.setName(name);
    }

    public abstract void constructWarrior(int elements, int number);

    public Warrior buildWarrior(int elements, int number) {
        if (elements < this.getLife()) {
            return null;
        }
        constructWarrior(elements, number);
        this.warrior.setLife(this.life);
        this.warrior.setAttack(this.attack);
        this.warrior.setNumber(number);
        this.warrior.setName(this.getName());
        return this.warrior;
    }

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

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }
}
