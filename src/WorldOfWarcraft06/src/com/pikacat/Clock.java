package com.pikacat;

import com.pikacat.event.*;

import java.util.ArrayList;
import java.util.List;

public class Clock extends GameObject {
    private final List<Event> eventQueue = new ArrayList<>();
    private boolean gameEnd = false;

    public void addEvent(Event event) {
        this.eventQueue.add(event);
    }

    public void start() {
        EventSystem.register(this);

        int i = 0;
        Event event = eventQueue.get(i);

        while (!this.gameEnd && event.getHour() * 60 + event.getMinute() <= Settings.END_TIME) {
            event.handle();
            ++i;
            event = eventQueue.get(i % eventQueue.size());
        }
    }

    @Override
    public void acceptEvent(Event event) {
        if (event instanceof EndEvent) {
            // 游戏结束了
            this.gameEnd = true;
        }
    }
}
