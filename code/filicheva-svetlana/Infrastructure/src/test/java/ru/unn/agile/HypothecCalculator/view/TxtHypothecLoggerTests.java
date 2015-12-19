package ru.unn.agile.HypothecCalculator.view;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;
import java.util.regex.Pattern;

import java.util.List;

public class TxtHypothecLoggerTests {
    private static final String FILENAME
            = "log/TxtHypothecLoggerTests.log";
    private TxtHypothecLogger txtHypothecLogger;

    @Before
    public void setUp() {
        txtHypothecLogger = new TxtHypothecLogger(FILENAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(txtHypothecLogger);
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
    public void isLogEmptyAtTheStart() {
        List<String> log = txtHypothecLogger.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void canWriteLogMessage() {
        String rightMessage = "Some interesting message";

        txtHypothecLogger.addMessage(rightMessage);

        String actualMessage = txtHypothecLogger.getLog().get(0);
        assertThat(actualMessage, containsString(rightMessage));
    }

    @Test
    public void canWriteSeveralMessages() {
        final int numberOfMessages = 8;
        String[] rightMessages = new String[numberOfMessages];

        for (int i = 0; i < numberOfMessages; i++) {
            rightMessages[i] = "Message №" + i;
            txtHypothecLogger.addMessage(rightMessages[i]);
        }

        List<String> actualMessages = txtHypothecLogger.getLog();
        assertEquals(numberOfMessages, actualMessages.size());
        for (int i = 0; i < numberOfMessages; i++) {
            assertThat(actualMessages.get(i), containsString(rightMessages[i]));
        }
    }

    @Test
    public void canWriteTime() {
        String rightMessage = "Another interesting message";

        txtHypothecLogger.addMessage(rightMessage);

        String actualMessage = txtHypothecLogger.getLog().get(0);
        assertTrue(Pattern.matches(
                "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} > .*", actualMessage
        ));
    }
}
