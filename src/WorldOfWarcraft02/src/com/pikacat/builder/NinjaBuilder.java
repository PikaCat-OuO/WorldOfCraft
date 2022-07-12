package com.pikacat.builder;

import com.pikacat.warrior.Ninja;
import com.pikacat.warrior.Warrior;
import com.pikacat.weapon.WeaponFactory;

public class NinjaBuilder extends WarriorBuilder {

    public NinjaBuilder() {
        super("ninja");
    }

    @Override
    public Warrior buildWarrior(int elements, int number) {
        Ninja ninja = new Ninja();
        super.assignValue(number, ninja);
        ninja.addWeapon(WeaponFactory.getWeapon(number % 3));
        ninja.addWeapon(WeaponFactory.getWeapon((number + 1) % 3));
        return ninja;
    }

}
