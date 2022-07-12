package com.pikacat.builder;

import com.pikacat.warrior.Warrior;
import com.pikacat.warrior.Wolf;

public class WolfBuilder extends WarriorBuilder {

    public WolfBuilder() {
        super("wolf");
    }

    @Override
    public Warrior buildWarrior(int elements, int number) {
        Wolf wolf = new Wolf();
        super.assignValue(number, wolf);
        return wolf;
    }

}
