package ru.unn.agile.Polinom.Model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class PolinomCreateTest {
    private Polinom polinom;
    private final double[] inputCoefficients;
    private final double[] expectedCoefficients;

    public PolinomCreateTest(final double[] inputCoefficients,
        final double[] expectedCoefficients) {
        this.inputCoefficients = inputCoefficients;
        this.expectedCoefficients = expectedCoefficients;
    }

    @Parameterized.Parameters
    public static List<Object[]> inputAndExpectedOutput() {
        return Arrays.asList(new Object[][] {
            {new double[]{0.0}, new double[]{0.0}},
            {new double[]{0.0, 15.0}, new double[]{0.0, 15.0}},
            {new double[]{15.0, 0.0}, new double[]{15.0}},
            {new double[]{0.0, 15.0, 0.0, 25.0, 0.0, 0.0}, new double[]{0.0, 15.0, 0.0, 25.0}},
            {new double[]{11253.0, 5564.0, 0.0025, 0.000065, 1155526445.0},
            new double[]{11253.0, 5564.0, 0.0025, 0.000065, 1155526445.0}},
            {new double[]{-51.52}, new double[]{-51.52}},
            {new double[]{-15.1566, 63.0, -0.0005, 0.0, 0.0}, new double[]{-15.1566, 63.0, -0.0005}}
        });
    }

    @Before
    public void initPolinom() {
        polinom = new Polinom(inputCoefficients);
    }

    @Test
    public void canCreatePolinom() {
        Assert.assertTrue(Arrays.equals(polinom.getCoefficients(), expectedCoefficients));
    }
}
