package com.pikacat.event;

public abstract class Event {

    private int hour;
    private int minute;

    public abstract boolean handle(int time);

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return String.format("%03d:%02d", this.hour, this.minute);
    }

}