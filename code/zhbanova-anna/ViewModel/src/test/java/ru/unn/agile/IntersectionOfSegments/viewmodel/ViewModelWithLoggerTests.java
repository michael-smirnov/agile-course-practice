package ru.unn.agile.IntersectionOfSegments.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ViewModelWithLoggerTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
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
    public void canCreateViewModelWithCorrectLogger() {
        assertNotNull(viewModel);
    }

    @Test
    public void defaultLogIsEmpty() {
        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void throwsWhenCreateViewModelWithNullLogger() {
        try {
            new ViewModel(null);
        } catch (IllegalArgumentException ex) {
            assertEquals("Error: Logger is null", ex.getMessage());
        }
    }

    @Test
    public void logCanHaveSeveralMessages() {
        viewModel.seg2Point1XProperty().set("1");
        viewModel.focusWasChanged(Boolean.FALSE);
        viewModel.seg2Point1YProperty().set("5");
        viewModel.focusWasChanged(Boolean.FALSE);

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void doesLogContainProperMessageAfterCalculation() {
        setInputData();
        viewModel.calculate();
        String logMessage = viewModel.getLog().get(0);

        assertTrue(logMessage.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void doesLogContainProperMessageAfterChangingInputData() {
        viewModel.seg2Point1XProperty().set("1");
        viewModel.focusWasChanged(Boolean.FALSE);
        String logMessage = viewModel.getLog().get(0);

        assertTrue(logMessage.matches(".*" + LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void doNotAddLogMessageWhenSameParameterWasInput() {
        viewModel.seg1Point1XProperty().set("1");
        viewModel.focusWasChanged(Boolean.FALSE);
        viewModel.seg1Point1XProperty().set("1");
        viewModel.focusWasChanged(Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void logSaveCorrectInputData() {
        setInputData();
        viewModel.focusWasChanged(Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.seg1Point1XProperty().get()
                + ".*" + viewModel.seg1Point1YProperty().get()
                + ".*" + viewModel.seg1Point2XProperty().get()
                + ".*" + viewModel.seg1Point2YProperty().get()
                + ".*" + viewModel.seg2Point1XProperty().get()
                + ".*" + viewModel.seg2Point1YProperty().get()
                + ".*" + viewModel.seg2Point2XProperty().get()
                + ".*" + viewModel.seg2Point2YProperty().get()
                + ".*"));
    }

    @Test
    public void addLogMessageAfterDeletingInputData() {
        viewModel.seg1Point1XProperty().set("1");
        viewModel.focusWasChanged(Boolean.FALSE);
        viewModel.seg1Point1XProperty().set("");
        viewModel.focusWasChanged(Boolean.FALSE);

        assertEquals(2, viewModel.getLog().size());
    }

    private void setInputData() {
        viewModel.seg1Point1XProperty().set("1");
        viewModel.seg1Point1YProperty().set("5");
        viewModel.seg1Point2XProperty().set("5");
        viewModel.seg1Point2YProperty().set("5");
        viewModel.seg2Point1XProperty().set("2");
        viewModel.seg2Point1YProperty().set("5");
        viewModel.seg2Point2XProperty().set("10");
        viewModel.seg2Point2YProperty().set("5");
    }
}
