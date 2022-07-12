package com.pikacat;

import com.pikacat.event.Event;

public abstract class GameObject {
    private String name;
    private String side;

    public void acceptEvent(Event event) {

    }

    public void detach() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
