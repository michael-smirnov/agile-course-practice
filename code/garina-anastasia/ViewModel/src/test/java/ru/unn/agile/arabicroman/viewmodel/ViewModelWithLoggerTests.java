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

        assertEquals(1, viewModelWithLogger.getLogMessages().size());
    }

    @Test
    public void canLoggerContainSeveralMessages() {
        viewModelWithLogger.convert();
        viewModelWithLogger.convert();

        assertEquals(2, viewModelWithLogger.getLogMessages().size());
    }

    @Test
    public void canReverseConvertingDirectionMethodAddSomeMessageInLogger() {
        viewModelWithLogger.reverseConvertingDirection();

        assertEquals(1, viewModelWithLogger.getLogMessages().size());
    }

    @Test
    public void canConvertMethodAddProperMessageIntoLogger() {
        viewModelWithLogger.convert();
        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + LogMessages.CONVERT_WAS_PRESSED + ".*"));
    }

    public void canReverseConvertingDirectionMethodAddProperMessageIntoLogger() {
        viewModelWithLogger.convert();
        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + LogMessages.REVERSE_WAS_PRESSED + ".*"));
    }
}
