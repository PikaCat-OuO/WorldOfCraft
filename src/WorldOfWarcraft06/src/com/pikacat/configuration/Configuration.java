package com.pikacat.configuration;

import com.pikacat.Clock;
import com.pikacat.Settings;
import com.pikacat.builder.WarriorBuilder;
import com.pikacat.building.Building;
import com.pikacat.building.City;
import com.pikacat.building.Headquarter;
import com.pikacat.event.Event;
import com.pikacat.weapon.Weapon;
import com.pikacat.weapon.WeaponFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;

public class Configuration {
    static {
        try {
            Configuration.root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse("src/config.xml").getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node root;
    private static final Map<String, WarriorBuilder> warriorBuilders = new LinkedHashMap<>();

    private static List<Node> getChildren(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Node> children = new ArrayList<>();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getParentNode().equals(root)) {
                children.add(node);
            }
        }
        return children;
    }

    private static Node getNodeByName(Node current, String name) {
        NodeList nodeList = current.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getParentNode().equals(current) &&
                    node.getNodeName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    private static Node getNodeByName(String name) {
        return getNodeByName(root, name);
    }

    private static List<Node> getConfigByName(Node current, String name) {
        return getChildren(getNodeByName(current, name));
    }

    private static List<Node> getConfigByName(String name) {
        return getConfigByName(root, name);
    }

    // ??????config.xml????????????????????????
    public static void initGame() {
        try {
            // ?????????????????????
            for (Node node : getConfigByName("Weapon")) {
                String classPath = node.getAttributes().getNamedItem("ClassPath").getNodeValue();
                WeaponFactory.addWeapon((Weapon) Class.forName(classPath).getConstructor().newInstance());
            }
            // ????????????????????????
            for (Node node : getConfigByName("WarriorBuilder")) {
                String classPath = node.getAttributes().getNamedItem("ClassPath").getNodeValue();
                WarriorBuilder warriorBuilder = (WarriorBuilder) Class.forName(classPath).getConstructor().newInstance();
                for (Node weapon : getConfigByName(node, "Weapons")) {
                    warriorBuilder.addInitialWeapon(
                            Integer.parseInt(weapon.getAttributes().getNamedItem("Add").getNodeValue()),
                            Integer.parseInt(weapon.getAttributes().getNamedItem("Mod").getNodeValue()));
                }
                Configuration.warriorBuilders.put(node.getNodeName(), warriorBuilder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ????????????????????????
    public static SimpleEntry<List<Building>, Clock> configGame(Scanner scanner) {
        List<Building> cityList = new LinkedList<>();

        // ?????????????????????
        int lifeElements = scanner.nextInt();
        int cities = scanner.nextInt();
        Settings.ARROW_ATTACK = scanner.nextInt();
        Settings.ROYALTY_DECAY = scanner.nextInt();
        Settings.END_TIME = scanner.nextInt();

        // ??????????????????????????????
        for (Map.Entry<String, WarriorBuilder> entry : Configuration.warriorBuilders.entrySet()) {
            entry.getValue().setLife(scanner.nextInt());
        }

        for (Map.Entry<String, WarriorBuilder> entry : Configuration.warriorBuilders.entrySet()) {
            entry.getValue().setAttack(scanner.nextInt());
        }

        // ????????????????????????
        Headquarter redHeadquarter = new Headquarter(lifeElements);
        redHeadquarter.setName("red headquarter");
        redHeadquarter.setSide("red");
        redHeadquarter.setNumber(0);
        cityList.add(redHeadquarter);

        for (Node node : getConfigByName(getNodeByName("Headquarter"), "Red")) {
            redHeadquarter.addWarriorBuilder(warriorBuilders.get(node.getNodeName()));
        }

        // ??????????????????????????????????????????
        for (int j = 1; j <= cities; ++j) {
            City city = new City();
            city.setNumber(j);
            city.setName("city " + j);
            cityList.add(city);
        }

        // ????????????????????????
        Headquarter blueHeadquarter = new Headquarter(lifeElements);
        blueHeadquarter.setName("blue headquarter");
        blueHeadquarter.setSide("blue");
        blueHeadquarter.setNumber(cities + 1);
        cityList.add(blueHeadquarter);

        for (Node node : getConfigByName(getNodeByName("Headquarter"), "Blue")) {
            blueHeadquarter.addWarriorBuilder(warriorBuilders.get(node.getNodeName()));
        }

        // ?????????????????????
        for (int j = 0; j < cityList.size(); ++j) {
            if (j != cityList.size() - 1) {
                cityList.get(j).setNextBuilding(cityList.get(j + 1));
            }
            if (0 != j) {
                cityList.get(j).setFormerBuilding(cityList.get(j - 1));
            }
        }

        // ??????????????????
        Clock clock = new Clock();
        try {
            for (Node node : getConfigByName("Event")) {
                String classPath = node.getAttributes().getNamedItem("ClassPath").getNodeValue();
                Event event = (Event) Class.forName(classPath).getConstructor().newInstance();
                clock.addEvent(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SimpleEntry<>(cityList, clock);
    }
}
