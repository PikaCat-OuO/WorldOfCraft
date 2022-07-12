package com.pikacat;

import com.pikacat.event.*;

import java.util.ArrayList;
import java.util.List;

public class Clock extends GameObject {
    private final List<Event> eventQueue = new ArrayList<>();
    private boolean gameEnd = false;

    public void start(int time) {
        EventSystem.register(this);

        eventQueue.add(new BornEvent());
        eventQueue.add(new LionRunEvent());
        eventQueue.add(new WarriorMarchEvent());
        eventQueue.add(new CityProduceLifeElementsEvent());
        eventQueue.add(new TakeCityLifeElementsEvent());
        eventQueue.add(new WarriorShotEvent());
        eventQueue.add(new CityCleanEvent());
        eventQueue.add(new UseBombEvent());
        eventQueue.add(new FightEvent());
        eventQueue.add(new ReportElementsEvent());
        eventQueue.add(new ReportWeaponEvent());

        int i = 0;
        Event event = eventQueue.get(i);

        while (!this.gameEnd && event.getHour() * 60 + event.getMinute() <= time) {
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
