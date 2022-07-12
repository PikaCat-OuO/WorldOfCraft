package com.pikacat.builder;

import com.pikacat.warrior.Lion;
import com.pikacat.weapon.WeaponFactory;

public class LionBuilder extends WarriorBuilder {

    public LionBuilder() {
        super("lion");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Lion lion = new Lion();
        lion.addWeapon(WeaponFactory.getWeapon(number % 3));
        lion.setLoyalty(elements - this.getLife());
        this.setWarrior(lion);
    }

}