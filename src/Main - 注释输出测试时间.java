import java.util.*;
import java.math.*;
import java.text.*;

class DragonBuilder extends WarriorBuilder {

    public DragonBuilder() {
        super("dragon");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Dragon dragon = new Dragon();
        dragon.setMorale((double) (elements - this.getLife()) / this.getLife());
        this.setWarrior(dragon);
        equipWeapon(number % 3);
    }

}

class IcemanBuilder extends WarriorBuilder {

    public IcemanBuilder() {
        super("iceman");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        this.setWarrior(new Iceman());
        equipWeapon(number % 3);
    }

}

class LionBuilder extends WarriorBuilder {

    public LionBuilder() {
        super("lion");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        Lion lion = new Lion();
        lion.setLoyalty(elements - this.getLife());
        this.setWarrior(lion);
    }

}

class NinjaBuilder extends WarriorBuilder {

    public NinjaBuilder() {
        super("ninja");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        this.setWarrior(new Ninja());
        equipWeapon(number % 3);
        equipWeapon((number + 1) % 3);
    }

}

abstract class WarriorBuilder extends GameObject{
    private int life;
    private int attack;
    private Warrior warrior;

    public WarriorBuilder(String name) {
        this.setName(name);
    }

    public abstract void constructWarrior(int elements, int number);

    public void equipWeapon(int number) {
        Weapon weapon = WeaponFactory.getWeapon(this.getAttack(), number);
        // 如果剑的攻击力为0，直接消失
        if (!(weapon instanceof Sword) || ((Sword) weapon).getAttack() != 0) {
            this.warrior.addWeapon(weapon);
        }
    }

    public Warrior buildWarrior(int elements, int number) {
        if (elements < this.getLife()) {
            return null;
        }
        constructWarrior(elements, number);
        this.warrior.setLife(this.life);
        this.warrior.setAttack(this.attack);
        this.warrior.setNumber(number);
        this.warrior.setName(this.getName());
        return this.warrior;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }
}

class WolfBuilder extends WarriorBuilder {

    public WolfBuilder() {
        super("wolf");
    }

    @Override
    public void constructWarrior(int elements, int number) {
        this.setWarrior(new Wolf());
    }

}

class Building extends GameObject {
    private int number;
    private Warrior redWarrior;
    private Warrior blueWarrior;
    private Building formerBuilding;
    private Building nextBuilding;

    public Building() {
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

    public Building getFormerBuilding() {
        return formerBuilding;
    }

    public void setFormerBuilding(Building formerBuilding) {
        this.formerBuilding = formerBuilding;
    }

    public Building getNextBuilding() {
        return nextBuilding;
    }

    public void setNextBuilding(Building nextBuilding) {
        this.nextBuilding = nextBuilding;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void detach(Warrior warrior) {
        if ("red".equals(warrior.getSide())) {
            this.redWarrior = null;
        } else {
            this.blueWarrior = null;
        }
    }

    @Override
    public void detach() {
        this.redWarrior = null;
        this.blueWarrior = null;
        this.formerBuilding = null;
        this.nextBuilding = null;
    }

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof ReportWeaponEvent) {
            if ("red".equals(((ReportWeaponEvent) event).getSide()) && null != this.redWarrior) {
                Logger.logWeaponEvent(event, this.redWarrior);
            } else if ("blue".equals(((ReportWeaponEvent) event).getSide()) && null != this.blueWarrior) {
                Logger.logWeaponEvent(event, this.blueWarrior);
            }
        } else if (event instanceof LionRunEvent) {
            if (this.getRedWarrior() instanceof Lion) {
                this.getRedWarrior().acceptEvent(event);
            }
            if (this.getBlueWarrior() instanceof Lion) {
                this.getBlueWarrior().acceptEvent(event);
            }
        }
    }
}

class City extends Building {
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

class Headquarter extends Building {
    private final List<WarriorBuilder> warriorBuilders = new ArrayList<>();
    private int lifeElements;
    private int counter = 1;
    private int i = 0;

