package com.pikacat.weapon;

import com.pikacat.warrior.Warrior;

public class Magic extends Weapon {
    public Magic() {
        this.setWeaponNo(3);
        this.setName("magic");
    }

    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        int lifeSteal = Math.min(victim.getLife(), attacker.getAttack() * 3 / 10);
        attacker.setLife(attacker.getLife() + lifeSteal);
        victim.setLife(victim.getLife() - attacker.getAttack() * 3 / 10);
        return false;
    }

    @Override
    public boolean canDamage(int attack) {
        return true;
    }
}