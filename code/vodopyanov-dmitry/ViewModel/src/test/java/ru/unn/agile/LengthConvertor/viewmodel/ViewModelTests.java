package ru.unn.agile.LengthConvertor.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.LengthConvertor.Model.LengthUnit;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;
    public void setOuterViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultStatus() {
        assertEquals(Status.WAITING.toString(), viewModel.hintMessageProperty().get());
    }

    @Test
    public void canSetDefaultInputValue() {
        assertEquals("", viewModel.inputValueProperty().get());
    }

    @Test
    public void canSetDefaultInputUnit() {
        assertEquals(LengthUnit.INCH, viewModel.inputUnitProperty().get());
    }

    @Test
    public void canSetDefaultOutputValue() {
        assertEquals("", viewModel.outputValueProperty().get());
    }

    @Test
    public void canSetDefaultOutputUnit() {
        assertEquals(LengthUnit.FOOT, viewModel.outputUnitProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldIsFillByValidData() {
        viewModel.inputValueProperty().set("1");

        assertEquals(Status.READY.toString(), viewModel.hintMessageProperty().get());
    }

    @Test
    public void canStatusReportsBadFormat() {
        viewModel.inputValueProperty().set("a");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.hintMessageProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        assertEquals(Status.WAITING.toString(), viewModel.hintMessageProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenProgramStarts() {
        assertTrue(viewModel.convertingDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        viewModel.inputValueProperty().set("smth");

        assertTrue(viewModel.convertingDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithValidInput() {
        viewModel.inputValueProperty().set("1");

        assertFalse(viewModel.convertingDisabledProperty().get());
    }

    @Test
    public void canSetKMeterInputUnit() {
        viewModel.inputUnitProperty().set(LengthUnit.KMETER);

        assertEquals(LengthUnit.KMETER, viewModel.inputUnitProperty().get());
    }

    @Test
    public void canSetInchOutputUnit() {
        viewModel.outputUnitProperty().set(LengthUnit.INCH);

        assertEquals(LengthUnit.INCH, viewModel.outputUnitProperty().get());
    }

    @Test
    public void canSetBadFormatMessageWhenNumberIsNegative() {
        viewModel.inputValueProperty().set("-1");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.hintMessageProperty().get());
    }

    @Test
    public void canSetSuccessMessage() {
        viewModel.inputValueProperty().set("1");

        viewModel.convert();

        assertEquals(Status.SUCCESS.toString(), viewModel.hintMessageProperty().get());
    }

    @Test
    public void isErrorMessegeEqualsErrorBadFormatWhenInputDataIncorrect() {
        viewModel.inputValueProperty().set("a");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getHintMessage());
    }

    @Test
    public void canReturnRightOutputValueAsString() {
        viewModel.inputValueProperty().set("1");
        viewModel.inputUnitProperty().set(LengthUnit.INCH);
        viewModel.outputUnitProperty().set(LengthUnit.FOOT);

        viewModel.convert();

        assertEquals("0.08333333267716535", viewModel.getOutputValue());
    }

    @Test
    public void isGetUnitsEqualsToUnitsPropertyGet() {
        assertEquals(viewModel.unitsProperty().get(), viewModel.getUnits());
    }
}
