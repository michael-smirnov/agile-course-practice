package ru.unn.NewtonMethod.viewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.junit.Assert.*;
import static ru.unn.NewtonMethod.viewModel.NewtonMethodViewModel.*;

public class NewtonMethodViewModelTests {
    private NewtonMethodViewModel viewModel;

    public void setViewModel(final NewtonMethodViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new NewtonMethodViewModel(new FakeNewtonMethodLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void isStatusWaitingInTheBeginning() {
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    private void inputData() {
        viewModel.setFunction("(x+3)*(x+3)-2");
        viewModel.setDerivative("2*(x+3)");
        viewModel.setLeftPointOfRange("-2");
        viewModel.setRightPointOfRange("0");
    }

    @Test
    public void isStatusReadyWhenFieldsAreFill() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatRange() {
        viewModel.setLeftPointOfRange("a");

        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertEquals(Status.BAD_FORMAT_RANGE.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatFunction() {
        viewModel.setFunction("x+x(");

        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertEquals(Status.BAD_FORMAT_FUNCTION.toString(), viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfParseIsOK() {
        viewModel.setRightPointOfRange("a");
        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        viewModel.setRightPointOfRange("1.0");
        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void byDefaultCalculateButtonIsDisabled() {
        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenFormatIsBad() {
        viewModel.setRightPointOfRange("trash");

        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenIncorrectFunction() {
        viewModel.setFunction("x*x-2)");

        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWithIncompleteInput() {
        viewModel.setRightPointOfRange("1");
        viewModel.setLeftPointOfRange("1");

        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void canSetSuccessMessage() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void canSetNoRootMessage() {
        inputData();
        viewModel.setLeftPointOfRange("-1");

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(Status.NO_ROOT.toString(), viewModel.getStatus());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.setLeftPointOfRange("a");

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(Status.BAD_FORMAT_RANGE.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenKeyIsNotEnter() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusSuccessWhenKeyIsEnter() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void whenEnterFunctionAndRangeCalculateButtonIsEnabled() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ANY.getKey());

        assertTrue(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void whenCalculateRootDisplayResult() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals("-1.586", viewModel.getRoot());
    }

    @Test
    public void canCreateNewtonMethodViewModelLogger() {
        assertNotNull(viewModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotCreateNewtonMethodViewModelWithNullLogger() {
        new NewtonMethodViewModel(null);
    }

    @Test
    public void isLogEmptyByDefault() {
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isEditingRangeFieldsAddNewMessageToLog() {
        viewModel.setLeftPointOfRange("-9");

        viewModel.valueFieldFocusLost();

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isLogContainProperLogMessageAfterRangeFieldsEdited() {
        viewModel.setRightPointOfRange("10.78");

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLog().get(0);

        assertThat(logMessage, matchesPattern(".*" + LogMessages.RANGE_FIELD_CHANGED + ".*"));
    }

    @Test
    public void areBordersOfRangeCorrectlyLoggedOnEditing() {
        inputData();

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLog().get(0);

        assertThat(logMessage, matchesPattern(".*" + LogMessages.RANGE_FIELD_CHANGED
                + "\\[" + viewModel.getLeftPoint() + ";" + viewModel.getRightPoint() + "]"));
    }

    @Test
    public void doNotLogIfRangeFieldsDoNotChange() {
        viewModel.setLeftPointOfRange("-12.23");
        viewModel.valueFieldFocusLost();
        viewModel.setLeftPointOfRange("-12.23");
        viewModel.valueFieldFocusLost();

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isEditingFunctionsFieldsAddNewMessageToLog() {
        viewModel.setFunction("(x+3)*(x+3)-2");

        viewModel.valueFieldFocusLost();

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isLogContainProperLogMessageAfterFunctionFieldsEdited() {
        viewModel.setDerivative("x*2");

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLog().get(0);

        assertThat(logMessage, matchesPattern(".*" + LogMessages.FUNCTION_CHANGED + ".*"));
    }

    @Test
    public void areFunctionsCorrectlyLoggedOnEditing() {
        viewModel.setFunction("x");
        viewModel.setDerivative("1");

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLog().get(0);

        assertThat(logMessage, matchesPattern(".*" + LogMessages.FUNCTION_CHANGED
                + "function \\[" + viewModel.getFunction() + "\\]; "
                + "derivative \\[" + viewModel.getDerivative() + "\\]"));
    }

    @Test
    public void doNotLogIfFunctionFieldsDoNotChange() {
        viewModel.setFunction("(x+3)*(x+3)-2");
        viewModel.valueFieldFocusLost();
        viewModel.setFunction("(x+3)*(x+3)-2");
        viewModel.valueFieldFocusLost();

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isPressingCalculateButtonAddNewMessageToLog() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void isLogContainProperLogMessageAfterPressingCalculateButton() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());
        String logMessage = viewModel.getLog().get(2);

        assertThat(logMessage, matchesPattern(".*" + LogMessages.CALCULATE_BUTTON_PRESSED + ".*"));
    }

    @Test
    public void canPutSeveralLogMessagesIfCalledCalculate() {
        inputData();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());
        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());
        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(5, viewModel.getLog().size());
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabledAndFunctionFieldsEdited() {
        viewModel.setFunction("(x+3)*(x+3)-2");

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabledAndRangeFieldsEdited() {
        viewModel.setRightPointOfRange("10");

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabledAndWhenRangeAndFunctionFieldsEdited() {
        viewModel.setRightPointOfRange("10");
        viewModel.setFunction("(x+3)*(x+3)-2");

        viewModel.processKeyInTextField(KeyboardKeys.ENTER.getKey());

        assertEquals(2, viewModel.getLog().size());
    }
}
