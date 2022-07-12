package com.pikacat.warrior;

import com.pikacat.event.Event;

public class Wolf extends Warrior {
    @Override
    public void afterFight(Event event, Warrior warrior) {
        super.afterFight(event, warrior);
        if (this.isAlive() && !warrior.isAlive()) {
            // 如果击杀了敌方武士，就把敌方武士的武器拿过来
            addWeapon(warrior.getWeaponPack());
        }
    }
}
