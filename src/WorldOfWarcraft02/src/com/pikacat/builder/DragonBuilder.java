package com.pikacat.builder;

import com.pikacat.warrior.Dragon;
import com.pikacat.warrior.Warrior;
import com.pikacat.weapon.WeaponFactory;

public class DragonBuilder extends WarriorBuilder {

    public DragonBuilder() {
        super("dragon");
    }

    @Override
    public Warrior buildWarrior(int elements, int number) {
        Dragon dragon = new Dragon();
        super.assignValue(number, dragon);
        dragon.addWeapon(WeaponFactory.getWeapon(number % 3));
        dragon.setMorale((double) (elements - this.getLife()) / this.getLife());
        return dragon;
    }

}
