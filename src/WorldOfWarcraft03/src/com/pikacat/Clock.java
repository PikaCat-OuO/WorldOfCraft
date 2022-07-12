package com.pikacat;

import com.pikacat.event.*;

import java.util.LinkedList;
import java.util.Queue;

public class Clock extends GameObject {
    private int elapse = 0;
    private final Queue<Event> eventQueue = new LinkedList<>();

    public void start(int time) {
        EventSystem.register(this);

        eventQueue.offer(new BornEvent());
        eventQueue.offer(new LionRunEvent());
        eventQueue.offer(new WarriorMarchEvent());
        eventQueue.offer(new WeaponRobEvent());
        eventQueue.offer(new FightEvent());
        eventQueue.offer(new ReportElementsEvent());
        eventQueue.offer(new ReportWeaponEvent());

        while (this.elapse <= time) {
            Event event = eventQueue.peek();
            assert event != null;
            if (event.handle(this.elapse)) {
                eventQueue.poll();
                eventQueue.offer(event);
            }
            this.elapse += 5;
        }
    }

    @Override
    public void acceptEvent(Event event) {
        if (event instanceof EndEvent) {
            // 游戏结束了
            this.elapse = 10000;
        }
    }
}
