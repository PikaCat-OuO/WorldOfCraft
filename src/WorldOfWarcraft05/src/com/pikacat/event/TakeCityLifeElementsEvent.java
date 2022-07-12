package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;

public class TakeCityLifeElementsEvent extends Event {
    public TakeCityLifeElementsEvent() {
        super(30);
    }

    @Override
    public void handle() {
        // 武士取走生命元事件
        EventSystem.notify(City.class, this);
        super.handle();
    }
}
