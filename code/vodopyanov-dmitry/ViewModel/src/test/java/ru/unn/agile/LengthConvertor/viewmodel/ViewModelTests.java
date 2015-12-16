package ru.unn.agile.LengthConvertor.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.LengthConvertor.Model.LengthUnit;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel vModel;

    public void setExternalViewModel(final ViewModel vModel) {
        this.vModel = vModel;
    }

    @Before
    public void setUp() {
        if (vModel == null) {
            vModel = new ViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        vModel = null;
    }

    @Test
    public void canSetDefaultInputValue() {
        assertEquals("", vModel.inputValueProperty().get());
    }

    @Test
    public void canSetDefaultInputUnit() {
        assertEquals(LengthUnit.INCH, vModel.inputUnitProperty().get());
    }

    @Test
    public void canSetDefaultOutputValue() {
        assertEquals("", vModel.outputValueProperty().get());
    }

    @Test
    public void canSetDefaultOutputUnit() {
        assertEquals(LengthUnit.FOOT, vModel.outputUnitProperty().get());
    }

    @Test
    public void canSetDefaultStatus() {
        assertEquals(Status.WAITING.toString(), vModel.hintMessageProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldIsFillByValidData() {
        vModel.inputValueProperty().set("1");

        assertEquals(Status.READY.toString(), vModel.hintMessageProperty().get());
    }

    @Test
    public void canStatusReportsBadFormat() {
        vModel.inputValueProperty().set("a");

        assertEquals(Status.BAD_FORMAT.toString(), vModel.hintMessageProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        assertEquals(Status.WAITING.toString(), vModel.hintMessageProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenProgramStarts() {
        assertTrue(vModel.convertingDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        vModel.inputValueProperty().set("smth");

        assertTrue(vModel.convertingDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithValidInput() {
        vModel.inputValueProperty().set("1");

        assertFalse(vModel.convertingDisabledProperty().get());
    }

    @Test
    public void canSetKMeterInputUnit() {
        vModel.inputUnitProperty().set(LengthUnit.KMETER);

        assertEquals(LengthUnit.KMETER, vModel.inputUnitProperty().get());
    }

    @Test
    public void canSetInchOutputUnit() {
        vModel.outputUnitProperty().set(LengthUnit.INCH);

        assertEquals(LengthUnit.INCH, vModel.outputUnitProperty().get());
    }

    @Test
    public void canSetBadFormatMessageWhenNumberIsNegative() {
        vModel.inputValueProperty().set("-1");

        assertEquals(Status.BAD_FORMAT.toString(), vModel.hintMessageProperty().get());
    }

    @Test
    public void canSetSuccessMessage() {
        vModel.inputValueProperty().set("1");

        vModel.convert();

        assertEquals(Status.SUCCESS.toString(), vModel.hintMessageProperty().get());
    }

    @Test
    public void isErrorMessegeEqualsErrorBadFormatWhenInputDataIncorrect() {
        vModel.inputValueProperty().set("a");

        assertEquals(Status.BAD_FORMAT.toString(), vModel.getHintMessage());
    }

    @Test
    public void canReturnRightOutputValueAsString() {
        vModel.inputValueProperty().set("1");
        vModel.inputUnitProperty().set(LengthUnit.INCH);
        vModel.outputUnitProperty().set(LengthUnit.FOOT);

        vModel.convert();

        assertEquals("0.08333333267716535", vModel.getOutputValue());
    }

    @Test
    public void isGetUnitsEqualsToUnitsPropertyGet() {
        assertEquals(vModel.unitsProperty().get(), vModel.getUnits());
    }
}
