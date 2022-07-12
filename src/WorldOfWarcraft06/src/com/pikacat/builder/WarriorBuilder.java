package com.pikacat.builder;

import com.pikacat.GameObject;
import com.pikacat.warrior.Warrior;
import com.pikacat.weapon.Sword;
import com.pikacat.weapon.Weapon;
import com.pikacat.weapon.WeaponFactory;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

// 基于建造者模式的武士建造者
public abstract class WarriorBuilder extends GameObject{
    private int life;
    private int attack;
    private Warrior warrior;
    private final List<SimpleEntry<Integer, Integer>> initialWeapons = new ArrayList<>();

    public WarriorBuilder(String name) {
        this.setName(name);
    }

    public abstract void constructWarrior(int elements, int number);

    public void addInitialWeapon(int add, int mod) {
        this.initialWeapons.add(new SimpleEntry<>(add, mod));
    }

    public void equipWeapons(int number) {
        for (SimpleEntry<Integer, Integer> entry : this.initialWeapons) {
            Weapon weapon = WeaponFactory.getWeapon(this.getAttack(), (number + entry.getKey()) % entry.getValue());
            // 如果剑的攻击力为0，直接消失
            if (!(weapon instanceof Sword) || ((Sword) weapon).getAttack() != 0) {
                this.warrior.addWeapon(weapon);
            }
        }
    }

    public Warrior buildWarrior(int elements, int number) {
        if (elements < this.getLife()) {
            return null;
        }
        constructWarrior(elements, number);
        equipWeapons(number);
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
