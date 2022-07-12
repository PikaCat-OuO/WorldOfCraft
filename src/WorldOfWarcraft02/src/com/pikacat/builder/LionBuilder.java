package com.pikacat.builder;

import com.pikacat.warrior.Lion;
import com.pikacat.warrior.Warrior;

public class LionBuilder extends WarriorBuilder {

    public LionBuilder() {
        super("lion");
    }

    @Override
    public Warrior buildWarrior(int elements, int number) {
        Lion lion = new Lion();
        super.assignValue(number, lion);
        lion.setLoyalty(elements - this.getLife());
        return lion;
    }

}
