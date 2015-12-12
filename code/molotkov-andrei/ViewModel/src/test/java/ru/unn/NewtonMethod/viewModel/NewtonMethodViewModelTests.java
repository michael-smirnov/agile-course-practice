package ru.unn.NewtonMethod.viewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static ru.unn.NewtonMethod.viewModel.NewtonMethodRegexMatcher.matchesPattern;

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
        assertEquals(NewtonMethodViewModel.Status.WAITING.getMessage(), viewModel.getStatus());
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

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertEquals(NewtonMethodViewModel.Status.READY.getMessage(), viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatRange() {
        viewModel.setLeftPointOfRange("a");

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertEquals(NewtonMethodViewModel.Status.BAD_FORMAT_RANGE.getMessage(),
                    viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatFunction() {
        viewModel.setFunction("x+x(");

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertEquals(NewtonMethodViewModel.Status.BAD_FORMAT_FUNCTION.getMessage(),
                    viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfParseIsOK() {
        viewModel.setRightPointOfRange("a");
        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        viewModel.setRightPointOfRange("1.0");
        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertEquals(NewtonMethodViewModel.Status.WAITING.getMessage(), viewModel.getStatus());
    }

    @Test
    public void byDefaultCalculateButtonIsDisabled() {
        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenFormatIsBad() {
        viewModel.setRightPointOfRange("trash");

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenIncorrectFunction() {
        viewModel.setFunction("x*x-2)");

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWithIncompleteInput() {
        viewModel.setRightPointOfRange("1");
        viewModel.setLeftPointOfRange("1");

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void canSetSuccessMessage() {
        inputData();

        viewModel.processKeyInTextField(10);

        assertEquals(NewtonMethodViewModel.Status.SUCCESS.getMessage(), viewModel.getStatus());
    }

    @Test
    public void canSetNoRootMessage() {
        inputData();
        viewModel.setLeftPointOfRange("-1");

        viewModel.processKeyInTextField(10);

        assertEquals(NewtonMethodViewModel.Status.NO_ROOT.getMessage(), viewModel.getStatus());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.setLeftPointOfRange("a");

        viewModel.processKeyInTextField(10);

        assertEquals(NewtonMethodViewModel.Status.BAD_FORMAT_RANGE.getMessage(),
                    viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenKeyIsNotEnter() {
        inputData();

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertEquals(NewtonMethodViewModel.Status.READY.getMessage(), viewModel.getStatus());
    }

    @Test
    public void isStatusSuccessWhenKeyIsEnter() {
        inputData();

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ENTER.getKey());

        assertEquals(NewtonMethodViewModel.Status.SUCCESS.getMessage(), viewModel.getStatus());
    }

    @Test
    public void whenEnterFunctionAndRangeCalculateButtonIsEnabled() {
        inputData();

        viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ANY.getKey());

        assertTrue(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void whenCalculateRootDisplayResult() {
        inputData();

        viewModel.processKeyInTextField(10);

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
    public void logIsEmptyInTheBeginning() {
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
    public void isLogContainProperMessageAfterRangeFieldsEdited() {
        viewModel.setRightPointOfRange("10.78");

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLog().get(0);

        assertThat(logMessage, matchesPattern(".*"
                + NewtonMethodViewModel.LogMessages.RANGE_FIELD_CHANGED + ".*"));
    }

    @Test
    public void areBordersOfRangeCorrectlyLoggedOnEditing() {
        inputData();

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLog().get(0);

        assertThat(logMessage, matchesPattern(".*" + NewtonMethodViewModel.LogMessages.RANGE_FIELD_CHANGED
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
    public void isLogContainProperMessageAfterFunctionFieldsEdited() {
        viewModel.setDerivative("x*2");

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLog().get(0);

        assertThat(logMessage, matchesPattern(".*"
                + NewtonMethodViewModel.LogMessages.FUNCTION_CHANGED + ".*"));
    }

    @Test
    public void areFunctionsCorrectlyLoggedOnEditing() {
        viewModel.setFunction("x");
        viewModel.setDerivative("1");

        viewModel.valueFieldFocusLost();
        String logMessage = viewModel.getLog().get(0);

        assertThat(logMessage, matchesPattern(".*" + NewtonMethodViewModel.LogMessages.FUNCTION_CHANGED
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

        viewModel.processKeyInTextField(10);

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void isLogContainProperMessageAfterPressingCalculateButton() {
        inputData();

        viewModel.processKeyInTextField(10);
        String logMessage = viewModel.getLog().get(2);

        assertThat(logMessage, matchesPattern(".*"
                + NewtonMethodViewModel.LogMessages.CALCULATE_BUTTON_PRESSED + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        inputData();

        viewModel.processKeyInTextField(10);
        viewModel.processKeyInTextField(10);
        viewModel.processKeyInTextField(10);

        assertEquals(5, viewModel.getLog().size());
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabledAndFunctionFieldsEdited() {
        viewModel.setFunction("(x+3)*(x+3)-2");

        viewModel.processKeyInTextField(10);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabledAndRangeFieldsEdited() {
        viewModel.setRightPointOfRange("10");

        viewModel.processKeyInTextField(10);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabledAndRangeAndFunctionFieldsEdited() {
        viewModel.setRightPointOfRange("10");
        viewModel.setFunction("(x+3)*(x+3)-2");

        viewModel.processKeyInTextField(10);

        assertEquals(2, viewModel.getLog().size());
    }
}
