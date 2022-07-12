package com.pikacat.building;

import com.pikacat.Logger;
import com.pikacat.event.*;
import com.pikacat.flag.Flag;
import com.pikacat.warrior.Warrior;
import com.pikacat.weapon.Arrow;
import com.pikacat.weapon.Bomb;

public class City extends Building {
    // 该城市拥有的生命元的数量
    private int lifeElements = 0;
    // 该城市的旗帜
    private Flag flag;
    // 上一次的赢家
    private String lastWinSide;

    public String getAttackSide() {
        if (null != this.flag) {
            return this.flag.getSide();
        } else {
            return 0 == this.getNumber() % 2 ? "blue" : "red";
        }
    }

    public Flag getFlag() {
        return flag;
    }

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof CityProduceLifeElementsEvent) {
            this.lifeElements += 10;
        } else if (event instanceof TakeCityLifeElementsEvent) {
            // 如果只有一个武士就拿走
            Warrior warrior = null;
            if (null != this.getRedWarrior() && null == this.getBlueWarrior()) {
                warrior = this.getRedWarrior();
            } else if (null == this.getRedWarrior() && null != this.getBlueWarrior()) {
                warrior = this.getBlueWarrior();
            }
            if (null != warrior) {
                Headquarter headquarter = warrior.getHeadquarter();
                headquarter.setLifeElements(headquarter.getLifeElements() + this.lifeElements);
                Logger.logTakeCityElementsEvent(event, warrior, this.lifeElements);
                this.lifeElements = 0;
            }
        } else if (event instanceof WarriorShotEvent) {
            // 红方射箭
            Warrior redWarrior = this.getRedWarrior();
            Warrior blueWarrior = this.getNextBuilding().getBlueWarrior();
            if (null != redWarrior && null != blueWarrior && redWarrior.hasWeapon(Arrow.class)) {
                redWarrior.shotArrow(blueWarrior);
                if (!blueWarrior.isAlive()) {
                    blueWarrior.setDeadOfArrow(true);
                }
                Logger.logShotEvent(event, redWarrior, blueWarrior);
            }

            // 蓝方射箭
            redWarrior = this.getFormerBuilding().getRedWarrior();
            blueWarrior = this.getBlueWarrior();
            if (null != redWarrior && null != blueWarrior && blueWarrior.hasWeapon(Arrow.class)) {
                blueWarrior.shotArrow(redWarrior);
                if (!redWarrior.isAlive()) {
                    redWarrior.setDeadOfArrow(true);
                }
                Logger.logShotEvent(event, blueWarrior, redWarrior);
            }
        } else if (event instanceof CityCleanEvent) {
            if (null != this.getRedWarrior() && null == this.getBlueWarrior() &&
                    !this.getRedWarrior().isAlive()) {
                this.getRedWarrior().die();
            } else if (null != this.getBlueWarrior() && null == this.getRedWarrior() &&
                    !this.getBlueWarrior().isAlive()) {
                this.getBlueWarrior().die();
            } else if (null != this.getRedWarrior() && null != this.getBlueWarrior() &&
                    this.getRedWarrior().isDeadOfArrow() && this.getBlueWarrior().isDeadOfArrow()) {
                // 双方均死于箭
                this.getRedWarrior().die();
                this.getBlueWarrior().die();
            }
        } else if (event instanceof UseBombEvent) {
            if (null != this.getRedWarrior() && null != this.getBlueWarrior() &&
                    this.getRedWarrior().isAlive() && this.getBlueWarrior().isAlive()) {
                // 评估是否使用炸弹
                boolean redDied;
                boolean blueDied;
                if ("red".equals(this.getAttackSide())) {
                    int result = this.getRedWarrior().evaluate(this.getBlueWarrior());
                    redDied = 1 == (result & 1);
                    blueDied = 2 == (result & 2);
                } else {
                    int result = this.getBlueWarrior().evaluate(this.getRedWarrior());
                    blueDied = 1 == (result & 1);
                    redDied = 2 == (result & 2);
                }
                // 看红方是否需要使用炸弹
                if (redDied && this.getRedWarrior().hasWeapon(Bomb.class)) {
                    Logger.logBombMessage(event, this.getRedWarrior(), this.getBlueWarrior());
                    this.getRedWarrior().useBomb(this.getBlueWarrior());
                } else if (blueDied && this.getBlueWarrior().hasWeapon(Bomb.class)) {
                    Logger.logBombMessage(event, this.getBlueWarrior(), this.getRedWarrior());
                    this.getBlueWarrior().useBomb(this.getRedWarrior());
                }
            }
        } else if (event instanceof FightEvent && null != this.getRedWarrior() && null != this.getBlueWarrior()) {
            this.getRedWarrior().beforeFight();
            this.getBlueWarrior().beforeFight();

            // 决定主动攻击方
            Warrior attacker;
            Warrior victim;
            if ("red".equals(this.getAttackSide())) {
                attacker = this.getRedWarrior();
                victim = this.getBlueWarrior();
            } else {
                attacker = this.getBlueWarrior();
                victim = this.getRedWarrior();
            }
            ((FightEvent) event).setActiveAttacker(attacker);

            // 开始战斗
            if (attacker.isAlive() && victim.isAlive()) {
                Logger.logAttackMessage(event, attacker, victim);
                attacker.attack(victim);
                if (victim.isAlive()) {
                    Logger.logFightBackMessage(event, victim, attacker);
                    victim.fightBack(attacker);
                }
            }

            // 首先输出战死结果
            if (!this.getRedWarrior().isAlive()) {
                Logger.logDiedMessage(event, this.getRedWarrior());
            }
            if (!this.getBlueWarrior().isAlive()) {
                Logger.logDiedMessage(event, this.getBlueWarrior());
            }

            // 接着处理剩余的内容
            this.getRedWarrior().afterFight(event, this.getBlueWarrior());
            this.getBlueWarrior().afterFight(event, this.getRedWarrior());

            // 然后处理旗帜和生命元
            if ((this.getRedWarrior().isAlive() && this.getBlueWarrior().isAlive()) ||
                    (!this.getRedWarrior().isAlive() && !this.getBlueWarrior().isAlive())) {
                this.lastWinSide = null;
            } else {
                Warrior warrior;
                if (this.getRedWarrior().isAlive()) {
                    warrior = this.getRedWarrior();
                } else {
                    warrior = this.getBlueWarrior();
                }
                Logger.logTakeCityElementsEvent(event, warrior, this.lifeElements);
                String side = warrior.getSide();
                if (side.equals(this.lastWinSide)) {
                    // 如果已经有自己的旗帜了就不处理
                    if (null == this.flag || !side.equals(this.flag.getSide())) {
                        this.flag = new Flag();
                        this.flag.setSide(side);
                        Logger.logFlagRaiseEvent(event, this);
                    }
                } else {
                    this.lastWinSide = side;
                }
            }

            // 然后判断死亡
            if (!this.getRedWarrior().isAlive()) {
                this.getRedWarrior().die();
            }
            if (!this.getBlueWarrior().isAlive()) {
                this.getBlueWarrior().die();
            }
        } else if (event instanceof WarriorMarchEvent) {
            WarriorMarchEvent warriorMarchEvent = (WarriorMarchEvent) event;

            // 先行进红方的武士
            Warrior warrior = this.getRedWarrior();
            this.setRedWarrior(warriorMarchEvent.getRedWaitWarrior());
            warriorMarchEvent.setRedWaitWarrior(warrior);
            if (null != this.getRedWarrior()) {
                this.getRedWarrior().setBuilding(this);
                Logger.logMarchEvent(event, this.getRedWarrior());
            }

            // 再行进蓝方的武士
            this.setBlueWarrior(this.getNextBuilding().getBlueWarrior());
            if (null != this.getBlueWarrior()) {
                this.getBlueWarrior().setBuilding(this);
                Logger.logMarchEvent(event, this.getBlueWarrior());
            }
        }
    }
}
