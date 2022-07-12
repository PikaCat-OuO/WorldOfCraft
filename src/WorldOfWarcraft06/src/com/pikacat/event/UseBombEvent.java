package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;

public class UseBombEvent extends Event {
    public UseBombEvent() {
        super(38);
    }

    @Override
    public void handle() {
        // 武士使用炸弹事件
        EventSystem.notify(City.class, this);
        super.handle();
    }
}
