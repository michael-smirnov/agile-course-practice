package ru.unn.agile.PercentAccretion.Model;

public class PercentData {
    private double initialSum;
    private double percentRate;
    private int countOfYears;
    public static final double FROM_PERCENT = 0.01;

    public PercentData() {
        initialSum = 0;
        percentRate = 0;
        countOfYears = 0;
    }

    public void setInitialSum(final double newInitialSum) {
        initialSum = newInitialSum;
    }

    public void setPercentRate(final double newPercentRate) {
        percentRate = newPercentRate;
    }

    public void setCountOfYears(final int newCountOfYears) {
        countOfYears = newCountOfYears;
    }

    public double getInitialSum() {
        return initialSum;
    }

    public double getPercentRate() {
        return percentRate;
    }

    public int getCountOfYears() {
        return countOfYears;
    }
}
