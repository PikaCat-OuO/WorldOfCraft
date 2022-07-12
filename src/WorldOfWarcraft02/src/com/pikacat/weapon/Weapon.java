package com.pikacat.weapon;

import com.pikacat.GameObject;

public abstract class Weapon extends GameObject implements Cloneable{
    private int weaponNo;

    public int getWeaponNo() {
        return weaponNo;
    }

    public void setWeaponNo(int weaponNo) {
        this.weaponNo = weaponNo;
    }

    @Override
    protected Weapon clone() throws CloneNotSupportedException {
        return (Weapon) super.clone();
    }
}
