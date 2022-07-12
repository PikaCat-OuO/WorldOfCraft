package com.pikacat.warrior;

import com.pikacat.Logger;
import com.pikacat.Settings;
import com.pikacat.building.Headquarter;
import com.pikacat.event.Event;
import com.pikacat.event.LionRunEvent;

public class Lion extends Warrior {
    private int loyalty;
    private int lifeBeforeFight;

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    @Override
    public void beforeFight() {
        super.beforeFight();
        this.lifeBeforeFight = this.getLife();
    }

    @Override
    public void afterFight(Event event, Warrior warrior) {
        super.afterFight(event, warrior);
        if (!this.isAlive() && warrior.isAlive() && !this.isDeadOfArrow()) {
            warrior.setLife(warrior.getLife() + this.lifeBeforeFight);
        } else if (warrior.isAlive()) {
            this.loyalty -= Settings.ROYALTY_DECAY;
        }
    }

    @Override
    public String getBornMessage() {
        return super.getBornMessage() + "\nIts loyalty is " + this.loyalty;
    }

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof LionRunEvent) {
            // 忠诚值低于0，并且不在敌方的司令部内
            if (this.loyalty <= 0) {
                if (this.getBuilding() instanceof Headquarter && !this.getSide().equals(this.getBuilding().getSide())) {
                    return;
                }
                Logger.logLionRunAwayEvent(event, this);
                this.die();
            }
        }
    }
}
