package com.pikacat.weapon;

import java.util.LinkedList;
import java.util.List;

public class WeaponFactory {
    private static final List<Weapon> weaponList = new LinkedList<>();

    public static void addWeapon(Weapon weapon) {
        WeaponFactory.weaponList.add(weapon);
    }

    public static Weapon getWeapon(int attack, int number) {
        try {
            Weapon weapon = weaponList.get(number).clone();
            if (weapon instanceof Sword) {
                ((Sword) weapon).setAttack(attack * 2 / 10);
            }
            return weapon;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
