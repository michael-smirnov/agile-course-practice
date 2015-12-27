package ru.unn.agile.CurrencyConverter.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.fail;

public class CurrencyConverterLoggerTests {
    private static final String FILENAME = "./Logger_Tests.log";
    private CurrencyConverterLogger logger;

    @Before
    public void setUp() {
        logger = new CurrencyConverterLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canRecordMessage() {
        String testMess = "BLA BLA BLA";

        logger.log(testMess);

        String message = logger.getLog().get(0);
        assertTrue(message.contains(testMess));
    }

    @Test
    public void canRecordSeveralMessage() {
        String[] messages = {"BLA BLA BLA1", "BLA BLA BLA2"};

        logger.log(messages[0]);
        logger.log(messages[1]);

        List<String> actualMessages = logger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertTrue(actualMessages.get(i).contains(messages[i]));
        }
    }

    @Test
    public void isLogContainsTimeAndDate() {
        String testMess = "mimimimimi";

        logger.log(testMess);

        String message = logger.getLog().get(0);
        assertTrue(message.matches("^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
