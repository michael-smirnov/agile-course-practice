package ru.unn.agile.PercentAccretion.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PercentAccretionTest {
    private PercentData data;
    private PercentAccretionFactory percentAccretionFactory;

    @Before
    public void initializePercentData() {
        data = new PercentData();
        data.setInitialSum(100);
        data.setPercentRate(50);
        data.setCountOfYears(1);
        percentAccretionFactory = new PercentAccretionFactory();
    }

    @Test
    public void canCalculateSumWithSimplePercent() {
        double expectedSum = 150.0;
        PercentAccretion simplePercentCounter = percentAccretionFactory.create(
                PercentAccretionFactory.AccretionType.SIMPLE_PERCENT_SUM);
        double actualSum = simplePercentCounter.calculate(data);
        double delta = 0.00001;

        assertEquals(expectedSum, actualSum, delta);
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionWhenWrongArgInSimplePercent() {
        data.setPercentRate(-1);
        PercentAccretion simplePercentCounter = percentAccretionFactory.create(
                PercentAccretionFactory.AccretionType.SIMPLE_PERCENT_SUM);
        simplePercentCounter.calculate(data);
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionWhenWrongArgInComplexPercent() {
        data.setPercentRate(-1);
        PercentAccretion complexPercentCounter = percentAccretionFactory.create(
                PercentAccretionFactory.AccretionType.COMPLEX_PERCENT_SUM);
        complexPercentCounter.calculate(data);
    }

    @Test
    public void canCalculateSumWithComplexPercent() {
        double expectedValue = 150.0;
        PercentAccretion complexPercentCounter = percentAccretionFactory.create(
                PercentAccretionFactory.AccretionType.COMPLEX_PERCENT_SUM);
        double actualValue = complexPercentCounter.calculate(data);
        double delta = 0.00001;

        assertEquals(expectedValue, actualValue, delta);
    }

    @Test
    public void canGetSimplePercentAccretionFromFactory() {
        assertEquals(SimplePercentAccretion.class,
                     percentAccretionFactory.create(PercentAccretionFactory.
                             AccretionType.SIMPLE_PERCENT_SUM).getClass());
    }

    @Test
    public void canGetComplexPercentAccretionFromFactory() {
        assertEquals(ComplexPercentAccretion.class,
                     percentAccretionFactory.create(PercentAccretionFactory.
                             AccretionType.COMPLEX_PERCENT_SUM).getClass());
    }
}
