package com.pikacat.builder;

import com.pikacat.warrior.Dragon;

public class DragonBuilder extends WarriorBuilder {

    public DragonBuilder() {
        super("dragon");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Dragon dragon = new Dragon();
        dragon.setMorale((double) (elements - this.getLife()) / this.getLife());
        this.setWarrior(dragon);
    }

}
