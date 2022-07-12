package com.pikacat;

import com.pikacat.event.TimeEvent;

public class Clock {
    private int time = 0;

    public void start() {
        while (EventSystem.hasGameObject()) {
            TimeEvent timeEvent = new TimeEvent();
            timeEvent.setTime(time);
            time += 1;

            EventSystem.notify("red headquarter", timeEvent);
            EventSystem.notify("blue headquarter", timeEvent);
        }
    }

}
