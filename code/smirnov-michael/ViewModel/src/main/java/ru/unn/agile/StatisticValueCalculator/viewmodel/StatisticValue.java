package ru.unn.agile.StatisticValueCalculator.viewmodel;

public enum StatisticValue {
    MEAN("Mean"),
    VARIANCE("Variance"),
    PROBABILITY("Probability", StatisticParameter.EVENT),
    RAW_MOMENT("Raw moment", StatisticParameter.ORDER),
    CENTRAL_MOMENT("Central moment", StatisticParameter.ORDER);

    private final String name;
    private final StatisticParameter parameter;

    StatisticValue(final String name) {
        this(name, null);
    }

    StatisticValue(final String name, final StatisticParameter parameter) {
        this.name = name;
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public StatisticParameter getParameter() {
        return parameter;
    }
}
