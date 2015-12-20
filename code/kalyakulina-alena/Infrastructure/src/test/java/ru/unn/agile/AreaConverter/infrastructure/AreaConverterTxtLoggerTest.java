package ru.unn.agile.AreaConverter.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class AreaConverterTxtLoggerTest {
    private static final String FILENAME = "./AreaConverterTxtLoggerTest.log";
    private AreaConverterTxtLogger logger;

    @Before
    public void setUp() {
        logger = new AreaConverterTxtLogger(FILENAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException exception) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteMessageToLog() {
        String message = "Message";

        logger.logMessage(message);

        assertThat(logger.getLog().get(0), containsString(message));
    }

    @Test
    public void canWriteMultipleMessagesToLog() {
        String[] messages = {"Message 1", "Message 2"};

        logger.logMessage(messages[0]);
        logger.logMessage(messages[1]);

        List<String> messagesInLog = logger.getLog();
        assertThat(messagesInLog.get(0), containsString(messages[0]));
        assertThat(messagesInLog.get(1), containsString(messages[1]));
    }

    @Test
    public void canWriteDataAndTimeToLog() {
        String message = "Message";

        logger.logMessage(message);

        assertTrue(Pattern.matches("^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} > .*",
                logger.getLog().get(0)));
    }
}
