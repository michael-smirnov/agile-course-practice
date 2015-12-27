package ru.unn.agile.WeightConverter.Model;

public enum WeightUnit {
    OUNCE(35.27),
    POUND(2.20),
    STONE(0.15),
    GRAM(1000.0),
    KILOGRAM(1.0),
    CENTNER(0.01),
    TON(0.001);

    private final double multiplierKGram;

    WeightUnit(final double multiplierKGram) {
        this.multiplierKGram = multiplierKGram;
    }

    public double getMultiplier() {
        return multiplierKGram;
    }
}
