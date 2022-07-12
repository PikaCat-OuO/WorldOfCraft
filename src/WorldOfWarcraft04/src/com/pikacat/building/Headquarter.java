package com.pikacat.building;

import com.pikacat.Logger;
import com.pikacat.builder.WarriorBuilder;
import com.pikacat.event.BornEvent;
import com.pikacat.event.Event;
import com.pikacat.event.ReportElementsEvent;
import com.pikacat.event.WarriorMarchEvent;
import com.pikacat.warrior.Warrior;

import java.util.LinkedList;
import java.util.Queue;

public class Headquarter extends City {
    private final Queue<WarriorBuilder> warriorBuilders;
    private int lifeElements;
    private int counter = 1;

    public Headquarter(int lifeElements) {
        super();
        this.warriorBuilders = new LinkedList<>();
        this.lifeElements = lifeElements;
    }

    @Override
    public void acceptEvent(Event event) {
        if (event instanceof BornEvent) {
            // 如果无法制造武士就返回
            WarriorBuilder warriorBuilder = this.warriorBuilders.peek();
            assert warriorBuilder != null;
            Warrior warrior = warriorBuilder.buildWarrior(this.lifeElements, this.counter);
            if (null != warrior) {
                this.warriorBuilders.offer(this.warriorBuilders.poll());
                this.lifeElements -= warriorBuilder.getLife();
                warrior.setSide(this.getSide());
                warrior.setCity(this);
                this.counter += 1;
                if ("red".equals(this.getSide())) {
                    this.setRedWarrior(warrior);
                } else {
                    this.setBlueWarrior(warrior);
                }

                Logger.logBornMessage(event, warrior);
            }
        } else if (event instanceof ReportElementsEvent) {
            Logger.logElementEvent(event, this);
        } else if (event instanceof WarriorMarchEvent) {
            WarriorMarchEvent warriorMarchEvent = (WarriorMarchEvent) event;
            if ("red".equals(this.getSide())) {
                // 先把红方的士兵送出去
                warriorMarchEvent.setRedWaitWarrior(this.getRedWarrior());
                this.setRedWarrior(null);

                // 再把蓝方的士兵接过来
                this.setBlueWarrior(this.getNextCity().getBlueWarrior());
                if (null != this.getBlueWarrior()) {
                    this.getBlueWarrior().setCity(this);
                    // 司令部已被地方占领，游戏结束了
                    Logger.logHeadquarterTakenEvent(event, this);
                    warriorMarchEvent.setGameEnd(true);
                }
            } else {
                // 把红方的士兵接过来
                this.setRedWarrior(warriorMarchEvent.getRedWaitWarrior());
                if (null != this.getRedWarrior()) {
                    this.getRedWarrior().setCity(this);
                    // 司令部已被地方占领，游戏结束了
                    Logger.logHeadquarterTakenEvent(event, this);
                    warriorMarchEvent.setGameEnd(true);
                }

                // 蓝方的士兵已经送出去了
                this.setBlueWarrior(null);
            }
        }
    }

    public int getLifeElements() {
        return lifeElements;
    }

    public void addWarriorBuilder(WarriorBuilder warriorBuilder) {
        this.warriorBuilders.add(warriorBuilder);
    }
}

