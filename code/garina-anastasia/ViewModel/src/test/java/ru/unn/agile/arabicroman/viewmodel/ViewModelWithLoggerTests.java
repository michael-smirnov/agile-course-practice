package ru.unn.agile.arabicroman.viewmodel;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ViewModelWithLoggerTests {
    private ArabicRomanConverterViewModel viewModelWithLogger;

    @Before
    public void setUp() {
        viewModelWithLogger = new ArabicRomanConverterViewModel(new FakeLogger());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModelWithLogger);
    }

    @Test (expected = IllegalArgumentException.class)
    public void throwsOnNullLoggerAsArgumentInViewModelConstructor() {
        viewModelWithLogger = new ArabicRomanConverterViewModel(null);
    }

    @Test
    public void byDefaultLogIsEmpty() {
        assertEquals(0, viewModelWithLogger.getLogMessages().size());
    }

    @Test
    public void canConvertMethodAddSomeMessageInLogger() {
        viewModelWithLogger.convert();

        assertNotEquals(0, viewModelWithLogger.getLogMessages().size());
    }

    @Test
    public void canLoggerContainSeveralMessages() {
        viewModelWithLogger.convert();
        viewModelWithLogger.convert();

        assertEquals(4, viewModelWithLogger.getLogMessages().size());
    }

    @Test
    public void canReverseConvertingDirectionMethodAddSomeMessageInLogger() {
        viewModelWithLogger.reverseConvertingDirection();

        assertNotEquals(0, viewModelWithLogger.getLogMessages().size());
    }

    @Test
    public void canConvertMethodAddProperMessageIntoLogger() {
        viewModelWithLogger.convert();
        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + LogMessages.CONVERT_WAS_PRESSED + ".*"));
    }

    @Test
    public void canReverseConvertingDirectionMethodAddProperMessageIntoLogger() {
        viewModelWithLogger.reverseConvertingDirection();
        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + LogMessages.REVERSE_WAS_PRESSED + ".*"));
    }

    @Test
    public void canReverseConvertingDirectionMethodAddProperDataIntoLogMessage() {
        viewModelWithLogger.reverseConvertingDirection();
        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + viewModelWithLogger.getInputNumberFormat()
                + ".*" + viewModelWithLogger.getOutputNumberFormat() + ".*"));
    }

    @Test
    public void canConverMethodAddProperInputDataIntoLogMessage() {
        viewModelWithLogger.setInputNumber("5");
        viewModelWithLogger.convert();
        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + viewModelWithLogger.getInputNumber()
                + ".*" + viewModelWithLogger.getInputNumberFormat() + ".*"));
    }

    @Test
    public void canConverMethodAddProperMessagewhenFinisedSuccessfully() {
        viewModelWithLogger.setInputNumber("5");
        viewModelWithLogger.convert();
        String message = viewModelWithLogger.getLogMessages().get(1);

        assertTrue(message.matches(".*" + viewModelWithLogger.getOutputNumber()
                + ".*" + viewModelWithLogger.getOutputNumberFormat() + ".*"));
    }

    @Test
    public void canConverMethodAddProperMessageWhenFailed() {
        viewModelWithLogger.setInputNumber("5fd");
        viewModelWithLogger.convert();
        String message = viewModelWithLogger.getLogMessages().get(1);

        assertTrue(message.matches(".*" + LogMessages.FAILED_CONVERT_OPERATION + ".*"));
    }
}
