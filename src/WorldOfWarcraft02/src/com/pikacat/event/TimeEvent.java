package com.pikacat.event;

public class TimeEvent implements Event {
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%03d", this.time);
    }
}
