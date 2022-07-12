package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;
import com.pikacat.warrior.Warrior;

import java.util.Stack;

public class FightEvent extends Event {
    private Warrior activeAttacker;
    private final Stack<Warrior> waitingWarrior = new Stack<>();

    public FightEvent() {
        super(40);
    }

    public void addWaitingWarrior(Warrior warrior) {
        this.waitingWarrior.add(warrior);
    }

    public Warrior getActiveAttacker() {
        return activeAttacker;
    }

    public void setActiveAttacker(Warrior activeAttacker) {
        this.activeAttacker = activeAttacker;
    }

    @Override
    public void handle() {
        // 战斗事件
        EventSystem.notify(City.class, this);
        // 现在开始奖励蓝方的武士，反方向奖励
        while (!this.waitingWarrior.empty()) {
            this.waitingWarrior.pop().getPrize();
        }
        // 完成之后进行生命元的回收工作
        TakeCityLifeElementsEvent takeCityLifeElementsEvent = new TakeCityLifeElementsEvent();
        takeCityLifeElementsEvent.setHour(this.getHour());
        // 产生一个偏差，使得时间对不上，不再打印
        takeCityLifeElementsEvent.setMinute(this.getMinute() - 5);
        EventSystem.notify(City.class, takeCityLifeElementsEvent);
        super.handle();
    }
}
