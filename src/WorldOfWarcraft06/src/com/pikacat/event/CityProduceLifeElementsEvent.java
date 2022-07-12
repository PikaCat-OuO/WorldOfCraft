package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.City;

public class CityProduceLifeElementsEvent extends Event {
    public CityProduceLifeElementsEvent() {
        super(20);
    }

    @Override
    public void handle() {
        EventSystem.notify(City.class, this);
        super.handle();
    }
}
