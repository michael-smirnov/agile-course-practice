package ru.unn.agile.BitArray.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class BitArrayLoggerTests {
    private static final String FILENAME = "./BitArray_Tests.log";
    private BitArrayLogger logger;

    @Before
    public void initializeBitArrayLogger() {
        logger = new BitArrayLogger(FILENAME);
    }

    @Test
    public void canCreateBitArrayLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test
    public void didLoggerCreateTxtFileOnDisk() {
        assertTrue(new File(FILENAME).isFile());
    }

    @Test
    public void canLoggerAddMessageToLog() {
        String message = "TEST";

        logger.log(message);

        assertFalse(logger.getLog().isEmpty());
    }

    @Test
    public void canLoggerSaveMultipleMessages() {
        String messageFirst = "Test 1";
        String messageSecond = "Test 2";

        logger.log(messageFirst);
        logger.log(messageSecond);

        assertEquals(2, logger.getLog().size());
    }
}
