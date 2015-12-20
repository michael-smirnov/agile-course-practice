package ru.unn.agile.PercentAccretion.Model;

public abstract class PercentAccretion {
    public abstract double calculate(final PercentData data);

    public boolean checkArguments(final PercentData data) {
        return data.getInitialSum() >= 0 && data.getPercentRate() >= 0
                && data.getCountOfYears() >= 0;
    }
}
