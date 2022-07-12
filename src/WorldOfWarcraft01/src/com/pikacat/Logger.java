package com.pikacat;

import com.pikacat.event.Event;
import com.pikacat.warrior.Warrior;

public class Logger {
    public static void logBornEvent(Event timeEvent, String name, Warrior warrior, int totalWarrior) {
        System.out.println(timeEvent + String.format(" %s %s %d born with strength %d,%d %s in %s",
                warrior.getSide(), warrior.getName(), warrior.getNumber(), warrior.getLife(),
                totalWarrior, warrior.getName(), name));
    }

    public static void logStopEvent(Event timeEvent, String name) {
        System.out.println(timeEvent + " " + name + " stops making warriors");
    }
}
