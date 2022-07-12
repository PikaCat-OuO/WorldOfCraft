package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.warrior.Lion;

public class LionRunEvent extends Event {
    @Override
    public boolean handle(int time) {
        if (5 == time % 60) {
            // 狮子逃跑事件
            this.setHour(time / 60);
            this.setMinute(5);
            EventSystem.notify(Lion.class, this);
            return true;
        } else {
            return false;
        }
    }
}
