package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;

public class ReportWeaponEvent extends Event {
    @Override
    public boolean handle(int time) {
        if (55 == time % 60) {
            // 武士报告拥有武器事件
            this.setHour(time / 60);
            this.setMinute(55);
            EventSystem.notify(City.class, this);
            return true;
        } else {
            return false;
        }
    }
}