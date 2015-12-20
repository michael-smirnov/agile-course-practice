package ru.unn.agile.PercentAccretion.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PercentDataTest {
    private PercentData data;

    @Before
    public void setData() {
        data = new PercentData();
        data.setInitialSum(100);
        data.setPercentRate(10);
        data.setCountOfYears(2);
    }

    @Test
    public void canGetInitialSum() {
        double expectedValue = 100.0;
        double delta = 0.0001;

        assertEquals(expectedValue, data.getInitialSum(), delta);
    }

    @Test
    public void canGetPercentRate() {
        double expectedValue = 10.0;
        double delta = 0.0001;

        assertEquals(expectedValue, data.getPercentRate(), delta);
    }

    @Test
    public void canGetCountOfYears() {
        double expectedValue = 2.0;
        double delta = 0.0001;

        assertEquals(expectedValue, data.getCountOfYears(), delta);
    }

    @Test
    public void canSetInitialSum() {
        double expectedValue = 500.0;
        double delta = 0.0001;

        data.setInitialSum(500);

        assertEquals(expectedValue, data.getInitialSum(), delta);
    }

    @Test
    public void canSetPercentRate() {
        double expectedValue = 50.0;
        double delta = 0.0001;

        data.setPercentRate(50);

        assertEquals(expectedValue, data.getPercentRate(), delta);
    }

    @Test
    public void canSetCountOfYears() {
        double expectedValue = 3.0;
        double delta = 0.0001;

        data.setCountOfYears(3);

        assertEquals(expectedValue, data.getCountOfYears(), delta);
    }
}
