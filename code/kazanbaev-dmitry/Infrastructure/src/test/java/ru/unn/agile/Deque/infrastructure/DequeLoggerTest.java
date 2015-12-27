package ru.unn.agile.Deque.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class DequeLoggerTest {
    private static final String PATH_TO_FILE = "./DequeLoggerTest.log";
    private DequeLogger logger;

    @Before
    public void initializeLogger() {
        logger = new DequeLogger(PATH_TO_FILE);
    }

    @Test
    public void canCreateDequeLogger() {
        assertNotNull(logger);
    }

    @Test
    public void byDefaultLogIsEmpty() {
        assertTrue(logger.getLog().isEmpty());
    }

    @Test
    public void didLoggerCreateTxtFileOnDisk() {
        assertTrue(new File(PATH_TO_FILE).isFile());
    }

    @Test
    public void doesLogMethodAddSomethingInLog() {
        logger.log("test");

        assertFalse(logger.getLog().isEmpty());
    }

    @Test
    public void canAddSeveralMessagesInLog() {
        logger.log("test");
        logger.log("test2");

        assertEquals(2, logger.getLog().size());
    }
}
