package ru.unn.agile.Triangle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TriangleViewModelTests {
    private TriangleViewModel viewModel;
    @Before
    public void setUp() {
        viewModel = new TriangleViewModel();
    }

    private void setExampleValues() {
        viewModel.setCoordinate1X("9.65");
        viewModel.setCoordinate1Y("5");
        viewModel.setCoordinate1Z("-2.0");
        viewModel.setCoordinate2X("3");
        viewModel.setCoordinate2Y("10");
        viewModel.setCoordinate2Z("-1");
        viewModel.setCoordinate3X("-0.5");
        viewModel.setCoordinate3Y("1.5");
        viewModel.setCoordinate3Z("1.5");
        viewModel.setValueToCalculate(ValuesToCalculate.PERIMETER);
    }

    @Test
    public void byDefaultCanSetFieldsValues() {
        assertEquals(viewModel.getCoordinate1X(), "");
        assertEquals(viewModel.getCoordinate1Y(), "");
        assertEquals(viewModel.getCoordinate1Z(), "");
        assertEquals(viewModel.getCoordinate2X(), "");
        assertEquals(viewModel.getCoordinate2Y(), "");
        assertEquals(viewModel.getCoordinate2Z(), "");
        assertEquals(viewModel.getCoordinate3X(), "");
        assertEquals(viewModel.getCoordinate3Y(), "");
        assertEquals(viewModel.getCoordinate3Z(), "");
        assertEquals(viewModel.getValueToCalculate(), ValuesToCalculate.PERIMETER);
        assertEquals(viewModel.getResult(), "0.0");
    }

    @Test
    public void byDefaultStatusIsWaiting() {
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void byDefaultCalculateButtonIsDisabled() {
        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void enterExampleValuesIsFinishedWithRightStatus() throws Exception {
        setExampleValues();
        viewModel.checkInput();
        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void computeExampleIsFinishedWithRightStatus() throws Exception {
        setExampleValues();
        viewModel.checkInput();
        viewModel.compute();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void whenEnterCoordinatesCalculateButtonIsEnabled() {
        viewModel.setCoordinate1X("5");
        assertTrue(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void whenNotAllFieldsAreCompletedStatusIsWaiting() {
        viewModel.setCoordinate1X("4");
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void whenEnterStringStatusIsBadFormat() {
        viewModel.setCoordinate1X("Ooo");
        viewModel.checkInput();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void whenFieldsAreEmptyStatusIsWaiting() {
        assertEquals(Status.WAITING, viewModel.getStatus());
    }
}
