package ru.unn.agile.HexBinOctCalculator.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.containsString;

public class RealLoggerTests {
    private static final String FILENAME = "./RealLoggerTests.log";
    private RealLogger realLogger;

    @Before
    public void setUp() {
        realLogger = new RealLogger(FILENAME);
    }

    @Test
    public void canCreateLogFileWithCurrentName() {
        assertNotNull(realLogger);
    }

    @Test
    public void canCreateLogFileInFolder() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteOneMessageIntoLog() {
        realLogger.log("Test message");
        String message = realLogger.getLog().get(0);

        assertThat(message, containsString("Test message"));
    }

    @Test
    public void canWriteSeveralMessagesIntoLog() {
        realLogger.log("Test message 1");
        realLogger.log("Test message 2");
        List<String> actualMessages = realLogger.getLog();

        assertThat(actualMessages.get(0), containsString("Test message 1"));
        assertThat(actualMessages.get(1), containsString("Test message 2"));

    }

    @Test
    public void logHasDateAndTime() {
        realLogger.log("Test message");
        String message = realLogger.getLog().get(0);

        assertTrue(Pattern.matches("^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} > .*", message));
    }
}
