package ru.unn.agile.WeightConverter.Model;

import org.junit.Test;
import static org.junit.Assert.*;

public class WeightConverterTest {
    private static final double DELTA = 1e-9;
    private WeightConverter converter = new WeightConverter();

    @Test
    public void whenConvertZero() {
        Weight weight = new Weight(0.0, WeightUnit.KILOGRAM);
        Weight expectedWeight = new Weight(0.0, WeightUnit.OUNCE);

        Weight actualWeight = converter.convert(weight, WeightUnit.OUNCE);

        assertEquals(expectedWeight, actualWeight);
    }

    @Test
    public void canConvertGramsToKGrams() {
        Weight weight = new Weight(1000.0, WeightUnit.GRAM);
        Weight expectedWeight = new Weight(1.0, WeightUnit.KILOGRAM);

        Weight actualWeight = converter.convert(weight, WeightUnit.KILOGRAM);

        assertEquals(expectedWeight, actualWeight);
    }

    @Test (expected = IllegalArgumentException.class)
    public void isConvertorWithIncorrectParametersCrushed() {
        Weight weight = new Weight(-1.0, WeightUnit.GRAM);

        Weight actualWeight = converter.convert(weight, WeightUnit.KILOGRAM);
    }

    @Test
    public void  canConvertKGramsToCentner() {
        Weight weight = new Weight(100.0, WeightUnit.KILOGRAM);
        Weight expectedWeight = new Weight(1.0, WeightUnit.CENTNER);

        Weight actualWeight = converter.convert(weight, WeightUnit.CENTNER);

        assertEquals(expectedWeight, actualWeight);
    }

    @Test
    public void  canConvertString() {
        String inputValue = "100.0";

        String outputValue = converter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.CENTNER);

        assertEquals("1.0", outputValue);
    }

    @Test
    public void  canConvertKGramsToTon() {
        Weight inputWeight = new Weight(10000.0, WeightUnit.KILOGRAM);
        Weight expectedWeight = new Weight(10.0, WeightUnit.TON);

        Weight actualWeight = converter.convert(inputWeight, WeightUnit.TON);

        assertEquals(expectedWeight, actualWeight);
    }

    @Test
    public void  canConvertOunceToKGrams() {
        Weight inputWeight = new Weight(35.27, WeightUnit.OUNCE);
        Weight expectedWeight = new Weight(1.0, WeightUnit.KILOGRAM);

        Weight actualWeight = converter.convert(inputWeight, WeightUnit.KILOGRAM);

        assertEquals(expectedWeight, actualWeight);
    }

    @Test
    public void  canUseHashCode() {
        Weight inputWeight = new Weight(1.0, WeightUnit.GRAM);

        assertNotNull(inputWeight.hashCode());
    }

    @Test
    public void  canConvertPoundToKGrams() {
        Weight inputWeight = new Weight(2.2, WeightUnit.POUND);
        Weight expectedWeight = new Weight(1.0, WeightUnit.KILOGRAM);

        Weight actualWeight = converter.convert(inputWeight, WeightUnit.KILOGRAM);

        assertEquals(expectedWeight, actualWeight);
    }

    @Test
    public void  canConvertStoneToKGrams() {
        Weight inputWeight = new Weight(0.15, WeightUnit.STONE);
        Weight expectedWeight = new Weight(1.0, WeightUnit.KILOGRAM);

        Weight actualWeight = converter.convert(inputWeight, WeightUnit.KILOGRAM);

        assertEquals(expectedWeight, actualWeight);
    }

    @Test
    public void areResultsTheSame() {
        Weight weight1 = new Weight(10, WeightUnit.CENTNER);
        Weight weight2 = new Weight(1, WeightUnit.TON);

        Weight res1 = converter.convert(weight1, WeightUnit.KILOGRAM);
        Weight res2 = converter.convert(weight2, WeightUnit.KILOGRAM);

        assertEquals(res1, res2);
    }

    @Test
    public void canCreateLengthClassWithInitialValues() {
        Weight weight = new Weight(1, WeightUnit.GRAM);
        assertNotNull(weight);
    }
}
