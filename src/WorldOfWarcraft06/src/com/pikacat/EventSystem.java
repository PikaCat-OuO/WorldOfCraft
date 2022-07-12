package com.pikacat;

import com.pikacat.event.Event;

import java.util.LinkedList;
import java.util.List;

// 基于观察者模式的事件系统
public class EventSystem {
    public static final List<GameObject> gameObjects = new LinkedList<>();

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

    public static void notify(Event event) {
        for (int i = 0; i < EventSystem.gameObjects.size(); ++i) {
            EventSystem.gameObjects.get(i).acceptEvent(event);
        }
    }

    public static void reset() {
        for (GameObject gameObject : EventSystem.gameObjects) {
            gameObject.detach();
        }
        EventSystem.gameObjects.clear();
    }
}