package com.pikacat.builder;

import com.pikacat.warrior.Dragon;
import com.pikacat.warrior.Warrior;

public class DragonBuilder extends WarriorBuilder {

    @Override
    public Warrior buildWarrior() {
        Dragon dragon = new Dragon();
        dragon.setLife(this.getLife());
        dragon.setAttack(this.getAttack());
        dragon.setName("dragon");
        return dragon;
    }

}
