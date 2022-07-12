package com.pikacat.building;

import com.pikacat.EventSystem;
import com.pikacat.Logger;
import com.pikacat.builder.WarriorBuilder;
import com.pikacat.event.Event;
import com.pikacat.event.TimeEvent;
import com.pikacat.warrior.Warrior;

import java.util.*;

public class Headquarter extends Building {
    private WarriorBuilder warriorBuilder;
    private final Map<String, List<Warrior>> warriors;
    private int lifeElements;
    private int counter = 1;

    public Headquarter(int lifeElements) {
        this.lifeElements = lifeElements;
        this.warriors = new HashMap<>();
    }

    @Override
    public void acceptEvent(Event event) {
        if (event instanceof TimeEvent) {
            TimeEvent timeEvent = (TimeEvent) event;

            WarriorBuilder warriorBuilder = this.warriorBuilder.findBuilder(this.lifeElements);
            Warrior warrior = warriorBuilder.buildWarrior(this.lifeElements, this.counter);
            this.lifeElements -= warriorBuilder.getLife();
            this.warriorBuilder = warriorBuilder.getNext();
            if (null != warrior) {
                warrior.setSide(this.getSide());
                this.counter += 1;
                this.addWarrior(warrior);

                Logger.logBornEvent(timeEvent, this.getName(), warrior,
                        this.warriors.get(warrior.getName()).size());
                return;
            }

            // 已经无法再制造武士了
            Logger.logStopEvent(timeEvent, this.getName());
            EventSystem.unregister(this);
        }
    }

    public void addWarrior(Warrior warrior) {
        if (!this.warriors.containsKey(warrior.getName())) {
            this.warriors.put(warrior.getName(), new LinkedList<>());
        }
        this.warriors.get(warrior.getName()).add(warrior);
    }

    public void setWarriorBuilder(WarriorBuilder warriorBuilder) {
        this.warriorBuilder = warriorBuilder;
    }

    public int getLifeElements() {
        return lifeElements;
    }

    public void setLifeElements(int lifeElements) {
        this.lifeElements = lifeElements;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
