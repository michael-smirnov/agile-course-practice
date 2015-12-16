package ru.unn.agile.LengthConvertor.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertNotNull;

public class LengthConvertorLoggerTests {
    private static final String LOG_NAME = "./LengthConvertorLoggerTests.log";
    private LengthConvertorLogger lengthConvertorLogger;

    @Before
    public void setUp() {
        lengthConvertorLogger = new LengthConvertorLogger(LOG_NAME);
    }

    @Test
    public void canWeCreateLoggerWithLogName() {
        assertNotNull(lengthConvertorLogger);
    }

    @Test
    public void canWeCreateLogFileOnFileSystem() {
        try {
            new BufferedReader(new FileReader(LOG_NAME));
        } catch (FileNotFoundException e) {
            fail("File " + LOG_NAME + " was not found!");
        }
    }

    @Test
    public void canAddLineToLog() {
        String testMessage = "Some test log message";

        lengthConvertorLogger.addLogLine(testMessage);

        assertFalse(lengthConvertorLogger.getLog().isEmpty());
    }

    @Test
    public void canAddSeveralLinesToLog() {
        String[] messages = {"Some test log message", "Another test log message",
                             "Yet another test log message"};

        lengthConvertorLogger.addLogLine(messages[0]);
        lengthConvertorLogger.addLogLine(messages[1]);
        lengthConvertorLogger.addLogLine(messages[2]);

        assertEquals(3, lengthConvertorLogger.getLog().size());
    }
}
