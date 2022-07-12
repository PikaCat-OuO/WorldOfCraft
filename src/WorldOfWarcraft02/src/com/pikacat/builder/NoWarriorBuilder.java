package com.pikacat.builder;

import com.pikacat.warrior.Warrior;

public class NoWarriorBuilder extends WarriorBuilder {

    public NoWarriorBuilder() {
        super("");
    }

    @Override
    public WarriorBuilder findBuilder(int elements) {
        if (elements < this.getLife()) return this;
        else return this.getNext().findBuilder(elements);
    }

    @Override
    public Warrior buildWarrior(int elements, int number) {
        return null;
    }

}
