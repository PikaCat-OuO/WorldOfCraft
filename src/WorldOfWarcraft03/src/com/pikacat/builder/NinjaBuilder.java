package com.pikacat.builder;

import com.pikacat.warrior.Ninja;
import com.pikacat.weapon.WeaponFactory;

public class NinjaBuilder extends WarriorBuilder {

    public NinjaBuilder() {
        super("ninja");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Ninja ninja = new Ninja();
        ninja.addWeapon(WeaponFactory.getWeapon(number % 3));
        ninja.addWeapon(WeaponFactory.getWeapon((number + 1) % 3));
        this.setWarrior(ninja);
    }

}
