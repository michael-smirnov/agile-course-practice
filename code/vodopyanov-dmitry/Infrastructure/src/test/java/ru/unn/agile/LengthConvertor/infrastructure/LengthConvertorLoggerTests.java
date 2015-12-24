package ru.unn.agile.LengthConvertor.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

public class LengthConvertorLoggerTests {
    private static final String FILE_LOG_NAME = "./LengthConvertorLoggerTests.xml";
    private LengthConvertorLogger lengthConvertorLogger;

    @Before
    public void setUp() {
        lengthConvertorLogger = new LengthConvertorLogger(FILE_LOG_NAME);
    }

    @Test
    public void canCreateLoggerWithLogName() {
        assertNotNull(lengthConvertorLogger);
    }

    @Test
    public void canCreateLogFileOnFileSystem() {
        try {
            new BufferedReader(new FileReader(FILE_LOG_NAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILE_LOG_NAME + " was not found!");
        }
    }

    @Test
    public void canAddLineToLog() {
        String testMessage = "Some test log message";

        lengthConvertorLogger.addLogLine(testMessage);

        String logLine = lengthConvertorLogger.getLog().get(0);

        assertTrue(logLine.contains(testMessage));
    }

    @Test
    public void canAddSeveralLinesToLog() {
        String[] messages = {"Some test log message", "Another test log message",
                             "Yet another test log message"};

        lengthConvertorLogger.addLogLine(messages[0]);
        lengthConvertorLogger.addLogLine(messages[1]);
        lengthConvertorLogger.addLogLine(messages[2]);

        List<String> logLines = lengthConvertorLogger.getLog();

        for (int i = 0; i < logLines.size(); i++) {
            assertTrue(logLines.get(i).contains(messages[i]));
        }
    }

    @Test
    public void isLogContainsDateAndTime() {
        String testMessage = "Some test log message";

        lengthConvertorLogger.addLogLine(testMessage);

        String logLine = lengthConvertorLogger.getLog().get(0);

        assertTrue(logLine.matches("^\\[\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}\\] .*"));
    }
}
