package com.pikacat.warrior;

import com.pikacat.EventSystem;
import com.pikacat.GameObject;
import com.pikacat.building.Building;
import com.pikacat.building.Headquarter;
import com.pikacat.event.Event;
import com.pikacat.event.FightEvent;
import com.pikacat.weapon.*;

public abstract class Warrior extends GameObject {
    private int number;
    private int life;
    private int attack;

    private final WeaponPack weaponPack = new WeaponPack();

    // 该武士所属的司令部
    private Headquarter headquarter;
    private Building building;

    // 是否由于被箭射击而死
    private boolean isDeadOfArrow = false;

    public Warrior() {
        EventSystem.register(this);
    }

    // AOP的思想
    public void beforeFight() {

    }

    public void afterFight(Event event, Warrior warrior) {
        // 打赢了，如果是蓝方的武士，直接奖励，如果是红方的武士，则等下再奖励
        if (!warrior.isAlive()) {
            if (this.getSide().equals("blue")) {
                this.getPrize();
            } else {
                FightEvent fightEvent = (FightEvent) event;
                fightEvent.addWaitingWarrior(this);
            }
        }
    }

    public void getPrize() {
        // 打赢了 奖赏8个生命值
        int lifeElements = this.headquarter.getLifeElements();
        if (lifeElements >= 8) {
            this.headquarter.setLifeElements(lifeElements - 8);
            this.life += 8;
        }
    }

    public void attack(Warrior victim) {
        // 攻击对方
        victim.setLife(victim.getLife() - this.attack);
        // 使用Sword攻击对方
        this.weaponPack.use(Sword.class, this, victim);
    }

    public void fightBack(Warrior victim) {
        // 攻击对方
        victim.setLife(victim.getLife() - this.attack / 2);
        // 使用Sword攻击对方
        this.weaponPack.use(Sword.class, this, victim);
    }

    // 评估是否使用炸弹
    // 1代表自己死亡，2代表敌方死亡
    public int evaluate(Warrior warrior) {
        int myLife = this.getLife();
        int enemyLife = warrior.getLife();
        Sword sword = ((Sword) this.getWeaponPack().getWeapon(Sword.class));
        enemyLife -= this.attack;
        if (null != sword) {
            enemyLife -= sword.getAttack();
        }

        // 敌人反击
        if (enemyLife > 0 && !(warrior instanceof Ninja)) {
            sword = ((Sword) warrior.getWeaponPack().getWeapon(Sword.class));
            myLife -= warrior.attack / 2;
            if (null != sword) {
                myLife -= sword.getAttack();
            }
        }

        int result = 0;
        if (myLife <= 0) {
            result |= 1;
        }

        if (enemyLife <= 0) {
            result |= 2;
        }

        return result;
    }

    public boolean isAlive() {
        return this.life > 0;
    }

    public void shotArrow(Warrior warrior) {
        this.weaponPack.use(Arrow.class, this, warrior);
    }

    public void useBomb(Warrior warrior) {
        this.weaponPack.use(Bomb.class, this, warrior);
    }

    public boolean hasWeapon(Class<? extends Weapon> weaponClass) {
        return this.weaponPack.hasWeapon(weaponClass);
    }

    @Override
    public void detach() {
        this.headquarter = null;
        this.building = null;
    }

    public void die() {
        // 从建筑中脱离
        this.building.detach(this);
        this.detach();

        // 从事件系统中移除
        EventSystem.unregister(this);
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void addWeapon(WeaponPack weaponPack) {
        this.weaponPack.addWeapon(weaponPack);
    }

    public void addWeapon(Weapon weapon) {
        this.weaponPack.addWeapon(weapon);
    }

    public WeaponPack getWeaponPack() {
        return weaponPack;
    }

    public Headquarter getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(Headquarter headquarter) {
        this.headquarter = headquarter;
    }

    public boolean isDeadOfArrow() {
        return isDeadOfArrow;
    }

    public void setDeadOfArrow(boolean deadOfArrow) {
        isDeadOfArrow = deadOfArrow;
    }

    public String getBornMessage() {
        return String.format("%s %s %d born", this.getSide(), this.getName(), this.number);
    }

    @Override
    public String toString() {
        return this.getSide() + " " + this.getName() + " " + this.getNumber();
    }
}
