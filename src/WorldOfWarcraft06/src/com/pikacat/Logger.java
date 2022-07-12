package com.pikacat;

import com.pikacat.building.City;
import com.pikacat.building.Headquarter;
import com.pikacat.event.Event;
import com.pikacat.event.FightEvent;
import com.pikacat.warrior.Ninja;
import com.pikacat.warrior.Warrior;

public class Logger {
    public static void logBornMessage(Event event, Warrior warrior) {
        System.out.printf("%s %s\n", event, warrior.getBornMessage());
    }

    public static void logYellMessage(Event event) {
        Warrior warrior = ((FightEvent) event).getActiveAttacker();
        System.out.printf("%s %s yelled in %s\n", event, warrior, warrior.getBuilding().getName());
    }

    public static void logWeaponEvent(Event event, Warrior warrior) {
        System.out.printf("%s %s has %s\n", event, warrior, warrior.getWeaponPack());
    }

    public static void logMarchEvent(Event event, Warrior warrior) {
        System.out.printf("%s %s %s %s with %d elements and force %d\n", event, warrior,
                warrior.getBuilding() instanceof Headquarter ? "reached" : "marched to",
                warrior.getBuilding().getName(), warrior.getLife(), warrior.getAttack());
    }

    public static void logLionRunAwayEvent(Event event, Warrior warrior) {
        System.out.printf("%s %s ran away\n", event, warrior);
    }

    public static void logElementEvent(Event event, Headquarter headquarter) {
        System.out.printf("%s %d elements in %s\n", event, headquarter.getLifeElements(), headquarter.getName());
    }

    public static void logHeadquarterTakenEvent(Event event, Headquarter headquarter) {
        System.out.printf("%s %s was taken\n", event, headquarter.getName());
    }

    public static void logTakeCityElementsEvent(Event event, Warrior warrior, int elements) {
        if (elements > 0 && 35 != event.getMinute()) {
            System.out.printf("%s %s earned %d elements for his headquarter\n", event, warrior, elements);
        }
    }

    public static void logShotEvent(Event event, Warrior attacker, Warrior victim) {
        System.out.printf("%s %s shot", event, attacker);
        if (!victim.isAlive()) {
            System.out.printf(" and killed %s", victim);
        }
        System.out.println();
    }

    public static void logBombMessage(Event event, Warrior attacker, Warrior victim) {
        System.out.printf("%s %s used a bomb and killed %s\n", event, attacker, victim);
    }

    public static void logDiedMessage(Event event, Warrior warrior) {
        if (!warrior.isDeadOfArrow()) {
            System.out.printf("%s %s was killed in %s\n", event, warrior, warrior.getBuilding().getName());
        }
    }

    public static void logAttackMessage(Event event, Warrior attacker, Warrior victim) {
        System.out.printf("%s %s attacked %s in %s with %d elements and force %d\n", event, attacker, victim,
                attacker.getBuilding().getName(), attacker.getLife(), attacker.getAttack());
    }

    public static void logFightBackMessage(Event event, Warrior attacker, Warrior victim) {
        if (!(attacker instanceof Ninja)) {
            System.out.printf("%s %s fought back against %s in %s\n", event, attacker, victim,
                    attacker.getBuilding().getName());
        }
    }

    public static void logFlagRaiseEvent(Event event, City city) {
        System.out.printf("%s %s flag raised in %s\n", event, city.getFlag().getSide(), city.getName());
    }
}
