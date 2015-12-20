package ru.unn.agile.IntersectionOfSegments.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLogger_Tests.log";
    private static final String TEST_MESSAGE = "Test message for txt logger";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateTxtLogger() {
        assertNotNull(txtLogger);
    }

    @Test
    public void loggerCanCreateTxtFileOnDisk() {
        assertTrue(new File(FILENAME).isFile());
    }

    @Test
    public void canWriteLogMessageToTxtLog() {
        txtLogger.log(TEST_MESSAGE);

        String message = txtLogger.getLog().get(0);

        assertTrue(message.matches(".*" + TEST_MESSAGE));
    }

    @Test
    public void canWriteSeveralMessagesToTxtLog() {
        int sizeOfLog = 3;
        for (int i = 0; i < sizeOfLog; i++) {
            txtLogger.log(TEST_MESSAGE + i);
        }

        for (int i = 0; i < sizeOfLog; i++) {
            assertTrue(txtLogger.getLog().get(i).matches(".*" + TEST_MESSAGE + i + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        txtLogger.log(TEST_MESSAGE);
        String message = txtLogger.getLog().get(0);

        assertTrue(message.matches("^\\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
