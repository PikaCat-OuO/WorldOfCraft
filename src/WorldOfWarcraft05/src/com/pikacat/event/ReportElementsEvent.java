package com.pikacat.event;

import com.pikacat.EventSystem;
import com.pikacat.building.Headquarter;

public class ReportElementsEvent extends Event {
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
