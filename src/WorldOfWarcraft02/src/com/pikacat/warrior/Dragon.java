package com.pikacat.warrior;

public class Dragon extends Warrior {
    private double morale;

    public double getMorale() {
        return morale;
    }

    public void setMorale(double morale) {
        this.morale = morale;
    }

    @Override
    public String getBornMessage(int totalWarrior, String name) {
        return super.getBornMessage(totalWarrior, name) + "\nIt has a " +
                this.getWeapon(0).getName()
                + String.format(",and it's morale is %.2f", this.morale);
    }
}
