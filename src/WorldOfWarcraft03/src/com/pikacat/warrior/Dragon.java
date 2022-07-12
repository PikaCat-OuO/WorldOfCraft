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
    public String getWinMessage() {
        return this + " yelled";
    }
}
