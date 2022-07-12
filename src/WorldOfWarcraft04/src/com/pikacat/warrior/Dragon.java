package com.pikacat.warrior;

import com.pikacat.Logger;
import com.pikacat.event.Event;

public class Dragon extends Warrior {
    @Override
    public String getWinMessage() {
        return this + " yelled";
    }
}