    public Headquarter(int lifeElements) {
        super();
        this.lifeElements = lifeElements;
    }

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof BornEvent) {
            // 如果无法制造武士就返回
            WarriorBuilder warriorBuilder = this.warriorBuilders.get(i % warriorBuilders.size());
            Warrior warrior = warriorBuilder.buildWarrior(this.lifeElements, this.counter);
            if (null != warrior) {
                this.lifeElements -= warriorBuilder.getLife();
                warrior.setSide(this.getSide());
                warrior.setHeadquarter(this);
                warrior.setBuilding(this);
                this.counter += 1;
                ++i;
                if ("red".equals(this.getSide())) {
                    this.setRedWarrior(warrior);
                } else {
                    this.setBlueWarrior(warrior);
                }

                Logger.logBornMessage(event, warrior);
            }
        } else if (event instanceof ReportElementsEvent) {
            Logger.logElementEvent(event, this);
        } else if (event instanceof WarriorMarchEvent) {
            WarriorMarchEvent warriorMarchEvent = (WarriorMarchEvent) event;
            if ("red".equals(this.getSide())) {
                // 先把红方的士兵送出去
                warriorMarchEvent.setRedWaitWarrior(this.getRedWarrior());
                this.setRedWarrior(null);

                // 再把蓝方的士兵接过来
                Warrior oldBlueWarrior = this.getBlueWarrior();
                this.setBlueWarrior(this.getNextBuilding().getBlueWarrior());
                if (null != this.getBlueWarrior()) {
                    this.getBlueWarrior().setBuilding(this);
                    Logger.logMarchEvent(event, this.getBlueWarrior());
                    // 如果有两个士兵进入，司令部已被地方占领，宣告游戏结束
                    if (null != oldBlueWarrior) {
                        Logger.logHeadquarterTakenEvent(event, this);
                        warriorMarchEvent.setGameEnd(true);
                    }
                } else {
                    this.setBlueWarrior(oldBlueWarrior);
                }
            } else {
                // 把红方的士兵接过来
                Warrior oldRedWarrior = this.getRedWarrior();
                this.setRedWarrior(warriorMarchEvent.getRedWaitWarrior());
                if (null != this.getRedWarrior()) {
                    this.getRedWarrior().setBuilding(this);
                    Logger.logMarchEvent(event, this.getRedWarrior());
                    // 如果有两个士兵进入，司令部已被地方占领，宣告游戏结束
                    if (null != oldRedWarrior) {
                        Logger.logHeadquarterTakenEvent(event, this);
                        warriorMarchEvent.setGameEnd(true);
                    }
                } else {
                    this.setRedWarrior(oldRedWarrior);
                }

                // 蓝方的士兵已经送出去了
                this.setBlueWarrior(null);
            }
        }
    }

    public void setLifeElements(int lifeElements) {
        this.lifeElements = lifeElements;
    }

    public int getLifeElements() {
        return lifeElements;
    }

    public void addWarriorBuilder(WarriorBuilder warriorBuilder) {
        this.warriorBuilders.add(warriorBuilder);
    }
}

class BornEvent extends Event {
    public BornEvent() {
        super(0);
    }

    @Override
    public void handle() {
        // 武士产生事件
        EventSystem.notify(Headquarter.class, this);
        super.handle();
    }
}

class CityCleanEvent extends Event {
    public CityCleanEvent() {
        super(35);
    }

    @Override
    public void handle() {
        // 清理因为射箭而死亡的没有敌人的城市
        EventSystem.notify(City.class, this);
        super.handle();
    }
}

class CityProduceLifeElementsEvent extends Event {
    public CityProduceLifeElementsEvent() {
        super(20);
    }

    @Override
    public void handle() {
        EventSystem.notify(City.class, this);
        super.handle();
    }
}

class EndEvent extends Event {
    public EndEvent() {
        super(0);
    }
}

abstract class Event {

    private int hour = 0;
    private int minute;

    public Event(int minute) {
        this.minute = minute;
    }

    public void handle() {
        this.hour += 1;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return String.format("%03d:%02d", this.hour, this.minute);
    }

}

class FightEvent extends Event {
    private Warrior activeAttacker;
    private final Stack<Warrior> waitingWarrior = new Stack<>();

    public FightEvent() {
        super(40);
    }

