package ru.unn.agile.WeightConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.WeightConverter.Model.WeightUnit;

import java.util.List;
import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setExternalViewModel(final ViewModel weightConverterViewModel) {
        this.viewModel = weightConverterViewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            FakeLogger fakeLogger = new FakeLogger();
            viewModel = new ViewModel(fakeLogger);
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValue() {
        assertEquals("", viewModel.valueProperty().get());
    }

    @Test
    public void canSetDefaultInputUnit() {
        assertEquals(WeightUnit.GRAM, viewModel.inputUnitProperty().get());
    }

    @Test
    public void canSetDefaultOutputUnit() {
        assertEquals(WeightUnit.KILOGRAM, viewModel.outputUnitProperty().get());
    }

    @Test
    public void canSetDefaultResult() {
        assertEquals("", viewModel.resultProperty().get());
    }

    @Test
    public void canSetDefaultStatus() {
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenConvertWithEmptyField() {
        viewModel.valueProperty().set("");

        viewModel.convert();

        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenTextFieldIsCorrectFill() {
        viewModel.valueProperty().set("4");

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.valueProperty().set("a");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convertButtonIsDisableInitially() {
        assertTrue(viewModel.conversionDisabledProperty().get());
    }

    @Test
    public void convertButtonIsDisableWhenChangeInputValueFromValidToBad() {
        viewModel.valueProperty().set("4");
        viewModel.valueProperty().set("a");

        assertTrue(viewModel.conversionDisabledProperty().get());
    }

    @Test
    public void convertButtonIsDisabledWhenInputValueHasCommaInsteadOfDot() {
        viewModel.valueProperty().set("7,1");

        assertTrue(viewModel.getConversionDisabled());
    }

    @Test
    public void convertButtonIsEnabledWithCorrectInput() {
        viewModel.valueProperty().set("4");

        assertFalse(viewModel.conversionDisabledProperty().get());
    }

    @Test
    public void canSetInputUnit() {
        viewModel.inputUnitProperty().set(WeightUnit.CENTNER);

        assertEquals(WeightUnit.CENTNER, viewModel.inputUnitProperty().get());
    }

    @Test
    public void outputUnitIsDefaultUnit() {
        assertEquals(WeightUnit.KILOGRAM, viewModel.outputUnitProperty().get());
    }

    @Test
    public void conversionFromKilogramToGramHasCorrectResult() {
        viewModel.inputUnitProperty().set(WeightUnit.KILOGRAM);
        viewModel.outputUnitProperty().set(WeightUnit.GRAM);
        viewModel.valueProperty().set("1");

        viewModel.convert();

        assertEquals("1000.0", viewModel.resultProperty().get());
    }

    @Test
    public void canSetBadFormatMessageWhenNumberIsNegative() {
        viewModel.valueProperty().set("-1");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.valueProperty().set("#kochan");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void isGetUnitsEqualsToUnitsPropertyGet() {
        assertEquals(viewModel.unitsProperty().get(), viewModel.getUnits());
    }

    @Test
    public void conversionFromCentnerToGramHasCorrectResult() {
        viewModel.inputUnitProperty().set(WeightUnit.CENTNER);
        viewModel.outputUnitProperty().set(WeightUnit.GRAM);
        viewModel.valueProperty().set("0.01");

        viewModel.convert();

        assertEquals("1000.0", viewModel.getResult());
    }

    @Test
    public void conversionFromPoundToKilogramHasCorrectResult() {
        viewModel.inputUnitProperty().set(WeightUnit.POUND);
        viewModel.outputUnitProperty().set(WeightUnit.KILOGRAM);
        viewModel.valueProperty().set("2.2");

        viewModel.convert();

        assertEquals("1.0", viewModel.resultProperty().get());
    }

    @Test
    public void logIsEmptyWhenCalculatorStarts() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowExceptionWithNullLogger() {
        ViewModel viewModelNull = new ViewModel(null);
    }

    @Test
    public void logIsEmptyWhenConverterStarts() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterConversion() {
        setInputParameters();

        viewModel.convert();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.CONVERT_BUTTON_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputParametersAfterConversion() {
        setInputParameters();

        viewModel.convert();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.valueProperty().get()
                + ".*" + viewModel.inputUnitProperty().get()
                + ".*" + viewModel.outputUnitProperty().get() + ".*"));
    }

    @Test
    public void argumentsInfoIsProperlyFormatted() {
        setInputParameters();

        viewModel.convert();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Entered parameters"
                + ": value = " + viewModel.valueProperty().get()
                + "; input unit = " + viewModel.inputUnitProperty().get()
                + "; output unit = " + viewModel.outputUnitProperty().get() + ".*"));
    }

    @Test
    public void inputWeightUnitIsMentionedInTheLog() {
        setInputParameters();

        viewModel.convert();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*POUND.*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setInputParameters();

        viewModel.convert();
        viewModel.convert();
        viewModel.convert();
        viewModel.convert();

        assertEquals(4, viewModel.getLog().size());
    }

    @Test
    public void canWeightUnitUpdateInLog() {
        viewModel.unitsChanged(WeightUnit.POUND, WeightUnit.TON);

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.WEIGHT_UNITS_WERE_CHANGED + ".*"));
    }

    @Test
    public void weightUnitIsNotLoggedIfNotChanged() {
        viewModel.unitsChanged(WeightUnit.GRAM, WeightUnit.POUND);
        viewModel.unitsChanged(WeightUnit.KILOGRAM, WeightUnit.KILOGRAM);
        viewModel.unitsChanged(WeightUnit.GRAM, WeightUnit.GRAM);
        viewModel.unitsChanged(WeightUnit.KILOGRAM, WeightUnit.OUNCE);

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void parametersAreCorrectlyLogged() {
        setInputParameters();

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED
                + ".*[" + viewModel.valueProperty().get() + "].*"));
    }

    @Test
    public void doNotLogWithoutChangesInputValue() {
        viewModel.valueProperty().set("12.0");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.valueProperty().set("12.0");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void converterIsNotCalledWhenButtonIsDisabled() {
        viewModel.convert();

        assertTrue(viewModel.getLog().isEmpty());
    }

    private void setInputParameters() {
        viewModel.inputUnitProperty().set(WeightUnit.POUND);
        viewModel.outputUnitProperty().set(WeightUnit.KILOGRAM);
        viewModel.valueProperty().set("2.2");
    }
}
