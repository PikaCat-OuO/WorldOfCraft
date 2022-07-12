package com.pikacat;

import com.pikacat.event.Event;

import java.util.HashMap;
import java.util.Map;

public class EventSystem {
    private static final Map<String, GameObject> gameObjectMap = new HashMap<>();

    public static void register(GameObject gameObject) {
        EventSystem.gameObjectMap.put(gameObject.getName(), gameObject);
    }

    public static void unregister(GameObject gameObject) {
        EventSystem.gameObjectMap.remove(gameObject.getName());
    }

    public static void notify(String name, Event event) {
        if (EventSystem.gameObjectMap.containsKey(name)) {
            EventSystem.gameObjectMap.get(name).acceptEvent(event);
        }
    }

    public static boolean hasGameObject() {
        return !EventSystem.gameObjectMap.isEmpty();
    }

    public static void reset() {
        EventSystem.gameObjectMap.clear();
    }
}
