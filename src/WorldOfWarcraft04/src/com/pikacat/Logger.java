package com.pikacat;

import com.pikacat.building.Headquarter;
import com.pikacat.event.Event;
import com.pikacat.warrior.Warrior;
import com.pikacat.warrior.Wolf;
import com.pikacat.weapon.Weapon;
import com.pikacat.weapon.WeaponFactory;

import java.util.List;
import java.util.Map;

public class Logger {
    public static void logBornMessage(Event event, Warrior warrior) {
        System.out.println(event + " " +  warrior.getBornMessage());
    }

    public static void logDiedMessage(Event event, Warrior redWarrior, Warrior blueWarrior) {
        System.out.println(event + " both " + redWarrior + " and " + blueWarrior + " died in " +
                redWarrior.getCity().getName());
    }

    public static void logAliveMessage(Event event, Warrior redWarrior, Warrior blueWarrior) {
        System.out.println(event + " both " + redWarrior + " and " + blueWarrior + " were alive in " +
                redWarrior.getCity().getName());
        if (null != redWarrior.getWinMessage()) {
            System.out.println(event + " " + redWarrior.getWinMessage() + " in " + redWarrior.getCity().getName());
        }
        if (null != blueWarrior.getWinMessage()) {
            System.out.println(event + " " + blueWarrior.getWinMessage() + " in " + blueWarrior.getCity().getName());
        }
    }

    public static void logWinMessage(Event event, Warrior winWarrior, Warrior lostWarrior) {
        System.out.println(event + " " + winWarrior + " killed " + lostWarrior + " in " +
                winWarrior.getCity().getName() + " remaining " + winWarrior.getLife() + " elements");
        if (null != winWarrior.getWinMessage()) {
            System.out.println(event + " " + winWarrior.getWinMessage() + " in " + winWarrior.getCity().getName());
        }
    }

    public static void logWeaponCountEvent(Event event, Warrior warrior) {
        Map<String, Integer> weaponCount = warrior.reportWeapon();
        List<String> weaponName = WeaponFactory.getWeaponName();
        System.out.print(event + " " + warrior + " has");
        for (String name : weaponName) {
            System.out.print(" " + weaponCount.get(name) + " " + name);
        }
        System.out.println(" and " + warrior.getLife() + " elements");
    }

    public static void logMarchEvent(Event event, Warrior warrior) {
        System.out.println(event + " " + warrior + " marched to " + warrior.getCity().getName() +
                " with " + warrior.getLife() + " elements and force " + warrior.getAttack());
    }

    public static void logLionRunAwayEvent(Event event, Warrior warrior) {
        System.out.println(event + " " + warrior + " ran away");
    }

    public static void logElementEvent(Event event, Headquarter headquarter) {
        System.out.println(event + " " + headquarter.getLifeElements() + " elements in " +
                headquarter.getName());
    }

    public static void logHeadquarterTakenEvent(Event event, Headquarter headquarter) {
        Warrior warrior = "red".equals(headquarter.getSide()) ? headquarter.getBlueWarrior() :
                headquarter.getRedWarrior();
        System.out.println(event + " " + warrior + " reached " + headquarter.getName() +
                " with " + warrior.getLife() + " elements and force " + warrior.getAttack());
        System.out.println(event + " " + headquarter.getName() + " was taken");
    }

    public static void logWolfRobEvent(Event event, Warrior warrior, Warrior victim) {
        List<Weapon> weaponList = ((Wolf) warrior).getRobbedWeapons();
        System.out.println(event + " " + warrior + " took " + weaponList.size() + " " +
                weaponList.get(0).getName() + " from " + victim + " in " + warrior.getCity().getName());
    }
}
