package com.pikacat.event;

public abstract class Event {

    private int hour = 0;
    private int minute;

    public Event(int minute) {
        this.minute = minute;
    }

    public void handle() {
        this.hour += 1;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return String.format("%03d:%02d", this.hour, this.minute);
    }

}
