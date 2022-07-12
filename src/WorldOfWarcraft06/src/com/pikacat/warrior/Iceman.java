package com.pikacat.warrior;

import com.pikacat.event.Event;
import com.pikacat.event.WarriorMarchEvent;

public class Iceman extends Warrior {
    private int counter = 0;

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        // 行进需要减少生命值
        if (event instanceof WarriorMarchEvent) {
            ++this.counter;
            if (0 == this.counter % 2) {
                this.setLife(Math.max(1, this.getLife() - 9));
                this.setAttack(this.getAttack() + 20);
            }
        }
    }
}
