package com.pikacat.event;

import com.pikacat.Clock;
import com.pikacat.EventSystem;
import com.pikacat.building.City;
import com.pikacat.warrior.Warrior;

public class WarriorMarchEvent extends Event {
    private Warrior redWaitWarrior;
    private boolean gameEnd = false;

    public Warrior getRedWaitWarrior() {
        return redWaitWarrior;
    }

    public void setRedWaitWarrior(Warrior redWaitWarrior) {
        this.redWaitWarrior = redWaitWarrior;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    @Override
    public boolean handle(int time) {
        this.redWaitWarrior = null;
        if (10 == time % 60) {
            // 武士推进事件
            this.setHour(time / 60);
            this.setMinute(10);
            EventSystem.notify(Warrior.class, this);
            EventSystem.notify(City.class, this);
            if (this.gameEnd) {
                EventSystem.notify(Clock.class, new EndEvent());
            }
            return true;
        } else {
            return false;
        }
    }
}
