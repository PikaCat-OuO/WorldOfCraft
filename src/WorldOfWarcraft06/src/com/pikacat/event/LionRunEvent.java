package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.Building;

public class LionRunEvent extends Event {
    public LionRunEvent() {
        super(5);
    }

    @Override
    public void handle() {
        // 狮子逃跑事件
        EventSystem.notify(Building.class, this);
        super.handle();
    }
}
