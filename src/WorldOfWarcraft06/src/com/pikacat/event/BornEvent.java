package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.Headquarter;

public class BornEvent extends Event {
    public BornEvent() {
        super(0);
    }

    @Override
    public void handle() {
        // 武士产生事件
        EventSystem.notify(Headquarter.class, this);
        super.handle();
    }
}
