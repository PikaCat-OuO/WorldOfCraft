package com.pikacat.weapon;

import com.pikacat.warrior.Warrior;

public class Bomb extends Weapon {
    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        attacker.die();
        victim.die();
        return false;
    }

    @Override
    public String toString() {
        return "bomb";
    }
}
