package ru.unn.agile.arabicroman.infrastructure;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.List;

public class TextLoggerTests {
    private static final String TEXT_LOGGER_FILE_NAME = "./TextLoggerTests-ArabicRoman.log";
    private static final String TESTING_MESSAGE = "Test for writing capability";
    private TextLogger textLogger;

    @Before
    public void setUp() {
        textLogger = new TextLogger(TEXT_LOGGER_FILE_NAME);
    }

    @Test
    public void canCreateTextLoggerWithCertainFileName() {
        assertNotNull(textLogger);
    }

    @Test
    public void canCreateLogFileInDefiniteDirectory() {
        try {
            new FileReader(TEXT_LOGGER_FILE_NAME);
        } catch (FileNotFoundException e) {
            fail("Following file " + TEXT_LOGGER_FILE_NAME + " cannot be found");
        }
    }

    @Test
    public void canSomeMessageBeWrittenInLogFile() {
        textLogger.addLogMessage(TESTING_MESSAGE);

        String messageInLog = textLogger.getLogMessages().get(0);

        assertTrue(messageInLog.matches(".*" + TESTING_MESSAGE + "$"));
    }

    @Test
    public void canSeveralMessagesBeWrittenInLogFile() {
        textLogger.addLogMessage(TESTING_MESSAGE + "0");
        textLogger.addLogMessage(TESTING_MESSAGE + "1");

        List<String> messagesInLog = textLogger.getLogMessages();

        for (int i = 0; i < messagesInLog.size(); i++) {
            assertTrue(messagesInLog.get(i).matches(".*" + TESTING_MESSAGE + i + "$"));
        }
    }

    @Test
    public void doMessagesInLogContainDateAndTimeInCorrectFormat() {
        textLogger.addLogMessage(TESTING_MESSAGE);

        String messageInLog = textLogger.getLogMessages().get(0);

        assertTrue(messageInLog.matches("^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
