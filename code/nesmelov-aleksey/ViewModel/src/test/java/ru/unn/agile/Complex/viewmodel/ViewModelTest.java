package ru.unn.agile.Complex.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ViewModelTest {
    private ViewModel viewModel;

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
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

    @Test
    public void canSetDefaultFirstReal() {
        assertEquals("0.0", viewModel.getFirstRealProperty().get());
    }

    @Test
    public void canSetDefaultFirstImaginary() {
        assertEquals("0.0", viewModel.getFirstImaginaryProperty().get());
    }

    @Test
    public void canSetDefaultSecondReal() {
        assertEquals("0.0", viewModel.getSecondRealProperty().get());
    }

    @Test
    public void canSetDefaultSecondImaginary() {
        assertEquals("0.0", viewModel.getSecondImaginaryProperty().get());
    }

    @Test
    public void canSetDefaultResult() {
        assertEquals("", viewModel.getResultProperty().get());
    }

    @Test
    public void canSetDefaultErrors() {
        assertEquals("", viewModel.getErrorsProperty().get());
    }

    @Test
    public void canSetDefaultOperation() {
        assertEquals(Operation.ADD.toString(), viewModel.getOperationProperty().get().toString());
    }

    @Test
    public void canSetDefaultDisabledCalculate() {
        assertFalse(viewModel.disabledCalculateProperty().get());
    }

    @Test
    public void canNotCalculateWhenFieldsAreEmpty() {
        cleanData();
        assertTrue(viewModel.disabledCalculateProperty().get());
    }

    @Test
    public void canCalculateWhenFieldsAreFill() {
        setPositiveData();
        assertFalse(viewModel.disabledCalculateProperty().get());
    }

    @Test
    public void canNotCalculateWhenEnterNumbersAndDeleteIt() {
        setPositiveData();
        cleanData();
        assertTrue(viewModel.disabledCalculateProperty().get());
    }

    @Test
    public void canNotCalculateWhenFormatIsBad() {
        setPositiveData();
        viewModel.getFirstRealProperty().set("a");
        assertTrue(viewModel.disabledCalculateProperty().get());
    }

    @Test
    public void errorIsInvalidFormatWhenFormatIsBad() {
        viewModel.getFirstRealProperty().set("a");
        assertEquals("Invalid format!", viewModel.getErrorsProperty().get());
    }

    @Test
    public void errorsIsEmptyLineWhenDeleteNumberWithBadFormat() {
        viewModel.getFirstRealProperty().set("a");
        viewModel.getFirstRealProperty().set("");
        assertEquals("Empty line!", viewModel.getErrorsProperty().get());
    }

    @Test
    public void errorsIsEmptyWhenEnterNumberWithGoodFormat() {
        viewModel.getFirstRealProperty().set("a");
        viewModel.getFirstRealProperty().set("5");
        assertEquals("", viewModel.getErrorsProperty().get());
    }

    @Test
    public void canChangeOperation() {
        viewModel.getOperationProperty().set(Operation.MULTIPLY);
        assertEquals(Operation.MULTIPLY.toString(),
                     viewModel.getOperationProperty().get().toString());
    }

    @Test
    public void resultIsCorrectWhenAddPositiveComplexNumbers() {
        setPositiveData();
        viewModel.getOperationProperty().set(Operation.ADD);

        viewModel.calculate();

        assertEquals("3.0+4.0i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenAddNegativeComplexNumbers() {
        setNegativeData();
        viewModel.getOperationProperty().set(Operation.ADD);

        viewModel.calculate();

        assertEquals("-3.0-4.0i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenAddComplexNumberAndZero() {
        setComplexNumberAndZero();
        viewModel.getOperationProperty().set(Operation.ADD);

        viewModel.calculate();

        assertEquals("2.0+3.0i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenSubtractPositiveComplexNumbers() {
        setPositiveData();
        viewModel.getOperationProperty().set(Operation.SUBTRACT);

        viewModel.calculate();

        assertEquals("1.0+2.0i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenSubtractNegativeComplexNumbers() {
        setNegativeData();
        viewModel.getOperationProperty().set(Operation.SUBTRACT);

        viewModel.calculate();

        assertEquals("-1.0-2.0i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenSubtractComplexNumberAndZero() {
        setComplexNumberAndZero();
        viewModel.getOperationProperty().set(Operation.SUBTRACT);

        viewModel.calculate();

        assertEquals("2.0+3.0i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenMultiplyPositiveComplexNumbers() {
        setPositiveData();
        viewModel.getOperationProperty().set(Operation.MULTIPLY);

        viewModel.calculate();

        assertEquals("-1.0+5.0i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenMultiplyNegativeComplexNumbers() {
        setNegativeData();
        viewModel.getOperationProperty().set(Operation.MULTIPLY);

        viewModel.calculate();

        assertEquals("-1.0+5.0i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenMultiplyComplexNumberAndZero() {
        setComplexNumberAndZero();
        viewModel.getOperationProperty().set(Operation.MULTIPLY);

        viewModel.calculate();

        assertEquals("0.0", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenDividePositiveComplexNumbers() {
        setPositiveData();
        viewModel.getOperationProperty().set(Operation.DIVIDE);

        viewModel.calculate();

        assertEquals("2.5+0.5i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsCorrectWhenDivideNegativeComplexNumbers() {
        setNegativeData();
        viewModel.getOperationProperty().set(Operation.DIVIDE);

        viewModel.calculate();

        assertEquals("2.5+0.5i", viewModel.getResultProperty().get());
    }

    @Test
    public void resultIsEmptyWhenDivideComplexNumberAndZero() {
        setComplexNumberAndZero();
        viewModel.getOperationProperty().set(Operation.DIVIDE);

        viewModel.calculate();

        assertEquals("", viewModel.getResultProperty().get());
    }

    @Test
    public void errorMessageWhenDivideComplexNumberAndZero() {
        setComplexNumberAndZero();
        viewModel.getOperationProperty().set(Operation.DIVIDE);

        viewModel.calculate();

        assertEquals("Divider can't be zero!", viewModel.getErrorsProperty().get());
    }

    @Test(expected = Exception.class)
    public void exceptionIfLoggerInViewModelConstructorIsNull() {
        new ViewModel(null);
    }

    @Test
    public void loggerIsEmptyWhenCreateViewModel() {
        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void loggerIsNotEmptyAfterCalculate() {
        setPositiveData();

        viewModel.calculate();

        assertFalse(viewModel.getLog().isEmpty());
    }

    @Test
    public void loggerHasCorrectLogMessageAfterCalculate() {
        setPositiveData();

        viewModel.calculate();

        assertTrue(viewModel.getLog().get(0).matches(".*" + LogMessage.PRESS_CALCULATE + ".*"));
    }

    @Test
    public void loggerHasCorrectNumbersAfterCalculate() {
        setPositiveData();

        viewModel.calculate();

        assertTrue(viewModel.getLog().get(0).matches(".*" + viewModel.getFirstRealProperty().get() +
                                                     ".*" + viewModel.getFirstImaginaryProperty().get() +
                                                     ".*" + viewModel.getSecondRealProperty().get() +
                                                     ".*" + viewModel.getSecondImaginaryProperty().get() + ".*"));
    }

    @Test
    public void loggerHasResultAfterCalculate() {
        setPositiveData();

        viewModel.calculate();

        assertTrue(viewModel.getLog().get(0).matches(".*Result was: .*"));
    }

    @Test
    public void loggerHasOperationTypeAfterCalculate() {
        setPositiveData();

        viewModel.calculate();

        assertTrue(viewModel.getLog().get(0).matches(".*" + viewModel.getOperationProperty().get() + ".*"));
    }

    @Test
    public void loggerHasCorrectResulltMessageAfterZeroDivider() {
        setComplexNumberAndZero();
        viewModel.getOperationProperty().set(Operation.DIVIDE);

        viewModel.calculate();

        assertTrue(viewModel.getLog().get(0).matches(".*" + "There was not result because of zero divider" + ".*"));
    }

    @Test
    public void loggerHasCorrectMessageAfterCalculationNumbers() {
        setPositiveData();

        viewModel.calculate();

        String message = "Calculate pressed. ";
        message += "Arguments was: Re1 = " + viewModel.getFirstRealProperty().get()
                + " Im1 = " + viewModel.getFirstImaginaryProperty().get()
                + " Re2 = " + viewModel.getSecondRealProperty().get()
                + " Im2 = " + viewModel.getSecondImaginaryProperty().get() + ". "
                + "Operation was: " + viewModel.getOperationProperty().get().toString() + ". "
                + "Result was: " + viewModel.getResultProperty().get() + ".";
        assertEquals(message, viewModel.getLog().get(0));
    }

    @Test
    public void canPutSeveralMessagesInLog() {
        for (int i = 0; i < 10; i ++) {
            viewModel.calculate();
        }

        assertEquals(10, viewModel.getLog().size());
    }

    @Test
    public void logShowThatOperationIsChanged() {
        viewModel.onOperationChanged(Operation.ADD, Operation.DIVIDE);

        assertTrue(viewModel.getLog().get(0).matches(".*" + Operation.DIVIDE + ".*"));
    }

    @Test
    public void logCanNotShowThatOperationIsChangedWhenItIsNotChanged() {
        viewModel.onOperationChanged(Operation.ADD, Operation.ADD);

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void doLogWhenChangedInputValue() {
        viewModel.getFirstImaginaryProperty().set("20");
        viewModel.onInputFocusChanged();

        assertFalse(viewModel.getLog().isEmpty());
    }

    @Test
    public void doNotLogTwiceWhenInputIsNotChanged() {
        viewModel.getFirstImaginaryProperty().set("999");
        viewModel.onInputFocusChanged();
        viewModel.getFirstImaginaryProperty().set("999");
        viewModel.onInputFocusChanged();

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void logContainLogMessageWhenFocusChanged() {
        viewModel.getFirstRealProperty().set("155");
        viewModel.onInputFocusChanged();

        assertTrue(viewModel.getLog().get(0).matches(".*" + LogMessage.UPDATE_INPUT + ".*"));
    }

    @Test
    public void logContainNumbersWhenInputFocusChanged() {
        viewModel.getFirstRealProperty().set("355");
        viewModel.onInputFocusChanged();

        assertTrue(viewModel.getLog().get(0).matches(".*" + viewModel.getFirstRealProperty().get() +
                                                     ".*" + viewModel.getFirstImaginaryProperty().get() +
                                                     ".*" + viewModel.getSecondRealProperty().get() +
                                                     ".*" + viewModel.getSecondImaginaryProperty().get() + ".*"));
    }

    @Test
    public void logContainErrorMessageWhenFormatIsInvalid() {
        viewModel.getFirstImaginaryProperty().set("abc");

        assertTrue(viewModel.getLog().get(0).matches(".*" + LogMessage.GET_ERROR
                 + viewModel.getErrorsProperty().get().toString() + ".*"));
    }

    @Test
    public void doNotLogErrorTwiceWhenFormatIsInvalid() {
        viewModel.getFirstImaginaryProperty().set("abc");
        viewModel.getFirstImaginaryProperty().set("abscsd");

        assertEquals(1, viewModel.getLog().size());
    }

    private void setPositiveData() {
        viewModel.getFirstRealProperty().set("2");
        viewModel.getFirstImaginaryProperty().set("3");
        viewModel.getSecondRealProperty().set("1");
        viewModel.getSecondImaginaryProperty().set("1");
    }
    private void setNegativeData() {
        viewModel.getFirstRealProperty().set("-2");
        viewModel.getFirstImaginaryProperty().set("-3");
        viewModel.getSecondRealProperty().set("-1");
        viewModel.getSecondImaginaryProperty().set("-1");
    }
    private void  setComplexNumberAndZero() {
        viewModel.getFirstRealProperty().set("2");
        viewModel.getFirstImaginaryProperty().set("3");
        viewModel.getSecondRealProperty().set("0");
        viewModel.getSecondImaginaryProperty().set("0");
    }
    private void cleanData() {
        viewModel.getFirstRealProperty().set("");
        viewModel.getFirstImaginaryProperty().set("");
        viewModel.getSecondRealProperty().set("");
        viewModel.getSecondImaginaryProperty().set("");
    }
}
