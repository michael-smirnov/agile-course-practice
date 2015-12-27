package ru.unn.agile.StatisticValueCalculator.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;

public class StatisticCalculatorTxtLoggerTests {
    private static final String LOG_PATH = "StatisticCalculatorLogger.log";
    private TxtLoggerOfStatisticCalculator logger;

    @Before
    public void setUp() {
        logger = new TxtLoggerOfStatisticCalculator(LOG_PATH);
    }

    @Test
    public void canInstantiateTxtLogger() {
        assertNotNull(logger);
    }

    @Test
    public void isLogFileCreatedAfterLoggerHadBeenInstantiated() {
        try {
            new BufferedReader(new FileReader(LOG_PATH));
        } catch (FileNotFoundException e) {
            fail("File " + LOG_PATH + " hadn't been created");
        }
    }

    @Test
    public void canWriteMessageIntoLog() {
        String message = "Just a test message";

        logger.set(message);
        assertTrue(logger.getLog().get(0).matches(".*" + message + "$"));
    }

    @Test
    public void doesLogMessageContainsDateAndTime() {
        logger.set("Test");
        assertTrue(logger.getLog().get(0).
                matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
