package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.Headquarter;

public class ReportElementsEvent extends Event {
    @Override
    public boolean handle(int time) {
        if (50 == time % 60) {
            // 司令部报告生命元事件
            this.setHour(time / 60);
            this.setMinute(50);
            EventSystem.notify(Headquarter.class, this);
            return true;
        } else {
            return false;
        }
    }
}