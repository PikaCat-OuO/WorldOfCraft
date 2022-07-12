package com.pikacat.builder;

import com.pikacat.warrior.Warrior;
import com.pikacat.warrior.Wolf;

public class WolfBuilder extends WarriorBuilder {

    @Override
    public Warrior buildWarrior() {
        Wolf wolf = new Wolf();
        wolf.setLife(this.getLife());
        wolf.setAttack(this.getAttack());
        wolf.setName("wolf");
        return wolf;
    }

}
