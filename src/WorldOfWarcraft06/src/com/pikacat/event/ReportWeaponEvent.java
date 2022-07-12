package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.Building;

public class ReportWeaponEvent extends Event {
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
