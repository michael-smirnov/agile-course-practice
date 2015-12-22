package ru.unn.agile.arabicroman.viewmodel;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import java.util.List;

public class ViewModelWithLoggerTests {
    private ArabicRomanConverterViewModel viewModelWithLogger;

    public void setViewModelWithLogger(final ArabicRomanConverterViewModel viewModelWithLogger) {
        this.viewModelWithLogger = viewModelWithLogger;
    }

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
        new ArabicRomanConverterViewModel(null);
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

        assertTrue(message.matches(".*" + LogMessages.CONVERT_HAS_BEEN_PRESSED + ".*"));
    }

    @Test
    public void canReverseConvertingDirectionMethodAddProperMessageIntoLogger() {
        viewModelWithLogger.reverseConvertingDirection();

        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + LogMessages.REVERSE_HAS_BEEN_PRESSED + ".*"));
    }

    @Test
    public void canReverseConvertingDirectionMethodAddProperDataIntoLogMessage() {
        viewModelWithLogger.reverseConvertingDirection();

        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + viewModelWithLogger.getInputNumberFormat()
                + ".*" + viewModelWithLogger.getOutputNumberFormat() + ".*"));
    }

    @Test
    public void canConvertMethodAddProperInputDataIntoLogMessage() {
        viewModelWithLogger.setInputNumber("5");
        viewModelWithLogger.convert();

        String message = viewModelWithLogger.getLogMessages().get(1);

        assertTrue(message.matches(".*" + viewModelWithLogger.getInputNumber()
                + ".*" + viewModelWithLogger.getInputNumberFormat() + ".*"));
    }

    @Test
    public void canConvertMethodAddProperMessagewhenFinishedSuccessfully() {
        viewModelWithLogger.setInputNumber("5");
        viewModelWithLogger.convert();

        String message = viewModelWithLogger.getLogMessages().get(2);

        assertTrue(message.matches(".*" + viewModelWithLogger.getOutputNumber()
                + ".*" + viewModelWithLogger.getOutputNumberFormat() + ".*"));
    }

    @Test
    public void canConvertMethodAddProperMessageWhenFailed() {
        viewModelWithLogger.setInputNumber("5fd");
        viewModelWithLogger.convert();

        String message = viewModelWithLogger.getLogMessages().get(2);

        assertTrue(message.matches(".*" + LogMessages.FAILED_CONVERT_OPERATION + ".*"));
    }
    @Test
    public void canAddMessageAboutEnteredNumber() {
        viewModelWithLogger.setInputNumber("4");

        String message = viewModelWithLogger.getLogMessages().get(0);

        assertTrue(message.matches(".*" + LogMessages.ENTERED_NUMBER + ".*"));
    }

    @Test
    public void canAddSeveralMessagesAboutEnteredNumbers() {
        viewModelWithLogger.setInputNumber("0");
        viewModelWithLogger.setInputNumber("1");
        viewModelWithLogger.setInputNumber("2");

        List<String> messagesInLog = viewModelWithLogger.getLogMessages();

        for (int i = 0; i < messagesInLog.size(); i++) {
            assertTrue(messagesInLog.get(i).matches(".*" + LogMessages.ENTERED_NUMBER + i + "$"));
        }
    }
    @Test
    public void doesNotAddLogMessageIfInputNumberHasNotChanged() {
        viewModelWithLogger.setInputNumber("0");
        viewModelWithLogger.setInputNumber("0");

        assertEquals(1, viewModelWithLogger.getLogMessages().size());
    }
}
