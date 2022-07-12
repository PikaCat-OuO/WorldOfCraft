package com.pikacat.warrior;

import com.pikacat.Logger;
import com.pikacat.event.Event;
import com.pikacat.event.WeaponRobEvent;
import com.pikacat.weapon.Weapon;

import java.util.List;
import java.util.stream.Collectors;

public class Wolf extends Warrior {
    private List<Weapon> robbedWeapons;

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof WeaponRobEvent) {
            Warrior victim = "red".equals(this.getSide()) ? this.getCity().getBlueWarrior() :
                    this.getCity().getRedWarrior();

            // 如果对方也是wolf就不进行抢劫
            if (null != victim && !(victim instanceof Wolf)) {
                this.robbedWeapons = this.robWeapon(victim);

                // 只能获取编号最小的武器
                if (!this.robbedWeapons.isEmpty()) {
                    int weaponNo = this.robbedWeapons.get(0).getWeaponNo();
                    this.robbedWeapons = this.robbedWeapons.stream().filter(weapon -> weaponNo == weapon.getWeaponNo())
                            .collect(Collectors.toList());
                    this.getWeaponList().addAll(this.robbedWeapons);
                    victim.getWeaponList().removeAll(this.robbedWeapons);
                }

                // 抢劫完成后报告情况
                if (!this.robbedWeapons.isEmpty()) {
                    Logger.logWolfRobEvent(event, this, victim);
                }
            }
        }
    }

    public List<Weapon> getRobbedWeapons() {
        return robbedWeapons;
    }
}
