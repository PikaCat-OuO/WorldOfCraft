package com.pikacat.warrior;

import com.pikacat.event.Event;
import com.pikacat.event.WarriorMarchEvent;

public class Iceman extends Warrior {
    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        // 行进需要减少生命值
        if (event instanceof WarriorMarchEvent) {
            this.setLife(this.getLife() - this.getLife() / 10);
        }
    }
}
