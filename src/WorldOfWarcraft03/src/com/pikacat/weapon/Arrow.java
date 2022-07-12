package com.pikacat.weapon;

import com.pikacat.warrior.Warrior;

public class Arrow extends Weapon {

    private int durable = 2;

    public Arrow() {
        this.setWeaponNo(2);
        this.setName("arrow");
    }

    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        victim.setLife(victim.getLife() - attacker.getAttack() * 3 / 10);
        this.durable -= 1;
        return this.durable > 0;
    }

    @Override
    public boolean canDamage(int attack) {
        return true;
    }

    @Override
    public int compareTo(Weapon weapon) {
        int result = super.compareTo(weapon);
        if (0 == result){
            return this.durable - ((Arrow) weapon).durable;
        } else {
            return result;
        }
    }

    @Override
    public int robCompare(Weapon weapon) {
        int result = super.robCompare(weapon);
        if (0 == result) {
            return ((Arrow) weapon).durable - this.durable;
        } else {
            return result;
        }
    }
}
