package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;

public class FightEvent extends Event {
    @Override
    public boolean handle(int time) {
        if (40 == time % 60) {
            // 战斗事件
            this.setHour(time / 60);
            this.setMinute(40);
            EventSystem.notify(City.class, this);
            return true;
        } else {
            return false;
        }
    }
}
