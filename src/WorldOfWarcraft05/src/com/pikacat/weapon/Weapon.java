package com.pikacat.weapon;

import com.pikacat.GameObject;
import com.pikacat.warrior.Warrior;

// 基于原型模式的武器模型
public abstract class Weapon extends GameObject implements Cloneable {
    public abstract boolean use(Warrior attacker, Warrior victim);

    @Override
    protected Weapon clone() throws CloneNotSupportedException {
        return (Weapon) super.clone();
    }
}
