package com.pikacat;

import com.pikacat.building.Building;
import com.pikacat.configuration.Configuration;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Configuration.initGame();

        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();

        for (int i = 1; i <= cases; ++i) {
            System.out.printf("Case %d:\n", i);

            SimpleEntry<List<Building>, Clock> gameInfo = Configuration.configGame(scanner);

            // 时钟开始模拟
            gameInfo.getValue().start();

            EventSystem.reset();
            gameInfo.getKey().clear();
        }
    }
}
