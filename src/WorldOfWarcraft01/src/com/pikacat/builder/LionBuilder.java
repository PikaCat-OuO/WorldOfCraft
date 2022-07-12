package com.pikacat.builder;

import com.pikacat.warrior.Lion;
import com.pikacat.warrior.Warrior;

public class LionBuilder extends WarriorBuilder {

    @Override
    public Warrior buildWarrior() {
        Lion lion = new Lion();
        lion.setLife(this.getLife());
        lion.setAttack(this.getAttack());
        lion.setName("lion");
        return lion;
    }

}
