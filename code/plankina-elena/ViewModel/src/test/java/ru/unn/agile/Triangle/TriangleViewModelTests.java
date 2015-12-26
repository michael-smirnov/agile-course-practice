package ru.unn.agile.Triangle;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Triangle.Model.TriangleExceptions;
import ru.unn.agile.TriangleViewModel.Status;
import ru.unn.agile.TriangleViewModel.TriangleViewModel;
import ru.unn.agile.TriangleViewModel.ValuesToCalculate;

import static org.junit.Assert.*;

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
        viewModel.setValueToCalculate(ValuesToCalculate.MEDIANS);
    }
    @Test
    public void byDefaultCoordinate1XisEmptyString() {
        assertEquals(viewModel.getCoordinate1X(), "");
    }

    @Test
    public void byDefaultCoordinate1YisEmptyString() {
        assertEquals(viewModel.getCoordinate1Y(), "");
    }

    @Test
    public void byDefaultCoordinate1ZisEmptyString() {
        assertEquals(viewModel.getCoordinate1Z(), "");
    }

    @Test
    public void byDefaultCoordinate2XisEmptyString() {
        assertEquals(viewModel.getCoordinate2X(), "");
    }

    @Test
    public void byDefaultCoordinate2YisEmptyString() {
        assertEquals(viewModel.getCoordinate2Y(), "");
    }

    @Test
    public void byDefaultCoordinate2ZisEmptyString() {
        assertEquals(viewModel.getCoordinate2Z(), "");
    }

    @Test
    public void byDefaultCoordinate3XisEmptyString() {
        assertEquals(viewModel.getCoordinate3X(), "");
    }

    @Test
    public void byDefaultCoordinate3YisEmptyString() {
        assertEquals(viewModel.getCoordinate3Y(), "");
    }

    @Test
    public void byDefaultCoordinate3ZisEmptyString() {
        assertEquals(viewModel.getCoordinate3Z(), "");
    }

    @Test
    public void byDefaultResultIsZero() {
        assertEquals(viewModel.getResult(), "0.0");
    }

    @Test
    public void byDefaultValueToCalculateIsMEDIANS() {
        assertEquals(viewModel.getValueToCalculate(), ValuesToCalculate.MEDIANS);
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
        viewModel.checkInputAndChangeStateIfOK();
        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void computeExampleIsFinishedWithRightStatus() throws Exception {
        setExampleValues();
        viewModel.compute();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void whenAllFieldsAreCompletedCorrectlyButtonIsEnabled() {
        setExampleValues();
        viewModel.checkInputAndChangeStateIfOK();
        assertTrue(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void whenEnterStringStatusIsBadFormat() {
        viewModel.setCoordinate1X("Ooo");
        viewModel.checkInputAndChangeStateIfOK();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void whenTriangleIsDegenerateStatusIsCorrect() throws Exception {
        viewModel.setCoordinate1X("1");
        viewModel.setCoordinate1Y("1");
        viewModel.setCoordinate1Z("1");
        viewModel.setCoordinate2X("2");
        viewModel.setCoordinate2Y("2");
        viewModel.setCoordinate2Z("2");
        viewModel.setCoordinate3X("3");
        viewModel.setCoordinate3Y("3");
        viewModel.setCoordinate3Z("3");
        viewModel.setValueToCalculate(ValuesToCalculate.PERIMETER);
        viewModel.compute();
        assertEquals(viewModel.getStatus(), TriangleExceptions.DEGENERATE_TRIANGLE.toString());
    }

    @Test
    public void buttonIsDisabledWhenNotFieldsAreFilled() {
        viewModel.setCoordinate1X("1");
        assertEquals(viewModel.isCalculateButtonEnabled(), false);
    }
}
