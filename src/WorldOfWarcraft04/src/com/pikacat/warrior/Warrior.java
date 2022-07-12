package com.pikacat.warrior;

import com.pikacat.EventSystem;
import com.pikacat.GameObject;
import com.pikacat.building.City;
import com.pikacat.weapon.Weapon;
import com.pikacat.weapon.WeaponFactory;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Warrior extends GameObject {
    private int number;
    private int life;
    private int attack;
    private final List<Weapon> weaponList;
    private City city;

    public Warrior() {
        EventSystem.register(this);
        this.weaponList = new LinkedList<>();
    }

    public void sortWeapon() {
        Collections.sort(this.weaponList);
    }

    public boolean isAlive() {
        return this.life > 0;
    }

    // 判断这个武士能否造成伤害
    public boolean canDamage() {
        // 要求这个武士没死并且拥有武器
        if (this.isAlive() && !this.weaponList.isEmpty()) {
            for (Weapon weapon : this.weaponList) {
                if (weapon.canDamage(this.attack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void attack(Warrior victim) {
        // 拿出第一把武器
        Weapon weapon = this.weaponList.remove(0);
        // 使用这把武器
        if (weapon.use(this, victim)) {
            // 如果武器还可以继续被使用
            this.addWeapon(weapon);
        }
    }

    public void die() {
        // 从建筑中脱离
        this.city.detach(this);

        // 从事件系统中移除
        EventSystem.unregister(this);
    }

    // 从别的武士手中获取武器
    public void robWarrior(Warrior warrior) {
        List<Weapon> weapons = robWeapon(warrior);
        this.weaponList.addAll(weapons);
        warrior.weaponList.removeAll(weapons);
    }

    public List<Weapon> robWeapon(Warrior warrior) {
        // 获得对方的武器列表
        List<Weapon> victimWeapon = warrior.getWeaponList();

        // 如果没有武器直接返回
        if (victimWeapon.isEmpty()) {
            return new LinkedList<>();
        }

        // 对武器排序
        victimWeapon.sort((Weapon::robCompare));

        // 计算最多可以获取几把武器
        int maxRob = 10 - this.getWeaponList().size();

        // 尽可能多的获取武器
        return victimWeapon.stream().limit(maxRob).collect(Collectors.toList());
    }

    public Map<String, Integer> reportWeapon() {
        // 统计武器的数量
        List<String> weaponName = WeaponFactory.getWeaponName();
        Map<String, Integer> weaponCount = new HashMap<>();
        for (String name : weaponName) {
            weaponCount.put(name, 0);
        }
        for (Weapon weapon : this.weaponList) {
            weaponCount.put(weapon.getName(), weaponCount.get(weapon.getName()) + 1);
        }

        return weaponCount;
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void addWeapon(Weapon weapon) {
        this.weaponList.add(weapon);
    }

    public String getBornMessage() {
        return String.format("%s %s %d born", this.getSide(), this.getName(), this.number);
    }

    public String getWinMessage() {
        return null;
    }

    @Override
    public String toString() {
        return this.getSide() + " " + this.getName() + " " + this.getNumber();
    }
}