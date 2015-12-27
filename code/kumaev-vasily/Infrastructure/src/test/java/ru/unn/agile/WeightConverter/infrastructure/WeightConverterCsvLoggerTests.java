package ru.unn.agile.WeightConverter.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static junit.framework.TestCase.assertNotNull;

public class WeightConverterCsvLoggerTests {
    private WeightConverterCsvLogger weightConverterCsvLogger;
    private static final String LOG_FILE_NAME = "./TxtLogger_Tests-lab3-weight-converter.csv";
    private static final String TEST_MESSAGE = "Test message";

    @Before
    public void setUp() {
        weightConverterCsvLogger = new WeightConverterCsvLogger(LOG_FILE_NAME);
    }

    @Test
    public void canCreateTxtLoggerWithFileName() {
        assertNotNull(weightConverterCsvLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        assertTrue(new File(LOG_FILE_NAME).isFile());
    }

    @Test
    public void canWriteMessageToLog() {
        weightConverterCsvLogger.log(TEST_MESSAGE);

        assertEquals(1, weightConverterCsvLogger.getLog().size());
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"First test message", "Second test message"};

        weightConverterCsvLogger.log(messages[0]);
        weightConverterCsvLogger.log(messages[1]);

        assertEquals(2, weightConverterCsvLogger.getLog().size());
    }

    @Test
    public void isLineLogContainDateAndTime() {
        weightConverterCsvLogger.log(TEST_MESSAGE);
        String line = weightConverterCsvLogger.getLog().get(0);

        assertTrue(line.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
