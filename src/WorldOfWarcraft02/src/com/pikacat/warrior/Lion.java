package com.pikacat.warrior;

public class Lion extends Warrior {
    private int loyalty;

    public int getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    @Override
    public String getBornMessage(int totalWarrior, String name) {
        return super.getBornMessage(totalWarrior, name) + "\nIt's loyalty is " + this.loyalty;
    }
}
