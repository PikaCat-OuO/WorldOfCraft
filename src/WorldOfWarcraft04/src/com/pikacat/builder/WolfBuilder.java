package com.pikacat.builder;

import com.pikacat.warrior.Wolf;

public class WolfBuilder extends WarriorBuilder {

    public WolfBuilder() {
        super("wolf");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Wolf wolf = new Wolf();
        this.setWarrior(wolf);
    }

}