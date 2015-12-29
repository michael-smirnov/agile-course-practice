package ru.unn.agile.StatisticValueCalculator.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class StatisticCalculatorTxtLoggerTests {
    private final String logPath = "./Logger-tests_"  + this.hashCode() + ".log";;
    private TxtLoggerOfStatisticCalculator logger;

    @Before
    public void setUp() {
        logger = new TxtLoggerOfStatisticCalculator(logPath);
    }

    @Test
    public void canInstantiateTxtLogger() {
        assertNotNull(logger);
    }

    @Test
    public void isLogFileCreatedAfterLoggerHadBeenInstantiated() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(logPath));
            reader.close();
        } catch (FileNotFoundException e) {
            fail("File " + logPath + " hadn't been created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canWriteMessageIntoLog() {
        String message = "Just a test message";

        logger.set(message);
        assertTrue(logger.getLog().get(0).matches(".*" + message + "$"));
    }

    @Test
    public void doesLogMessageContainsDateAndTime() {
        logger.set("Test");
        assertTrue(logger.getLog().get(0).
                matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @After
    public void deleteTestLogFile() throws IOException {
        logger.dispose();
        Files.delete(Paths.get(logPath));
    }
}
