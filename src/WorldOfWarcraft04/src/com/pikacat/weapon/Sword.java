package com.pikacat.weapon;

import com.pikacat.warrior.Warrior;

public class Sword extends Weapon {
    public Sword() {
        this.setWeaponNo(0);
        this.setName("sword");
    }

    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        victim.setLife(victim.getLife() - attacker.getAttack() * 2 / 10);
        return true;
    }

    @Override
    public boolean canDamage(int attack) {
        return attack * 2 / 10 > 0;
    }
}