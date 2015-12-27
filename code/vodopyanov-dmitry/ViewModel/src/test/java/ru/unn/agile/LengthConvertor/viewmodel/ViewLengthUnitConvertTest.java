package ru.unn.agile.LengthConvertor.viewmodel;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ru.unn.agile.LengthConvertor.Model.LengthUnit;

import java.util.Collection;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ViewLengthUnitConvertTest {
    private ViewModel viewModel;
    private String value;
    private String convertedValue;
    private LengthUnit unitOutput;

    public ViewLengthUnitConvertTest(final String value, final String convertedValue,
                                 final LengthUnit unitOutput) {
        this.value = value;
        this.convertedValue = convertedValue;
        this.unitOutput = unitOutput;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Parameters
    public static Collection<Object[]> meterToUnits() {
        return Arrays.asList(new Object[][]{
                {"1", "39.370079050158104", LengthUnit.INCH},
                {"1", "3.280839895013123", LengthUnit.FOOT},
                {"1", "1.0936132995336978", LengthUnit.YARD},
                {"1", "6.213711883763124E-4", LengthUnit.MILE},
                {"1", "1.0", LengthUnit.METER},
                {"1", "0.001", LengthUnit.KMETER},
                {"1", "100.0", LengthUnit.CMETER},
        });
    }

    @Test
    public void canConvertOneMeterToAllUnits() {
        viewModel.inputValueProperty().set(value);
        viewModel.inputUnitProperty().set(LengthUnit.METER);
        viewModel.outputUnitProperty().set(unitOutput);

        viewModel.convert();

        assertEquals(convertedValue, viewModel.outputValueProperty().get());
    }
}
