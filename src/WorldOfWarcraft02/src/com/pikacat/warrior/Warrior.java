package com.pikacat.warrior;

import com.pikacat.GameObject;
import com.pikacat.weapon.Weapon;

import java.util.LinkedList;
import java.util.List;

public abstract class Warrior extends GameObject {
    private int number;
    private int life;
    private int attack;
    private final List<Weapon> weaponList;

    public Warrior() {
        this.weaponList = new LinkedList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public void addWeapon(Weapon weapon) {
        this.weaponList.add(weapon);
    }

    public Weapon getWeapon(int number) {
        return this.weaponList.get(number);
    }

    public String getBornMessage(int totalWarrior, String name) {
        return String.format("%s %s %d born with strength %d,%d %s in %s",
                this.getSide(), this.getName(), this.number, this.life, totalWarrior, this.getName(), name);
    }
}
