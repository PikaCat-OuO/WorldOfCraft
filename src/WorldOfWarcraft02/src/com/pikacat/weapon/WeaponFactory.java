package com.pikacat.weapon;

import java.util.LinkedList;
import java.util.List;

public class WeaponFactory {
    private static final List<Weapon> weaponList = new LinkedList<>();

    public static void addWeapon(Weapon weapon) {
        WeaponFactory.weaponList.add(weapon);
    }

    public static Weapon getWeapon(int number) {
        try {
            return weaponList.get(number).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
