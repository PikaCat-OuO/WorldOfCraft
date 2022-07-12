package com.pikacat.builder;

import com.pikacat.warrior.Dragon;
import com.pikacat.weapon.WeaponFactory;

public class DragonBuilder extends WarriorBuilder {

    public DragonBuilder() {
        super("dragon");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Dragon dragon = new Dragon();
        dragon.addWeapon(WeaponFactory.getWeapon(number % 3));
        this.setWarrior(dragon);
    }

}