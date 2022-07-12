package com.pikacat.warrior;

public class Ninja extends Warrior {
    @Override
    public String getBornMessage(int totalWarrior, String name) {
        return super.getBornMessage(totalWarrior, name) + "\nIt has a " +
                this.getWeapon(0).getName() + " and a " +
                this.getWeapon(1).getName();
    }
}
