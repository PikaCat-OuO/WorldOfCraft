package com.pikacat.builder;

import com.pikacat.warrior.Iceman;
import com.pikacat.warrior.Warrior;

public class IcemanBuilder extends WarriorBuilder {

    @Override
    public Warrior buildWarrior() {
        Iceman iceman = new Iceman();
        iceman.setLife(this.getLife());
        iceman.setAttack(this.getAttack());
        iceman.setName("iceman");
        return iceman;
    }

}
