package com.pikacat.builder;

import com.pikacat.warrior.Ninja;

public class NinjaBuilder extends WarriorBuilder {

    public NinjaBuilder() {
        super("ninja");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        this.setWarrior(new Ninja());
        equipWeapon(number % 3);
        equipWeapon((number + 1) % 3);
    }

}
