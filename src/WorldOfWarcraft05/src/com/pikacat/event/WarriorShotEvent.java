package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;

public class WarriorShotEvent extends Event {
    public WarriorShotEvent() {
        super(35);
    }

    @Override
    public void handle() {
        // 武士放箭事件
        EventSystem.notify(City.class, this);
        super.handle();
    }
}
