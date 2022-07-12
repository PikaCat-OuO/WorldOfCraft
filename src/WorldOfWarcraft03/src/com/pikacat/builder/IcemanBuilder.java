package com.pikacat.builder;

import com.pikacat.warrior.Iceman;
import com.pikacat.weapon.WeaponFactory;

public class IcemanBuilder extends WarriorBuilder {

    public IcemanBuilder() {
        super("iceman");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Iceman iceman = new Iceman();
        iceman.addWeapon(WeaponFactory.getWeapon(number % 3));
        this.setWarrior(iceman);
    }

}
