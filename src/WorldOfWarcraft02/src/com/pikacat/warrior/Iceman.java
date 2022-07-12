package com.pikacat.warrior;

public class Iceman extends Warrior {

    @Override
    public String getBornMessage(int totalWarrior, String name) {
        return super.getBornMessage(totalWarrior, name) + "\nIt has a "+ this.getWeapon(0).getName();
    }
}
