package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;

public class WeaponRobEvent extends Event {
    @Override
    public boolean handle(int time) {
        if (35 == time % 60) {
            // 抢夺武器事件
            this.setHour(time / 60);
            this.setMinute(35);
            EventSystem.notify(City.class, this);
            return true;
        } else {
            return false;
        }
    }
}