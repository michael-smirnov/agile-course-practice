package ru.unn.agile.HypothecCalculator.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Rounder {

    private Rounder() { }

    public static double roundMoneySum(final double sum) {
        return new BigDecimal("" + sum).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
