package com.pikacat.builder;

import com.pikacat.warrior.Iceman;

public class IcemanBuilder extends WarriorBuilder {

    public IcemanBuilder() {
        super("iceman");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        this.setWarrior(new Iceman());
    }

}