    public void addWaitingWarrior(Warrior warrior) {
        this.waitingWarrior.add(warrior);
    }

    public Warrior getActiveAttacker() {
        return activeAttacker;
    }

    public void setActiveAttacker(Warrior activeAttacker) {
        this.activeAttacker = activeAttacker;
    }

    @Override
    public void handle() {
        // 战斗事件
        EventSystem.notify(City.class, this);
        // 现在开始奖励蓝方的武士，反方向奖励
        while (!this.waitingWarrior.empty()) {
            this.waitingWarrior.pop().getPrize();
        }
        // 完成之后进行生命元的回收工作
        TakeCityLifeElementsEvent takeCityLifeElementsEvent = new TakeCityLifeElementsEvent();
        takeCityLifeElementsEvent.setHour(this.getHour());
        // 产生一个偏差，使得时间对不上，不再打印
        takeCityLifeElementsEvent.setMinute(this.getMinute() - 5);
        EventSystem.notify(City.class, takeCityLifeElementsEvent);
        super.handle();
    }
}

class LionRunEvent extends Event {
    public LionRunEvent() {
        super(5);
    }

    @Override
    public void handle() {
        // 狮子逃跑事件
        EventSystem.notify(Building.class, this);
        super.handle();
    }
}

class ReportElementsEvent extends Event {
    public ReportElementsEvent() {
        super(50);
    }

    @Override
    public void handle() {
        // 司令部报告生命元事件
        EventSystem.notify(Headquarter.class, this);
        super.handle();
    }
}

class ReportWeaponEvent extends Event {
    private String side;

    public ReportWeaponEvent() {
        super(55);
    }

    @Override
    public void handle() {
        // 武士报告拥有武器事件
        this.side = "red";
        EventSystem.notify(Building.class, this);
        this.side = "blue";
        EventSystem.notify(Building.class, this);
        super.handle();
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}

class TakeCityLifeElementsEvent extends Event {
    public TakeCityLifeElementsEvent() {
        super(30);
    }

    @Override
    public void handle() {
        // 武士取走生命元事件
        EventSystem.notify(City.class, this);
        super.handle();
    }
}

class UseBombEvent extends Event {
    public UseBombEvent() {
        super(38);
    }

    @Override
    public void handle() {
        // 武士使用炸弹事件
        EventSystem.notify(City.class, this);
        super.handle();
    }
}

class WarriorMarchEvent extends Event {
    private Warrior redWaitWarrior;
    private boolean gameEnd = false;

    public WarriorMarchEvent() {
        super(10);
    }

    public Warrior getRedWaitWarrior() {
        return redWaitWarrior;
    }

    public void setRedWaitWarrior(Warrior redWaitWarrior) {
        this.redWaitWarrior = redWaitWarrior;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    @Override
    public void handle() {
        this.redWaitWarrior = null;
        EventSystem.notify(Warrior.class, this);
        EventSystem.notify(Building.class, this);
        if (this.gameEnd) {
            EventSystem.notify(Clock.class, new EndEvent());
        }
        super.handle();
    }
}

class WarriorShotEvent extends Event {
    public WarriorShotEvent() {
        super(35);
    }

    @Override
    public void handle() {
        // 武士放箭事件
        EventSystem.notify(City.class, this);
        super.handle();
    }
}

class Flag extends GameObject {
}

class Dragon extends Warrior {
    private double morale;

    public void setMorale(double morale) {
        this.morale = morale;
    }

    @Override
    public String getBornMessage() {
        return super.getBornMessage() + "\nIts morale is " + Settings.numberFormat.format(this.morale);
    }

    @Override
    public void afterFight(Event event, Warrior warrior) {
        super.afterFight(event, warrior);
        // 视敌人的情况增加或减少士气值
        this.morale += warrior.isAlive() ? -0.2 : 0.2;

        if (this.isAlive() && this == ((FightEvent) event).getActiveAttacker() && this.morale > 0.8) {
            Logger.logYellMessage(event);
        }
    }
}

class Iceman extends Warrior {
    private int counter = 0;

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        // 行进需要减少生命值
        if (event instanceof WarriorMarchEvent) {
            ++this.counter;
            if (0 == this.counter % 2) {
                this.setLife(Math.max(1, this.getLife() - 9));
                this.setAttack(this.getAttack() + 20);
            }
        }
    }
}

class Lion extends Warrior {
    private int loyalty;
    private int lifeBeforeFight;

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    @Override
    public void beforeFight() {
        super.beforeFight();
        this.lifeBeforeFight = this.getLife();
    }

