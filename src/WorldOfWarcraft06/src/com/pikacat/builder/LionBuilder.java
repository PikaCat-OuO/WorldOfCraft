package com.pikacat.builder;

import com.pikacat.warrior.Lion;

public class LionBuilder extends WarriorBuilder {

    public LionBuilder() {
        super("lion");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Lion lion = new Lion();
        lion.setLoyalty(elements - this.getLife());
        this.setWarrior(lion);
    }

}
