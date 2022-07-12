package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;

public class CityCleanEvent extends Event {
    public CityCleanEvent() {
        super(35);
    }

    @Override
    public void handle() {
        // 清理因为射箭而死亡的没有敌人的城市
        EventSystem.notify(City.class, this);
        super.handle();
    }
}
