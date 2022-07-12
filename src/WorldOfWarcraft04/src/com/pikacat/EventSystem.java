package com.pikacat;

import com.pikacat.event.Event;

import java.util.LinkedList;
import java.util.List;

// 基于观察者模式的事件系统
public class EventSystem {
    private static final List<GameObject> gameObjects = new LinkedList<>();

    public static void register(GameObject gameObject) {
        EventSystem.gameObjects.add(gameObject);
    }

    public static void unregister(GameObject gameObject) {
        EventSystem.gameObjects.remove(gameObject);
    }

    public static void notify(Class<? extends GameObject> clazz, Event event) {
        for (int i = 0; i < EventSystem.gameObjects.size(); ++i) {
            GameObject gameObject = EventSystem.gameObjects.get(i);
            if (clazz.isAssignableFrom(gameObject.getClass())) {
                gameObject.acceptEvent(event);
            }
        }
    }

    public static void reset() {
        EventSystem.gameObjects.clear();
    }
}
