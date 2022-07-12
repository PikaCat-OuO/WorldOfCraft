package com.pikacat.building;

import com.pikacat.EventSystem;
import com.pikacat.GameObject;
import com.pikacat.Logger;
import com.pikacat.event.Event;
import com.pikacat.event.LionRunEvent;
import com.pikacat.event.ReportWeaponEvent;
import com.pikacat.warrior.Lion;
import com.pikacat.warrior.Warrior;

public class Building extends GameObject {
    private int number;
    private Warrior redWarrior;
    private Warrior blueWarrior;
    private Building formerBuilding;
    private Building nextBuilding;

    public Building() {
        EventSystem.register(this);
    }

    public Warrior getRedWarrior() {
        return redWarrior;
    }

    public void setRedWarrior(Warrior redWarrior) {
        this.redWarrior = redWarrior;
    }

    public Warrior getBlueWarrior() {
        return blueWarrior;
    }

    public void setBlueWarrior(Warrior blueWarrior) {
        this.blueWarrior = blueWarrior;
    }

    public Building getFormerBuilding() {
        return formerBuilding;
    }

    public void setFormerBuilding(Building formerBuilding) {
        this.formerBuilding = formerBuilding;
    }

    public Building getNextBuilding() {
        return nextBuilding;
    }

    public void setNextBuilding(Building nextBuilding) {
        this.nextBuilding = nextBuilding;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void detach(Warrior warrior) {
        if ("red".equals(warrior.getSide())) {
            this.redWarrior = null;
        } else {
            this.blueWarrior = null;
        }
    }

    @Override
    public void detach() {
        this.redWarrior = null;
        this.blueWarrior = null;
        this.formerBuilding = null;
        this.nextBuilding = null;
    }

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof ReportWeaponEvent) {
            if ("red".equals(((ReportWeaponEvent) event).getSide()) && null != this.redWarrior) {
                Logger.logWeaponEvent(event, this.redWarrior);
            } else if ("blue".equals(((ReportWeaponEvent) event).getSide()) && null != this.blueWarrior) {
                Logger.logWeaponEvent(event, this.blueWarrior);
            }
        } else if (event instanceof LionRunEvent) {
            if (this.getRedWarrior() instanceof Lion) {
                this.getRedWarrior().acceptEvent(event);
            }
            if (this.getBlueWarrior() instanceof Lion) {
                this.getBlueWarrior().acceptEvent(event);
            }
        }
    }
}
