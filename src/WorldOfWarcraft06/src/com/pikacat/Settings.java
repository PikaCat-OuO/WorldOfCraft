package com.pikacat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Settings {
    public static NumberFormat numberFormat = new DecimalFormat();
    public static int ROYALTY_DECAY;
    public static int ARROW_ATTACK;
    public static int END_TIME;

    static {
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
    }
}