    @Override
    public void afterFight(Event event, Warrior warrior) {
        super.afterFight(event, warrior);
        if (!this.isAlive() && warrior.isAlive() && !this.isDeadOfArrow()) {
            warrior.setLife(warrior.getLife() + this.lifeBeforeFight);
        } else if (warrior.isAlive()) {
            this.loyalty -= Settings.ROYALTY_DECAY;
        }
    }

    @Override
    public String getBornMessage() {
        return super.getBornMessage() + "\nIts loyalty is " + this.loyalty;
    }

    @Override
    public void acceptEvent(Event event) {
        super.acceptEvent(event);
        if (event instanceof LionRunEvent) {
            // 忠诚值低于0，并且不在敌方的司令部内
            if (this.loyalty <= 0) {
                if (this.getBuilding() instanceof Headquarter && !this.getSide().equals(this.getBuilding().getSide())) {
                    return;
                }
                Logger.logLionRunAwayEvent(event, this);
                this.die();
            }
        }
    }
}

class Ninja extends Warrior {
    @Override
    public void fightBack(Warrior victim) {
        // Ninja不会回击
    }
}

abstract class Warrior extends GameObject {
    private int number;
    private int life;
    private int attack;

    private final WeaponPack weaponPack = new WeaponPack();

    // 该武士所属的司令部
    private Headquarter headquarter;
    private Building building;

    // 是否由于被箭射击而死
    private boolean isDeadOfArrow = false;

    public Warrior() {
        EventSystem.register(this);
    }

    // AOP的思想
    public void beforeFight() {

    }

    public void afterFight(Event event, Warrior warrior) {
        // 打赢了，如果是蓝方的武士，直接奖励，如果是红方的武士，则等下再奖励
        if (!warrior.isAlive()) {
            if (this.getSide().equals("blue")) {
                this.getPrize();
            } else {
                FightEvent fightEvent = (FightEvent) event;
                fightEvent.addWaitingWarrior(this);
            }
        }
    }

    public void getPrize() {
        // 打赢了 奖赏8个生命值
        int lifeElements = this.headquarter.getLifeElements();
        if (lifeElements >= 8) {
            this.headquarter.setLifeElements(lifeElements - 8);
            this.life += 8;
        }
    }

    public void attack(Warrior victim) {
        // 攻击对方
        victim.setLife(victim.getLife() - this.attack);
        // 使用Sword攻击对方
        this.weaponPack.use(Sword.class, this, victim);
    }

    public void fightBack(Warrior victim) {
        // 攻击对方
        victim.setLife(victim.getLife() - this.attack / 2);
        // 使用Sword攻击对方
        this.weaponPack.use(Sword.class, this, victim);
    }

    // 评估是否使用炸弹
    // 1代表自己死亡，2代表敌方死亡
    public int evaluate(Warrior warrior) {
        int myLife = this.getLife();
        int enemyLife = warrior.getLife();
        Sword sword = ((Sword) this.getWeaponPack().getWeapon(Sword.class));
        enemyLife -= this.attack;
        if (null != sword) {
            enemyLife -= sword.getAttack();
        }

        // 敌人反击
        if (enemyLife > 0 && !(warrior instanceof Ninja)) {
            sword = ((Sword) warrior.getWeaponPack().getWeapon(Sword.class));
            myLife -= warrior.attack / 2;
            if (null != sword) {
                myLife -= sword.getAttack();
            }
        }

        int result = 0;
        if (myLife <= 0) {
            result |= 1;
        }

        if (enemyLife <= 0) {
            result |= 2;
        }

        return result;
    }

    public boolean isAlive() {
        return this.life > 0;
    }

    public void shotArrow(Warrior warrior) {
        this.weaponPack.use(Arrow.class, this, warrior);
    }

    public void useBomb(Warrior warrior) {
        this.weaponPack.use(Bomb.class, this, warrior);
    }

