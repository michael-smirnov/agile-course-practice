package ru.unn.agile.HypothecCalculator.infrastructure;

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

public class TxtLoggerTests {
    private static final String FILENAME = "TxtLoggerTests.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(txtLogger);
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
    public void isLogEmptyByDefault() {
        List<String> log = txtLogger.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void canWriteLogMessage() {
        txtLogger.addMessage("Message");

        List<String> log = txtLogger.getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void isLogMessageCorrect() {
        String rightMessage = "Some interesting message";

        txtLogger.addMessage(rightMessage);

        String actualMessage = txtLogger.getLog().get(0);
        assertThat(actualMessage, containsString(rightMessage));
    }

    @Test
    public void canWriteSeveralLogMessages() {
        final int numberOfMessages = 5;

        addSeveralMessages(numberOfMessages);

        List<String> actualMessages = txtLogger.getLog();
        assertEquals(numberOfMessages, actualMessages.size());
    }

    @Test
    public void areSeveralMessagesCorrect() {
        final int numberOfMessages = 5;

        String[] rightMessages = addSeveralMessages(numberOfMessages);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < numberOfMessages; i++) {
            assertThat(actualMessages.get(i), containsString(rightMessages[i]));
        }
    }

    @Test
    public void doesMessageContainDateAndTime() {
        String rightMessage = "Another interesting message";

        txtLogger.addMessage(rightMessage);

        String actualMessage = txtLogger.getLog().get(0);
        assertTrue(Pattern.matches(
                "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} > .*", actualMessage
        ));
    }

    private String[] addSeveralMessages(final int numberOfMessages) {
        String[] rightMessages = new String[numberOfMessages];

        for (int i = 0; i < numberOfMessages; i++) {
            rightMessages[i] = "Message №" + i;
            txtLogger.addMessage(rightMessages[i]);
        }

        return rightMessages;
    }
}
