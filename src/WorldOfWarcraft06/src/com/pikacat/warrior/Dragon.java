package com.pikacat.warrior;

import com.pikacat.Logger;
import com.pikacat.Settings;
import com.pikacat.event.Event;
import com.pikacat.event.FightEvent;

public class Dragon extends Warrior {
    private double morale;

    public void setMorale(double morale) {
        this.morale = morale;
    }

    @Override
    public String getBornMessage() {
        return super.getBornMessage() + "\nIts morale is " + Settings.numberFormat.format(this.morale);
    }

    @Override
    public void afterFight(Event event, Warrior warrior) {
        super.afterFight(event, warrior);
        // 视敌人的情况增加或减少士气值
        this.morale += warrior.isAlive() ? -0.2 : 0.2;

        if (this.isAlive() && this == ((FightEvent) event).getActiveAttacker() && this.morale > 0.8) {
            Logger.logYellMessage(event);
        }
    }
}