    public boolean hasWeapon(Class<? extends Weapon> weaponClass) {
        return this.weaponPack.hasWeapon(weaponClass);
    }

    @Override
    public void detach() {
        this.headquarter = null;
        this.building = null;
    }

    public void die() {
        // 从建筑中脱离
        this.building.detach(this);
        this.detach();

        // 从事件系统中移除
        EventSystem.unregister(this);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void addWeapon(WeaponPack weaponPack) {
        this.weaponPack.addWeapon(weaponPack);
    }

    public void addWeapon(Weapon weapon) {
        this.weaponPack.addWeapon(weapon);
    }

    public WeaponPack getWeaponPack() {
        return weaponPack;
    }

    public Headquarter getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(Headquarter headquarter) {
        this.headquarter = headquarter;
    }

    public boolean isDeadOfArrow() {
        return isDeadOfArrow;
    }

    public void setDeadOfArrow(boolean deadOfArrow) {
        isDeadOfArrow = deadOfArrow;
    }

    public String getBornMessage() {
        return String.format("%s %s %d born", this.getSide(), this.getName(), this.number);
    }

    @Override
    public String toString() {
        return this.getSide() + " " + this.getName() + " " + this.getNumber();
    }
}

class Wolf extends Warrior {
    @Override
    public void afterFight(Event event, Warrior warrior) {
        super.afterFight(event, warrior);
        if (this.isAlive() && !warrior.isAlive()) {
            // 如果击杀了敌方武士，就把敌方武士的武器拿过来
            addWeapon(warrior.getWeaponPack());
        }
    }
}

class Arrow extends Weapon {

    private int durable = 3;

    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        victim.setLife(victim.getLife() - Settings.ARROW_ATTACK);
        this.durable -= 1;
        return this.durable > 0;
    }

    @Override
    public String toString() {
        return String.format("arrow(%d)", this.durable);
    }
}

class Bomb extends Weapon {
    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        attacker.die();
        victim.die();
        return false;
    }

    @Override
    public String toString() {
        return "bomb";
    }
}

class Sword extends Weapon {
    private int attack;

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public boolean use(Warrior attacker, Warrior victim) {
        victim.setLife(victim.getLife() - this.attack);
        // 攻击后武器变钝
        this.attack = this.attack * 8 / 10;
        return this.attack > 0;
    }

    @Override
    public String toString() {
        return String.format("sword(%d)", this.attack);
    }
}

abstract class Weapon extends GameObject implements Cloneable {
    public abstract boolean use(Warrior attacker, Warrior victim);

    @Override
    protected Weapon clone() throws CloneNotSupportedException {
        return (Weapon) super.clone();
    }
}

class WeaponFactory {
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

class WeaponPack {
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

class Clock extends GameObject {
    private final List<Event> eventQueue = new ArrayList<>();
    private boolean gameEnd = false;

    public void start(int time) {
        EventSystem.register(this);

        eventQueue.add(new BornEvent());
        eventQueue.add(new LionRunEvent());
        eventQueue.add(new WarriorMarchEvent());
        eventQueue.add(new CityProduceLifeElementsEvent());
        eventQueue.add(new TakeCityLifeElementsEvent());
        eventQueue.add(new WarriorShotEvent());
        eventQueue.add(new CityCleanEvent());
        eventQueue.add(new UseBombEvent());
        eventQueue.add(new FightEvent());
        eventQueue.add(new ReportElementsEvent());
        eventQueue.add(new ReportWeaponEvent());

        int i = 0;
        Event event = eventQueue.get(i);

        while (!this.gameEnd && event.getHour() * 60 + event.getMinute() <= time) {
            event.handle();
            ++i;
            event = eventQueue.get(i % eventQueue.size());
        }
    }

    @Override
    public void acceptEvent(Event event) {
        if (event instanceof EndEvent) {
            // 游戏结束了
            this.gameEnd = true;
        }
    }
}

class EventSystem {
    public static final Map<Class<? extends GameObject>, List<GameObject>> gameObjects = new HashMap<>();

