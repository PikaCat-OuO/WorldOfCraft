package com.pikacat;

import com.pikacat.builder.*;
import com.pikacat.building.Headquarter;

import java.util.Scanner;

public class GameMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();

        for (int i = 0; i < cases; ++i) {
            System.out.println("Case:" + (i + 1));
            int lifeElements = scanner.nextInt();

            WarriorBuilder dragonBuilder = new DragonBuilder();
            dragonBuilder.setLife(scanner.nextInt());
            WarriorBuilder ninjaBuilder = new NinjaBuilder();
            ninjaBuilder.setLife(scanner.nextInt());
            WarriorBuilder icemanBuilder = new IcemanBuilder();
            icemanBuilder.setLife(scanner.nextInt());
            WarriorBuilder lionBuilder = new LionBuilder();
            lionBuilder.setLife(scanner.nextInt());
            WarriorBuilder wolfBuilder = new WolfBuilder();
            wolfBuilder.setLife(scanner.nextInt());

            Headquarter redHeadquarter = new Headquarter(lifeElements);
            redHeadquarter.setName("red headquarter");
            redHeadquarter.setSide("red");
            redHeadquarter.addWarriorBuilder(icemanBuilder);
            redHeadquarter.addWarriorBuilder(lionBuilder);
            redHeadquarter.addWarriorBuilder(wolfBuilder);
            redHeadquarter.addWarriorBuilder(ninjaBuilder);
            redHeadquarter.addWarriorBuilder(dragonBuilder);

            Headquarter blueHeadquarter = new Headquarter(lifeElements);
            blueHeadquarter.setName("blue headquarter");
            blueHeadquarter.setSide("blue");
            blueHeadquarter.addWarriorBuilder(lionBuilder);
            blueHeadquarter.addWarriorBuilder(dragonBuilder);
            blueHeadquarter.addWarriorBuilder(ninjaBuilder);
            blueHeadquarter.addWarriorBuilder(icemanBuilder);
            blueHeadquarter.addWarriorBuilder(wolfBuilder);

            EventSystem.register(redHeadquarter);
            EventSystem.register(blueHeadquarter);

            Clock clock = new Clock();
            clock.start();

            EventSystem.reset();
        }
    }
}
