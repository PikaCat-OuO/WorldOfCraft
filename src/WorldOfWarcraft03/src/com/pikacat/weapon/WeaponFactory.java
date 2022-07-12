package com.pikacat.weapon;

import java.util.LinkedList;
import java.util.List;

public class WeaponFactory {
    private static final List<Weapon> weaponList = new LinkedList<>();
    private static final List<String> weaponName = new LinkedList<>();

    public static void addWeapon(Weapon weapon) {
        WeaponFactory.weaponList.add(weapon);
        WeaponFactory.weaponName.add(weapon.getName());
    }

    public static Weapon getWeapon(int number) {
        try {
            return weaponList.get(number).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getWeaponName() {
        return WeaponFactory.weaponName;
    }
}
