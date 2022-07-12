package com.pikacat.building;

import com.pikacat.Logger;
import com.pikacat.builder.WarriorBuilder;
import com.pikacat.event.BornEvent;
import com.pikacat.event.Event;
import com.pikacat.event.ReportElementsEvent;
import com.pikacat.event.WarriorMarchEvent;
import com.pikacat.warrior.Warrior;

import java.util.ArrayList;
import java.util.List;

public class Headquarter extends Building {
    private final List<WarriorBuilder> warriorBuilders = new ArrayList<>();
    private int lifeElements;
    private int counter = 1;
    private int i = 0;

    public Headquarter(int lifeElements) {
        super();
        this.lifeElements = lifeElements;
    }

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof BornEvent) {
            // 如果无法制造武士就返回
            WarriorBuilder warriorBuilder = this.warriorBuilders.get(i % warriorBuilders.size());
            Warrior warrior = warriorBuilder.buildWarrior(this.lifeElements, this.counter);
            if (null != warrior) {
                this.lifeElements -= warriorBuilder.getLife();
                warrior.setSide(this.getSide());
                warrior.setHeadquarter(this);
                warrior.setBuilding(this);
                this.counter += 1;
                ++i;
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
                Warrior oldBlueWarrior = this.getBlueWarrior();
                this.setBlueWarrior(this.getNextBuilding().getBlueWarrior());
                if (null != this.getBlueWarrior()) {
                    this.getBlueWarrior().setBuilding(this);
                    Logger.logMarchEvent(event, this.getBlueWarrior());
                    // 如果有两个士兵进入，司令部已被地方占领，宣告游戏结束
                    if (null != oldBlueWarrior) {
                        Logger.logHeadquarterTakenEvent(event, this);
                        warriorMarchEvent.setGameEnd(true);
                    }
                } else {
                    this.setBlueWarrior(oldBlueWarrior);
                }
            } else {
                // 把红方的士兵接过来
                Warrior oldRedWarrior = this.getRedWarrior();
                this.setRedWarrior(warriorMarchEvent.getRedWaitWarrior());
                if (null != this.getRedWarrior()) {
                    this.getRedWarrior().setBuilding(this);
                    Logger.logMarchEvent(event, this.getRedWarrior());
                    // 如果有两个士兵进入，司令部已被地方占领，宣告游戏结束
                    if (null != oldRedWarrior) {
                        Logger.logHeadquarterTakenEvent(event, this);
                        warriorMarchEvent.setGameEnd(true);
                    }
                } else {
                    this.setRedWarrior(oldRedWarrior);
                }

                // 蓝方的士兵已经送出去了
                this.setBlueWarrior(null);
            }
        }
    }

    public void setLifeElements(int lifeElements) {
        this.lifeElements = lifeElements;
    }

    public int getLifeElements() {
        return lifeElements;
    }

    public void addWarriorBuilder(WarriorBuilder warriorBuilder) {
        this.warriorBuilders.add(warriorBuilder);
    }
}
