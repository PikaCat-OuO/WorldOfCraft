package com.pikacat.event;

import com.pikacat.Clock;
import com.pikacat.EventSystem;
import com.pikacat.building.Building;
import com.pikacat.warrior.Warrior;

public class WarriorMarchEvent extends Event {
    private Warrior redWaitWarrior;
    private boolean gameEnd = false;

    public WarriorMarchEvent() {
        super(10);
    }

    public Warrior getRedWaitWarrior() {
        return redWaitWarrior;
    }

    public void setRedWaitWarrior(Warrior redWaitWarrior) {
        this.redWaitWarrior = redWaitWarrior;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    @Override
    public void handle() {
        this.redWaitWarrior = null;
        EventSystem.notify(Warrior.class, this);
        EventSystem.notify(Building.class, this);
        if (this.gameEnd) {
            EventSystem.notify(Clock.class, new EndEvent());
        }
        super.handle();
    }
}
