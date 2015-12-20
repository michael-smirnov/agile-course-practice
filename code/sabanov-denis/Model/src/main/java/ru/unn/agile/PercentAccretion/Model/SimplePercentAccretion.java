package ru.unn.agile.PercentAccretion.Model;

public class SimplePercentAccretion extends PercentAccretion {
    @Override
    public double calculate(final PercentData data) {
        if (checkArguments(data)) {
            return data.getInitialSum() * (1 + data.getPercentRate()
                    * PercentData.FROM_PERCENT * data.getCountOfYears());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
