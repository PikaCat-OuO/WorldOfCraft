package com.pikacat.builder;

import com.pikacat.warrior.Iceman;
import com.pikacat.warrior.Warrior;
import com.pikacat.weapon.WeaponFactory;

public class IcemanBuilder extends WarriorBuilder {

    public IcemanBuilder() {
        super("iceman");
    }

    @Override
    public Warrior buildWarrior(int elements, int number) {
        Iceman iceman = new Iceman();
        super.assignValue(number, iceman);
        iceman.addWeapon(WeaponFactory.getWeapon(number % 3));
        return iceman;
    }

}
