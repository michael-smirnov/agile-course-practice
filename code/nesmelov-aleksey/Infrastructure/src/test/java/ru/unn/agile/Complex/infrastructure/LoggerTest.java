package ru.unn.agile.Complex.infrastructure;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class LoggerTest {
    private Logger logger;
    private static final String fileName = "./LoggerTest.log";

    @Before
    public void setUp() {
        logger = new Logger(fileName);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(new Logger(fileName));
    }

    @Test
    public void canWriteLog() {
        logger.addToLog("Test message.");

        assertTrue(logger.getLog().get(0).matches(".* Test message..*" ));
    }

    @Test
    public void logIsEmptyIfNotWrite() {
        assertTrue(logger.getLog().isEmpty());
    }

    @Test
    public void canWriteSeveralLogs() {
        logger.addToLog("Test message 1.");
        logger.addToLog("Test message 2.");

        assertTrue(logger.getLog().get(0).matches(".* Test message 1..*"));
        assertTrue(logger.getLog().get(1).matches(".* Test message 2..*"));
    }

    @Test
    public void logContainDataAndTime() {
        logger.addToLog("Test message.");

        assertTrue(logger.getLog().get(0).matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.*"));
    }
}
