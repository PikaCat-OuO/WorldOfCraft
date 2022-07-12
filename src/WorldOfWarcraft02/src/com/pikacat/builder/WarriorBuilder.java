package com.pikacat.builder;

import com.pikacat.GameObject;
import com.pikacat.warrior.Warrior;

public abstract class WarriorBuilder extends GameObject {
    private int life;
    private int attack;
    private WarriorBuilder next;

    public WarriorBuilder(String name) {
        this.setName(name);
    }

    public WarriorBuilder findBuilder(int elements) {
        if (elements >= this.getLife()) return this;
        else return this.next.findBuilder(elements);
    }

    public void assignValue(int number, Warrior warrior) {
        warrior.setLife(this.life);
        warrior.setAttack(this.attack);
        warrior.setNumber(number);
        warrior.setName(this.getName());
    }

    public abstract Warrior buildWarrior(int elements, int number);

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

    public WarriorBuilder getNext() {
        return next;
    }

    public void setNext(WarriorBuilder next) {
        this.next = next;
    }
}
