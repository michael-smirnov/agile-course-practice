package ru.unn.agile.IntegrationMethods.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        FakeNumericalIntegrationLogger fakeLogger = new FakeNumericalIntegrationLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals(ViewModel.Function.X, viewModel.getFunction());
        assertEquals("", viewModel.getLowerLimit());
        assertEquals("", viewModel.getUpperLimit());
        assertEquals(ViewModel.IntegrationMethod.LEFT_RECTANGLES, viewModel.getIntegrationMethod());
        assertEquals("", viewModel.getResult());
        assertEquals(ViewModel.Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void canSetXFunction() {
        viewModel.setFunction(ViewModel.Function.X);

        assertEquals(ViewModel.Function.X, viewModel.getFunction());
    }

    @Test
    public void canSetCosFunction() {
        viewModel.setFunction(ViewModel.Function.COS);

        assertEquals(ViewModel.Function.COS, viewModel.getFunction());
    }

    @Test
    public void canSetExpFunction() {
        viewModel.setFunction(ViewModel.Function.EXP);

        assertEquals(ViewModel.Function.EXP, viewModel.getFunction());
    }

    @Test
    public void canSetLeftRectanglesMethod() {
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.LEFT_RECTANGLES);

        assertEquals(ViewModel.IntegrationMethod.LEFT_RECTANGLES, viewModel.getIntegrationMethod());
    }

    @Test
    public void canSetRightRectanglesMethod() {
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.RIGHT_RECTANGLES);

        assertEquals(ViewModel.IntegrationMethod.RIGHT_RECTANGLES,
                viewModel.getIntegrationMethod());
    }

    @Test
    public void canSetMidpointRectanglesMethod() {
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.MIDPOINT_RECTANGLES);

        assertEquals(ViewModel.IntegrationMethod.MIDPOINT_RECTANGLES,
                viewModel.getIntegrationMethod());
    }

    @Test
    public void canSetTrapezesMethod() {
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.TRAPEZES);

        assertEquals(ViewModel.IntegrationMethod.TRAPEZES, viewModel.getIntegrationMethod());
    }

    @Test
    public void canSetSimpsonMethod() {
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.SIMPSON);

        assertEquals(ViewModel.IntegrationMethod.SIMPSON, viewModel.getIntegrationMethod());
    }

    @Test
    public void isIntegrateButtonDisableInTheBeginning() {
        assertEquals(false, viewModel.isIntegrateButtonEnabled());
    }

    @Test
    public void canIntegrate() {
        viewModel.setFunction(ViewModel.Function.X);
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.LEFT_RECTANGLES);
        setTextFields();

        viewModel.integrate();

        assertEquals("2.0000999999998115", viewModel.getResult());
    }

    @Test
    public void isStatusWaitingInTheBeginning() {
        assertEquals(ViewModel.Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenIntegrateWithEmptyTextFields() {
        viewModel.integrate();

        assertEquals(ViewModel.Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenTextFieldsAreFilled() {
        setTextFields();

        viewModel.processKeyPressing(KeyboardKeys.ANY);

        assertEquals(ViewModel.Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenInputIsIncorrect() {
        viewModel.setLowerLimit("abc");
        viewModel.processKeyPressing(KeyboardKeys.ANY);

        assertEquals(ViewModel.Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfFormatIsGood() {
        viewModel.setLowerLimit("abc");
        viewModel.processKeyPressing(KeyboardKeys.ANY);
        viewModel.setLowerLimit("0.0");
        viewModel.processKeyPressing(KeyboardKeys.ANY);

        assertEquals(ViewModel.Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusSuccessWhenIntegrateWithCorrectInput() {
        setTextFields();

        viewModel.integrate();

        assertEquals(ViewModel.Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenIntegrateWithIncorrectInput() {
        viewModel.setLowerLimit("abc");

        viewModel.integrate();

        assertEquals(ViewModel.Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenAnyKeyIsPressed() {
        setTextFields();

        viewModel.processKeyPressing(KeyboardKeys.ANY);

        assertEquals(ViewModel.Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusSuccessWhenEnterIsPressed() {
        setTextFields();

        viewModel.processKeyPressing(KeyboardKeys.ENTER);

        assertEquals(ViewModel.Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void isIntegrateButtonDisabledWhenFormatIsBad() {
        setTextFields();
        viewModel.processKeyPressing(KeyboardKeys.ANY);
        assertEquals(true, viewModel.isIntegrateButtonEnabled());

        viewModel.setLowerLimit("abc");
        viewModel.processKeyPressing(KeyboardKeys.ANY);

        assertEquals(false, viewModel.isIntegrateButtonEnabled());
    }

    @Test
    public void isIntegrateButtonDisabledWithIncompleteInput() {
        viewModel.setLowerLimit("0.0");

        viewModel.processKeyPressing(KeyboardKeys.ANY);

        assertEquals(false, viewModel.isIntegrateButtonEnabled());
    }

    @Test
    public void isIntegrateButtonEnabledWhenInputIsCorrect() {
        setTextFields();

        viewModel.processKeyPressing(KeyboardKeys.ANY);

        assertEquals(true, viewModel.isIntegrateButtonEnabled());
    }

    @Test
    public void canGetFunctionName() {
        String xName = ViewModel.Function.X.toString();
        assertEquals("x", xName);
    }

    @Test
    public void canGetListOfFunctions() {
        ViewModel.Function[] functions = ViewModel.Function.values();
        ViewModel.Function[] currentFunctions = new ViewModel.Function[]{
                ViewModel.Function.X,
                ViewModel.Function.COS,
                ViewModel.Function.EXP};

        assertArrayEquals(currentFunctions, functions);
    }

    @Test
    public void canGetIntegrationMethodName() {
        String leftRectanglesName = ViewModel.IntegrationMethod.LEFT_RECTANGLES.toString();
        assertEquals("left rectangles method", leftRectanglesName);
    }

    @Test
    public void canGetListOfIntegrationMethods() {
        ViewModel.IntegrationMethod[] methods = ViewModel.IntegrationMethod.values();
        ViewModel.IntegrationMethod[] currentMethods = new ViewModel.IntegrationMethod[]{
                ViewModel.IntegrationMethod.LEFT_RECTANGLES,
                ViewModel.IntegrationMethod.RIGHT_RECTANGLES,
                ViewModel.IntegrationMethod.MIDPOINT_RECTANGLES,
                ViewModel.IntegrationMethod.TRAPEZES,
                ViewModel.IntegrationMethod.SIMPSON};

        assertArrayEquals(currentMethods, methods);
    }

    @Test
    public void canViewModelAcceptLogger() {
        FakeNumericalIntegrationLogger fakeLogger = new FakeNumericalIntegrationLogger();
        ViewModel viewModelWithFakeLogger = new ViewModel(fakeLogger);

        assertNotNull(viewModelWithFakeLogger);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canViewModelThrowExceptionWhenLoggerIsNull() {
        new ViewModel(null);
    }

    @Test
    public void isLoggerEmptyByDefault() {
        List<String> records = viewModel.getLoggersRecords();

        assertEquals(0, records.size());
    }

    @Test
    public void canLoggerWorkWhenLowerLimitHasLostFocus() {
        setTextFields();
        viewModel.lowerLimitHasLostFocus();

        String record = viewModel.getLoggersRecords().get(0);
        boolean doesContainLowerLimit = record.contains(
                ViewModel.RecordsTemplatesForLogger.LOWER_LIMIT_WAS_CHANGED.toString()
                        + viewModel.getLowerLimit());

        assertTrue(doesContainLowerLimit);
    }

    @Test
    public void canLoggerWorkWhenUpperLimitHasLostFocus() {
        setTextFields();
        viewModel.upperLimitHasLostFocus();

        String record = viewModel.getLoggersRecords().get(0);
        boolean doesContainUpperLimit = record.contains(
                ViewModel.RecordsTemplatesForLogger.UPPER_LIMIT_WAS_CHANGED.toString()
                        + viewModel.getUpperLimit());

        assertTrue(doesContainUpperLimit);
    }

    @Test
    public void canLoggerNotMakeSameRecordsWhenLowerLimitHasLostFocus() {
        setTextFields();
        viewModel.lowerLimitHasLostFocus();
        viewModel.lowerLimitHasLostFocus();
        viewModel.lowerLimitHasLostFocus();

        assertEquals(1, viewModel.getLoggersRecords().size());
    }

    @Test
    public void canLoggerNotMakeSameRecordsWhenUpperLimitHasLostFocus() {
        setTextFields();
        viewModel.upperLimitHasLostFocus();
        viewModel.upperLimitHasLostFocus();
        viewModel.upperLimitHasLostFocus();

        assertEquals(1, viewModel.getLoggersRecords().size());
    }

    @Test
    public void isLoggerNotEmptyWhenIntegrate() {
        setTextFields();
        viewModel.integrate();

        assertNotEquals(0, viewModel.getLoggersRecords().size());
    }

    @Test
    public void doLoggersRecordsContainRecordTemplateWhenIntegrate() {
        setTextFields();
        viewModel.integrate();
        String record = viewModel.getLoggersRecords().get(0);
        boolean doesContainTemplate = record.contains(
                ViewModel.RecordsTemplatesForLogger.INTEGRATE_WAS_PRESSED.toString());

        assertTrue(doesContainTemplate);
    }

    @Test
    public void doLoggersRecordsContainLimitsWhenIntegrate() {
        setTextFields();

        viewModel.integrate();
        String record = viewModel.getLoggersRecords().get(0);
        boolean doesContainLowerLimit =
                record.contains(viewModel.getLowerLimit());
        boolean doesContainUpperLimit =
                record.contains(viewModel.getUpperLimit());

        assertTrue(doesContainLowerLimit && doesContainUpperLimit);
    }

    @Test
    public void doLoggersRecordsContainFunctionWhenIntegrate() {
        setTextFields();
        viewModel.integrate();
        String record = viewModel.getLoggersRecords().get(0);
        boolean doesContainFunction =
                record.contains(ViewModel.Function.X.toString());

        assertTrue(doesContainFunction);
    }

    @Test
    public void doLoggersRecordsContainIntegrationMethodWhenIntegrate() {
        setTextFields();
        viewModel.integrate();
        String record = viewModel.getLoggersRecords().get(0);
        boolean doesContainMethod = record.contains(
                ViewModel.IntegrationMethod.LEFT_RECTANGLES.toString());

        assertTrue(doesContainMethod);
    }

    @Test
    public void isRecordFormedCorrectlyWhenIntegrate() {
        setTextFields();

        viewModel.integrate();
        String record = viewModel.getLoggersRecords().get(0);
        boolean isRecordCorrect = record.contains(
                ViewModel.RecordsTemplatesForLogger.INTEGRATE_WAS_PRESSED.toString()
                        + "Lower limit = " + viewModel.getLowerLimit()
                        + ", upper limit = " + viewModel.getUpperLimit()
                        + ", function = " + viewModel.getFunction().toString()
                        + ", integration method: "
                        + viewModel.getIntegrationMethod().toString()
                        + ", status: " + viewModel.getStatus()
                        + ", result = " + viewModel.getResult() + "."
        );

        assertTrue(isRecordCorrect);
    }

    @Test
    public void canLoggerSaveFunctionChange() {
        viewModel.setFunction(ViewModel.Function.COS);

        String record = viewModel.getLoggersRecords().get(0);
        boolean doesContainNewFunction = record.contains(
                ViewModel.RecordsTemplatesForLogger.FUNCTION_WAS_CHANGED.toString()
                        + ViewModel.Function.COS.toString());

        assertTrue(doesContainNewFunction);
    }

    @Test
    public void canLoggerSaveMethodChange() {
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.SIMPSON);

        String record = viewModel.getLoggersRecords().get(0);
        boolean doesContainNewMethod = record.contains(
                ViewModel.RecordsTemplatesForLogger.METHOD_WAS_CHANGED
                        + ViewModel.IntegrationMethod.SIMPSON.toString());

        assertTrue(doesContainNewMethod);
    }

    @Test
    public void canLoggerNotSaveWhenFunctionHasNotChanged() {
        viewModel.setFunction(ViewModel.Function.COS);
        viewModel.setFunction(ViewModel.Function.COS);
        viewModel.setFunction(ViewModel.Function.COS);

        assertEquals(1, viewModel.getLoggersRecords().size());
    }

    @Test
    public void canLoggerNotSaveWhenMethodHasNotChanged() {
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.SIMPSON);
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.SIMPSON);
        viewModel.setIntegrationMethod(ViewModel.IntegrationMethod.SIMPSON);

        assertEquals(1, viewModel.getLoggersRecords().size());
    }

    @Test
    public void canLoggerWorkWhenEnterIsPressed() {
        setTextFields();

        viewModel.processKeyPressing(KeyboardKeys.ENTER);

        assertEquals(1, viewModel.getLoggersRecords().size());
    }

    private void setTextFields() {
        viewModel.setLowerLimit("0.0");
        viewModel.setUpperLimit("2.0");
    }
}
