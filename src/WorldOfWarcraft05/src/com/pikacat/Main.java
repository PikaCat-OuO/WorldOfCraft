package com.pikacat;

import com.pikacat.builder.*;
import com.pikacat.building.Building;
import com.pikacat.building.City;
import com.pikacat.building.Headquarter;
import com.pikacat.weapon.Arrow;
import com.pikacat.weapon.Bomb;
import com.pikacat.weapon.Sword;
import com.pikacat.weapon.WeaponFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<Building> cityList = new LinkedList<>();

    public static void main(String[] args) {
        WeaponFactory.addWeapon(new Sword());
        WeaponFactory.addWeapon(new Bomb());
        WeaponFactory.addWeapon(new Arrow());

        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();

        for (int i = 1; i <= cases; ++i) {
            System.out.printf("Case %d:\n", i);

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
    }
}
