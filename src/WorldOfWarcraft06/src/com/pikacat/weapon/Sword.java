package com.pikacat.weapon;

import com.pikacat.warrior.Warrior;

public class Sword extends Weapon {
    private int attack;

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        victim.setLife(victim.getLife() - this.attack);
        // 攻击后武器变钝
        this.attack = this.attack * 8 / 10;
        return this.attack > 0;
    }

    @Override
    public String toString() {
        return String.format("sword(%d)", this.attack);
    }
}
