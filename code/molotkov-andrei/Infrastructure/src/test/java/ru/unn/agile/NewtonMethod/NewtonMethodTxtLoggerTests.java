package ru.unn.agile.NewtonMethod;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.NewtonMethod.infrastructure.NewtonMethodTxtLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class NewtonMethodTxtLoggerTests {
    private static final String LOG_FILE_NAME = "./NewtonMethod-txtLogger-tests.log";
    private static final String TEST_MESSAGE = "Test message for txt logger";
    private NewtonMethodTxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new NewtonMethodTxtLogger(LOG_FILE_NAME);
    }

    @Test
    public void canCreateNewtonMethodTxtLogger() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(LOG_FILE_NAME));
        } catch (FileNotFoundException e) {
            fail("File " + LOG_FILE_NAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteMessageToTxtLog() {
        txtLogger.log(TEST_MESSAGE);

        assertEquals(1, txtLogger.getLog().size());
    }

    @Test
    public void isTxtLogContainProperMessage() {
        txtLogger.log(TEST_MESSAGE);

        String message = txtLogger.getLog().get(0);

        assertThat(message, matchesPattern(".*" + TEST_MESSAGE));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        for (int i = 0; i < 10; i++) {
            txtLogger.log(TEST_MESSAGE + i);
        }

        assertEquals(10, txtLogger.getLog().size());
    }

    @Test
    public void doesLogContainWritingDateAndTime() {
        txtLogger.log(TEST_MESSAGE);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern("^< \\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
