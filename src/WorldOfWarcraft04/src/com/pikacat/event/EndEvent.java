package com.pikacat.event;

public class EndEvent extends Event {
    @Override
    public boolean handle(int time) {
        return true;
    }
}
