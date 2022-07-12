package com.pikacat.weapon;

import com.pikacat.Settings;
import com.pikacat.warrior.Warrior;

public class Arrow extends Weapon {
    private int durable = 3;

    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        victim.setLife(victim.getLife() - Settings.ARROW_ATTACK);
        this.durable -= 1;
        return this.durable > 0;
    }

    @Override
    public String toString() {
        return String.format("arrow(%d)", this.durable);
    }
}