    public static void register(GameObject gameObject) {
        Class clazz = gameObject.getClass();
        while (!clazz.getName().equals("GameObject")) {
            if (!gameObjects.containsKey(clazz)) {
                gameObjects.put(clazz, new LinkedList<>());
            }
            EventSystem.gameObjects.get(clazz).add(gameObject);
            clazz = clazz.getSuperclass();
        }
    }

    public static void unregister(GameObject gameObject) {
        Class clazz = gameObject.getClass();
        while (!clazz.getName().equals("GameObject")) {
            EventSystem.gameObjects.get(clazz).remove(gameObject);
            clazz = clazz.getSuperclass();
        }
    }

    public static void notify(Class<? extends GameObject> clazz, Event event) {
        for (GameObject gameObject : EventSystem.gameObjects.getOrDefault(clazz, new LinkedList<>())) {
            gameObject.acceptEvent(event);
        }
    }

    public static void reset() {
        for (List<GameObject> gameObjects : EventSystem.gameObjects.values()) {
            for (GameObject gameObject : gameObjects) {
                gameObject.detach();
            }
        }
        EventSystem.gameObjects.clear();
    }
}

abstract class GameObject {
    private String name;
    private String side;

    public void acceptEvent(Event event) {

    }

    public void detach() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}

class Logger {
    public static void logBornMessage(Event event, Warrior warrior) {
//        System.out.printf("%s %s\n", event, warrior.getBornMessage());
    }

    public static void logYellMessage(Event event) {
        Warrior warrior = ((FightEvent) event).getActiveAttacker();
//        System.out.printf("%s %s yelled in %s\n", event, warrior, warrior.getBuilding().getName());
    }

    public static void logWeaponEvent(Event event, Warrior warrior) {
//        System.out.printf("%s %s has %s\n", event, warrior, warrior.getWeaponPack());
    }

    public static void logMarchEvent(Event event, Warrior warrior) {
//        System.out.printf("%s %s %s %s with %d elements and force %d\n", event, warrior,
//                warrior.getBuilding() instanceof Headquarter ? "reached" : "marched to",
//                warrior.getBuilding().getName(), warrior.getLife(), warrior.getAttack());
    }

    public static void logLionRunAwayEvent(Event event, Warrior warrior) {
//        System.out.printf("%s %s ran away\n", event, warrior);
    }

    public static void logElementEvent(Event event, Headquarter headquarter) {
//        System.out.printf("%s %d elements in %s\n", event, headquarter.getLifeElements(), headquarter.getName());
    }

    public static void logHeadquarterTakenEvent(Event event, Headquarter headquarter) {
//        System.out.printf("%s %s was taken\n", event, headquarter.getName());
    }

    public static void logTakeCityElementsEvent(Event event, Warrior warrior, int elements) {
        if (elements > 0 && 35 != event.getMinute()) {
//            System.out.printf("%s %s earned %d elements for his headquarter\n", event, warrior, elements);
        }
    }

    public static void logShotEvent(Event event, Warrior attacker, Warrior victim) {
//        System.out.printf("%s %s shot", event, attacker);
        if (!victim.isAlive()) {
//            System.out.printf(" and killed %s", victim);
        }
//        System.out.println();
    }

    public static void logBombMessage(Event event, Warrior attacker, Warrior victim) {
//        System.out.printf("%s %s used a bomb and killed %s\n", event, attacker, victim);
    }

    public static void logDiedMessage(Event event, Warrior warrior) {
        if (!warrior.isDeadOfArrow()) {
//            System.out.printf("%s %s was killed in %s\n", event, warrior, warrior.getBuilding().getName());
        }
    }

    public static void logAttackMessage(Event event, Warrior attacker, Warrior victim) {
//        System.out.printf("%s %s attacked %s in %s with %d elements and force %d\n", event, attacker, victim,
//                attacker.getBuilding().getName(), attacker.getLife(), attacker.getAttack());
    }

    public static void logFightBackMessage(Event event, Warrior attacker, Warrior victim) {
        if (!(attacker instanceof Ninja)) {
//            System.out.printf("%s %s fought back against %s in %s\n", event, attacker, victim,
//                    attacker.getBuilding().getName());
        }
    }

