package com.pikacat.weapon;

import com.pikacat.warrior.Warrior;

import java.util.HashMap;
import java.util.Map;

public class WeaponPack {
    private final Map<Class<? extends Weapon>, Weapon> weaponMap = new HashMap<>();

    public void addWeapon(Weapon weapon) {
        // 如果已经有这种武器了就直接返回
        if (!hasWeapon(weapon.getClass())) {
            this.weaponMap.put(weapon.getClass(), weapon);
        }
    }

    public void addWeapon(WeaponPack weaponPack) {
        for (Weapon weapon : weaponPack.weaponMap.values()) {
            addWeapon(weapon);
        }
    }

    public boolean hasWeapon(Class<? extends Weapon> weaponClass) {
        return this.weaponMap.containsKey(weaponClass);
    }

    public Weapon getWeapon(Class<? extends Weapon> weaponClass) {
        if (hasWeapon(weaponClass)) {
            return this.weaponMap.get(weaponClass);
        }
        return null;
    }

    public void use(Class<? extends Weapon> weaponClass, Warrior attacker, Warrior victim) {
        // 如果没有这种武器直接返回
        if (hasWeapon(weaponClass)) {
            if (!this.weaponMap.get(weaponClass).use(attacker, victim)) {
                // 如果武器用完就没了
                this.weaponMap.remove(weaponClass);
            }
        }
    }

    @Override
    public String toString() {
        if (this.weaponMap.isEmpty()) {
            return "no weapon";
        } else {
            String weapons = "";
            if (hasWeapon(Arrow.class)) {
                weapons += "," + this.weaponMap.get(Arrow.class);
            }
            if (hasWeapon(Bomb.class)) {
                weapons += "," + this.weaponMap.get(Bomb.class);
            }
            if (hasWeapon(Sword.class)) {
                weapons += "," + this.weaponMap.get(Sword.class);
            }
            return weapons.substring(1);
        }
    }
}

