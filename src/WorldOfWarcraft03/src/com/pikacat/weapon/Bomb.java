package com.pikacat.weapon;

import com.pikacat.warrior.Ninja;
import com.pikacat.warrior.Warrior;

public class Bomb extends Weapon {
    public Bomb() {
        this.setWeaponNo(1);
        this.setName("bomb");
    }

    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        // 不是Ninja自己要受伤
        if (!(attacker instanceof Ninja)) {
            attacker.setLife(attacker.getLife() - (attacker.getAttack() * 4 / 10) / 2);
        }
        victim.setLife(victim.getLife() - attacker.getAttack() * 4 / 10);
        return false;
    }

    @Override
    public boolean canDamage(int attack) {
        return true;
    }
}