    public static void logFlagRaiseEvent(Event event, City city) {
//        System.out.printf("%s %s flag raised in %s\n", event, city.getFlag().getSide(), city.getName());
    }
}

class Settings {
    public static NumberFormat numberFormat = new DecimalFormat();
    public static int ROYALTY_DECAY;
    public static int ARROW_ATTACK;

    static {
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
    }
}

class Main {
    private static final List<Building> cityList = new LinkedList<>();

    public static void main(String[] args) {
        WeaponFactory.addWeapon(new Sword());
        WeaponFactory.addWeapon(new Bomb());
        WeaponFactory.addWeapon(new Arrow());

        Scanner scanner = new Scanner(System.in);

        int cases = scanner.nextInt();

        long startTime = System.currentTimeMillis();    // 获取开始时间

        for (int i = 1; i <= cases; ++i) {
//            System.out.printf("Case %d:\n", i);

            // 初始数据的读入
            int lifeElements = scanner.nextInt();

            int cities = scanner.nextInt();

            Settings.ARROW_ATTACK = scanner.nextInt();

            Settings.ROYALTY_DECAY = scanner.nextInt();

            int time = scanner.nextInt();

            // 构建所有的武士建造者
            WarriorBuilder dragonBuilder = new DragonBuilder();
            WarriorBuilder ninjaBuilder = new NinjaBuilder();
            WarriorBuilder icemanBuilder = new IcemanBuilder();
            WarriorBuilder lionBuilder = new LionBuilder();
            WarriorBuilder wolfBuilder = new WolfBuilder();

            dragonBuilder.setLife(scanner.nextInt());
            ninjaBuilder.setLife(scanner.nextInt());
            icemanBuilder.setLife(scanner.nextInt());
            lionBuilder.setLife(scanner.nextInt());
            wolfBuilder.setLife(scanner.nextInt());

            dragonBuilder.setAttack(scanner.nextInt());
            ninjaBuilder.setAttack(scanner.nextInt());
            icemanBuilder.setAttack(scanner.nextInt());
            lionBuilder.setAttack(scanner.nextInt());
            wolfBuilder.setAttack(scanner.nextInt());

            // 构建红方的司令部
            Headquarter redHeadquarter = new Headquarter(lifeElements);
            redHeadquarter.setName("red headquarter");
            redHeadquarter.setSide("red");
            redHeadquarter.setNumber(0);
            cityList.add(redHeadquarter);

            redHeadquarter.addWarriorBuilder(icemanBuilder);
            redHeadquarter.addWarriorBuilder(lionBuilder);
            redHeadquarter.addWarriorBuilder(wolfBuilder);
            redHeadquarter.addWarriorBuilder(ninjaBuilder);
            redHeadquarter.addWarriorBuilder(dragonBuilder);

            // 构建两个司令部之间的所有城市
            for (int j = 1; j <= cities; ++j) {
                City city = new City();
                city.setNumber(j);
                city.setName("city " + j);
                cityList.add(city);
            }

            // 构建蓝方的司令部
            Headquarter blueHeadquarter = new Headquarter(lifeElements);
            blueHeadquarter.setName("blue headquarter");
            blueHeadquarter.setSide("blue");
            blueHeadquarter.setNumber(cities + 1);
            cityList.add(blueHeadquarter);

            blueHeadquarter.addWarriorBuilder(lionBuilder);
            blueHeadquarter.addWarriorBuilder(dragonBuilder);
            blueHeadquarter.addWarriorBuilder(ninjaBuilder);
            blueHeadquarter.addWarriorBuilder(icemanBuilder);
            blueHeadquarter.addWarriorBuilder(wolfBuilder);

            // 连接所有的城市
            for (int j = 0; j < cityList.size(); ++j) {
                if (j != cityList.size() - 1) {
                    cityList.get(j).setNextBuilding(cityList.get(j + 1));
                }
                if (0 != j) {
                    cityList.get(j).setFormerBuilding(cityList.get(j - 1));
                }
            }

            // 时钟开始模拟
            Clock clock = new Clock();
            clock.start(time);

            EventSystem.reset();
            cityList.clear();
        }

        long endTime = System.currentTimeMillis();    // 获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    // 输出程序运行时间
    }
}
