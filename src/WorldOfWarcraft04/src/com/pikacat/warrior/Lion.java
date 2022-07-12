package com.pikacat.warrior;

import com.pikacat.Logger;
import com.pikacat.Settings;
import com.pikacat.event.Event;
import com.pikacat.event.LionRunEvent;
import com.pikacat.event.WarriorMarchEvent;

public class Lion extends Warrior {
    private int loyalty;

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    @Override
    public String getBornMessage() {
        return super.getBornMessage() + "\nIts loyalty is " + this.loyalty;
    }

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof LionRunEvent) {
            if (this.loyalty <= 0) {
                Logger.logLionRunAwayEvent(event, this);
                this.die();
            }
        } else if (event instanceof WarriorMarchEvent) {
            this.loyalty -= Settings.ROYALTY_DECAY;
        }
    }
}
