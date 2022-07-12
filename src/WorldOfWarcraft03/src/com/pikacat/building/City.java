package com.pikacat.building;

import com.pikacat.EventSystem;
import com.pikacat.GameObject;
import com.pikacat.Logger;
import com.pikacat.event.*;
import com.pikacat.warrior.Warrior;
import com.pikacat.warrior.Wolf;

public class City extends GameObject {
    private int number;
    private Warrior redWarrior;
    private Warrior blueWarrior;
    private City formerCity;
    private City nextCity;

    public City() {
        EventSystem.register(this);
    }

    public Warrior getRedWarrior() {
        return redWarrior;
    }

    public void setRedWarrior(Warrior redWarrior) {
        this.redWarrior = redWarrior;
    }

    public Warrior getBlueWarrior() {
        return blueWarrior;
    }

    public void setBlueWarrior(Warrior blueWarrior) {
        this.blueWarrior = blueWarrior;
    }

    public City getNextCity() {
        return nextCity;
    }

    public void setNextCity(City nextCity) {
        this.nextCity = nextCity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public City getFormerCity() {
        return formerCity;
    }

    public void setFormerCity(City formerCity) {
        this.formerCity = formerCity;
    }

    public void detach(Warrior warrior) {
        if ("red".equals(warrior.getSide())) {
            this.redWarrior = null;
        } else {
            this.blueWarrior = null;
        }
    }

    @Override
    public void acceptEvent(Event event) {
        if (event instanceof FightEvent && null != redWarrior && null != blueWarrior) {
            this.redWarrior.sortWeapon();
            this.blueWarrior.sortWeapon();

            // 当前轮到哪一方
            boolean isRedTurn = 1 == this.getNumber() % 2;
            while (this.redWarrior.isAlive() && this.blueWarrior.isAlive()) {
                boolean redCanDamage = this.redWarrior.canDamage();
                boolean blueCanDamage = this.blueWarrior.canDamage();
                // 如果双方都无法造成伤害就结束战斗
                if (!redCanDamage && !blueCanDamage) break;

                if (isRedTurn && redCanDamage) {
                    this.redWarrior.attack(this.blueWarrior);
                } else if (!isRedTurn && blueCanDamage) {
                    this.blueWarrior.attack(this.redWarrior);
                }

                isRedTurn = !isRedTurn;
            }

            // 判断战斗结果
            if (this.redWarrior.isAlive() && this.blueWarrior.isAlive()) {
                Logger.logAliveMessage(event, this.redWarrior, this.blueWarrior);
            } else if (!this.redWarrior.isAlive() && !this.blueWarrior.isAlive()) {
                Logger.logDiedMessage(event, this.redWarrior, this.blueWarrior);
                this.redWarrior.die();
                this.blueWarrior.die();
            } else if (this.redWarrior.isAlive()) {
                Logger.logWinMessage(event, this.redWarrior, this.blueWarrior);
                this.redWarrior.robWarrior(this.blueWarrior);
                this.blueWarrior.die();
            } else {
                Logger.logWinMessage(event, this.blueWarrior, this.redWarrior);
                this.blueWarrior.robWarrior(this.redWarrior);
                this.redWarrior.die();
            }
        } else if (event instanceof WarriorMarchEvent) {
            WarriorMarchEvent warriorMarchEvent = (WarriorMarchEvent) event;

            // 先行进红方的武士
            Warrior warrior = this.redWarrior;
            this.redWarrior = warriorMarchEvent.getRedWaitWarrior();
            warriorMarchEvent.setRedWaitWarrior(warrior);
            if (null != this.redWarrior) {
                this.redWarrior.setCity(this);
                Logger.logMarchEvent(event, this.redWarrior);
            }

            // 再行进蓝方的武士
            this.blueWarrior = this.nextCity.blueWarrior;
            if (null != this.blueWarrior) {
                this.blueWarrior.setCity(this);
                Logger.logMarchEvent(event, this.blueWarrior);
            }
        } else if (event instanceof ReportWeaponEvent) {
            // 先汇报红方的，再汇报蓝方的
            if (null != this.redWarrior) {
                Logger.logWeaponCountEvent(event, this.redWarrior);
            }
            if (null != this.blueWarrior) {
                Logger.logWeaponCountEvent(event, this.blueWarrior);
            }
        } else if (event instanceof WeaponRobEvent) {
            if (this.redWarrior instanceof Wolf) {
                this.redWarrior.acceptEvent(event);
            } else if (this.blueWarrior instanceof Wolf) {
                this.blueWarrior.acceptEvent(event);
            }
        }
    }
}
