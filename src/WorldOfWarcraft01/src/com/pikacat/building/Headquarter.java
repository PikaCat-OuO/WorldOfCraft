package com.pikacat.building;

import com.pikacat.EventSystem;
import com.pikacat.Logger;
import com.pikacat.builder.WarriorBuilder;
import com.pikacat.event.Event;
import com.pikacat.event.TimeEvent;
import com.pikacat.warrior.Warrior;

import java.util.*;

public class Headquarter extends Building {
    private final Queue<WarriorBuilder> warriorBuilders;
    private final Map<Class<? extends Warrior>, List<Warrior>> warriors;
    private int lifeElements;
    private int counter = 1;

    public Headquarter(int lifeElements) {
        this.lifeElements = lifeElements;
        this.warriorBuilders = new LinkedList<>();
        this.warriors = new HashMap<>();
    }

    @Override
    public void acceptEvent(Event event) {
        if (event instanceof TimeEvent) {
            TimeEvent timeEvent = (TimeEvent)event;

            for (int i = 0; i < this.warriorBuilders.size(); ++i) {
                WarriorBuilder warriorBuilder = this.warriorBuilders.poll();
                this.warriorBuilders.offer(warriorBuilder);
                if (this.lifeElements >= warriorBuilder.getLife()) {
                    this.lifeElements -= warriorBuilder.getLife();

                    Warrior warrior = warriorBuilder.buildWarrior();
                    warrior.setSide(this.getSide());
                    warrior.setNumber(this.counter);
                    this.counter += 1;
                    this.addWarrior(warrior);

                    Logger.logBornEvent(timeEvent, this.getName(), warrior,
                            this.warriors.get(warrior.getClass()).size());

                    return;
                }
            }

            // 已经无法再制造武士了
            Logger.logStopEvent(timeEvent, this.getName());
            EventSystem.unregister(this);
        }
    }

    public void addWarrior(Warrior warrior) {
        if (!this.warriors.containsKey(warrior.getClass())) {
          this.warriors.put(warrior.getClass(), new LinkedList<>());
        }
        this.warriors.get(warrior.getClass()).add(warrior);
    }

    public void addWarriorBuilder(WarriorBuilder warriorBuilder) {
        this.warriorBuilders.add(warriorBuilder);
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
