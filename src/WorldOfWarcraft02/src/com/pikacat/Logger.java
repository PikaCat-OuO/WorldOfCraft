package com.pikacat;

import com.pikacat.event.Event;
import com.pikacat.warrior.Warrior;

public class Logger {
    public static void logBornEvent(Event timeEvent, String name, Warrior warrior, int totalWarrior) {
        System.out.println(timeEvent + " " +  warrior.getBornMessage(totalWarrior, name));
    }

    public static void logStopEvent(Event timeEvent, String name) {
        System.out.println(timeEvent + " " + name + " stops making warriors");
    }
}
