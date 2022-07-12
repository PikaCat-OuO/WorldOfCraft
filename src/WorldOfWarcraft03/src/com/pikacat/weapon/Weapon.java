package com.pikacat.weapon;

import com.pikacat.GameObject;
import com.pikacat.warrior.Warrior;

// 基于原型模式的武器模型
public abstract class Weapon extends GameObject implements Cloneable, Comparable<Weapon> {
    private int weaponNo;

    public abstract boolean use(Warrior attacker, Warrior victim);

    public int getWeaponNo() {
        return weaponNo;
    }

    public void setWeaponNo(int weaponNo) {
        this.weaponNo = weaponNo;
    }

    // 判断这把武器在某一个攻击力下能不能造成伤害
    public abstract boolean canDamage(int attack);

    @Override
    protected Weapon clone() throws CloneNotSupportedException {
        return (Weapon) super.clone();
    }

    @Override
    public int compareTo(Weapon weapon) {
        return this.weaponNo - weapon.weaponNo;
    }

    public int robCompare(Weapon weapon) {
        return this.weaponNo - weapon.weaponNo;
    }
}
