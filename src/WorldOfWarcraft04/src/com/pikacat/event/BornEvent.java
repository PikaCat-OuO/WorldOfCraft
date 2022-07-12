package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.Headquarter;

public class BornEvent extends Event {
    @Override
    public boolean handle(int time) {
        if (0 == time % 60) {
            // 武士产生事件
            this.setHour(time / 60);
            this.setMinute(0);
            EventSystem.notify(Headquarter.class, this);
            return true;
        }
        return false;
    }
}