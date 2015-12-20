package ru.unn.agile.PercentAccretion.Model;

public class ComplexPercentAccretion extends PercentAccretion {
    @Override
    public double calculate(final PercentData data) {
        if (checkArguments(data)) {
            return data.getInitialSum() * Math.pow(1 + data.getPercentRate()
                    * PercentData.FROM_PERCENT, data.getCountOfYears());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
