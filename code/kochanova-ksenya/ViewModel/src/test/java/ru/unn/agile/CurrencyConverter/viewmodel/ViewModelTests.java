package ru.unn.agile.CurrencyConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.CurrencyConverter.Model.UnitCurrency;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;
    public void setExternalViewModel(final ViewModel viewModel) {
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
        public void logIsEmptyByDefault() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void canSetDefaultInputValue() {
        assertEquals("", viewModel.inputValueProperty().get());
    }

    @Test
    public void canSetDefaultOutputValue() {
        assertEquals("", viewModel.outputValueProperty().get());
    }

    @Test
         public void canSetDefaultInputUnit() {
        assertEquals(UnitCurrency.DOLLAR, viewModel.inputUnitProperty().get());
    }

    @Test
    public void canSetDefaultOutputUnit() {
        assertEquals(UnitCurrency.RUBLE, viewModel.outputUnitProperty().get());
    }

    @Test
    public void canSetDefaultMessage() {
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenValueFieldEmpty() {
        viewModel.inputValueProperty().set("");

        viewModel.convert();

        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenInputValueIsCorrect() {
        viewModel.inputValueProperty().set("500.70");

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canDetectWrongFormat() {
        viewModel.inputValueProperty().set("^-^");

        assertEquals(Status.WRONG_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convertButtonIsDisabledWhenFormatIsWrong() {

        viewModel.inputValueProperty().set("1,1");

        assertTrue(viewModel.convertingDisableProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteInput() {
        viewModel.inputValueProperty().set(".");

        assertTrue(viewModel.convertingDisableProperty().get());
    }

    @Test
    public void canSetDoneMessageWhenConvertationIsDone() {
        viewModel.inputValueProperty().set("70");
        viewModel.inputUnitProperty().set(UnitCurrency.RUBLE);
        viewModel.outputUnitProperty().set(UnitCurrency.DOLLAR);

        viewModel.convert();

        assertEquals(Status.DONE.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetWrongFormatMessage() {
        viewModel.inputValueProperty().set("^-^");

        assertEquals(Status.WRONG_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenDataIsCorrect() {
        viewModel.inputValueProperty().set("135.20");
        viewModel.inputUnitProperty().set(UnitCurrency.EURO);
        viewModel.outputUnitProperty().set(UnitCurrency.DOLLAR);

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convertRubleToDollarIsCorrect() {
        viewModel.inputValueProperty().set("100");
        viewModel.inputUnitProperty().set(UnitCurrency.RUBLE);
        viewModel.outputUnitProperty().set(UnitCurrency.DOLLAR);

        viewModel.convert();

        assertEquals("1.6", viewModel.outputValueProperty().get());
        assertEquals(UnitCurrency.DOLLAR, viewModel.outputUnitProperty().get());
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void relevantRecordAfterConvertation() {
        viewModel.inputValueProperty().set("135");
        viewModel.inputUnitProperty().set(UnitCurrency.EURO);
        viewModel.outputUnitProperty().set(UnitCurrency.DOLLAR);

        viewModel.convert();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.CONVERT_WAS_PRESSED + ".*"));
    }

    @Test
    public void recordIfContainsInputData() {
        viewModel.inputValueProperty().set("15");
        viewModel.inputUnitProperty().set(UnitCurrency.RUBLE);
        viewModel.outputUnitProperty().set(UnitCurrency.POUND);

        viewModel.convert();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.inputValueProperty().get()
                + ".*" + viewModel.inputUnitProperty().get()
                + ".*" + viewModel.outputUnitProperty().get() + ".*"));
    }

    @Test
    public void currencyUnitDisplayInTheLog() {
        viewModel.inputValueProperty().set("8");
        viewModel.inputUnitProperty().set(UnitCurrency.POUND);
        viewModel.outputUnitProperty().set(UnitCurrency.EURO);

        viewModel.convert();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*POUND.*EURO.*"));
    }

    @Test
    public void canRecordSeveralLineToLog() {
        viewModel.inputValueProperty().set("1");
        viewModel.inputUnitProperty().set(UnitCurrency.EURO);
        viewModel.outputUnitProperty().set(UnitCurrency.RUBLE);

        viewModel.convert();
        viewModel.convert();

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void canDisplayUnitChangeInLog() {
        viewModel.inputValueProperty().set("1000");
        viewModel.inputUnitProperty().set(UnitCurrency.RUBLE);
        viewModel.outputUnitProperty().set(UnitCurrency.EURO);

        viewModel.onUnitChanged(UnitCurrency.RUBLE, UnitCurrency.POUND);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.CURRENCY_UNIT_WAS_CHANGED + "POUND.*"));
    }

    @Test
    public void unitCurrencyIsNotLoggedIfConstant() {
        viewModel.onUnitChanged(UnitCurrency.RUBLE, UnitCurrency.DOLLAR);
        viewModel.onUnitChanged(UnitCurrency.POUND, UnitCurrency.POUND);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void recordIfInputDataISCorrect() {
        viewModel.inputValueProperty().set("555");
        viewModel.inputUnitProperty().set(UnitCurrency.EURO);
        viewModel.outputUnitProperty().set(UnitCurrency.DOLLAR);

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.EDITING_WAS_FINISHED
                + "Data: "
                + " inputValue = " + viewModel.inputValueProperty().get()
                + " inputUnit: " + viewModel.inputUnitProperty().get().toString()
                + " outputUnit: " + viewModel.outputUnitProperty().get().toString()));
    }

    @Test
    public void noRecordIfConvertButtonIsNotEnable() {
        viewModel.convert();

        assertTrue(viewModel.getLog().isEmpty());
    }

}
