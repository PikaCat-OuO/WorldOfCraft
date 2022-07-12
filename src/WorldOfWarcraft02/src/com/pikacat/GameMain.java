package com.pikacat;

import com.pikacat.builder.*;
import com.pikacat.building.Headquarter;
import com.pikacat.weapon.Arrow;
import com.pikacat.weapon.Bomb;
import com.pikacat.weapon.Sword;
import com.pikacat.weapon.WeaponFactory;

import java.util.Scanner;

public class GameMain {

    public static void main(String[] args) {
        WeaponFactory.addWeapon(new Sword());
        WeaponFactory.addWeapon(new Bomb());
        WeaponFactory.addWeapon(new Arrow());

        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();

        for (int i = 0; i < cases; ++i) {
            System.out.println("Case:" + (i + 1));
            int lifeElements = scanner.nextInt();

            int dragonLife = scanner.nextInt();
            int ninjaLife = scanner.nextInt();
            int icemanLife = scanner.nextInt();
            int lionLife = scanner.nextInt();
            int wolfLife = scanner.nextInt();
            int minLife = Math.min(dragonLife, Math.min(ninjaLife, Math.min(icemanLife, Math.min(lionLife, wolfLife))));

            Headquarter redHeadquarter = new Headquarter(lifeElements);
            redHeadquarter.setName("red headquarter");
            redHeadquarter.setSide("red");

            WarriorBuilder noWarriorBuilder = new NoWarriorBuilder();
            noWarriorBuilder.setLife(minLife);
            WarriorBuilder dragonBuilder = new DragonBuilder();
            dragonBuilder.setLife(dragonLife);
            WarriorBuilder ninjaBuilder = new NinjaBuilder();
            ninjaBuilder.setLife(ninjaLife);
            WarriorBuilder icemanBuilder = new IcemanBuilder();
            icemanBuilder.setLife(icemanLife);
            WarriorBuilder lionBuilder = new LionBuilder();
            lionBuilder.setLife(lionLife);
            WarriorBuilder wolfBuilder = new WolfBuilder();
            wolfBuilder.setLife(wolfLife);

            noWarriorBuilder.setNext(icemanBuilder);
            icemanBuilder.setNext(lionBuilder);
            lionBuilder.setNext(wolfBuilder);
            wolfBuilder.setNext(ninjaBuilder);
            ninjaBuilder.setNext(dragonBuilder);
            dragonBuilder.setNext(noWarriorBuilder);

            redHeadquarter.setWarriorBuilder(noWarriorBuilder);


            Headquarter blueHeadquarter = new Headquarter(lifeElements);
            blueHeadquarter.setName("blue headquarter");
            blueHeadquarter.setSide("blue");

            noWarriorBuilder = new NoWarriorBuilder();
            noWarriorBuilder.setLife(minLife);
            dragonBuilder = new DragonBuilder();
            dragonBuilder.setLife(dragonLife);
            ninjaBuilder = new NinjaBuilder();
            ninjaBuilder.setLife(ninjaLife);
            icemanBuilder = new IcemanBuilder();
            icemanBuilder.setLife(icemanLife);
            lionBuilder = new LionBuilder();
            lionBuilder.setLife(lionLife);
            wolfBuilder = new WolfBuilder();
            wolfBuilder.setLife(wolfLife);

            noWarriorBuilder.setNext(lionBuilder);
            lionBuilder.setNext(dragonBuilder);
            dragonBuilder.setNext(ninjaBuilder);
            ninjaBuilder.setNext(icemanBuilder);
            icemanBuilder.setNext(wolfBuilder);
            wolfBuilder.setNext(noWarriorBuilder);

            blueHeadquarter.setWarriorBuilder(noWarriorBuilder);

            EventSystem.register(redHeadquarter);
            EventSystem.register(blueHeadquarter);

            Clock clock = new Clock();
            clock.start();

            EventSystem.reset();
        }
    }
}
