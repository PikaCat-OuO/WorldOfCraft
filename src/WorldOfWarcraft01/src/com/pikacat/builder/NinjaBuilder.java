package com.pikacat.builder;

import com.pikacat.warrior.Ninja;
import com.pikacat.warrior.Warrior;

public class NinjaBuilder extends WarriorBuilder {

    @Override
    public Warrior buildWarrior() {
        Ninja ninja = new Ninja();
        ninja.setLife(this.getLife());
        ninja.setAttack(this.getAttack());
        ninja.setName("ninja");
        return ninja;
    }

}
