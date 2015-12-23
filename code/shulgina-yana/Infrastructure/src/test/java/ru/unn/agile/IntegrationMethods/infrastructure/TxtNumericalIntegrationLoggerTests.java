package ru.unn.agile.IntegrationMethods.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TxtNumericalIntegrationLoggerTests {
    private TxtNumericalIntegrationLogger txtLogger;
    private static final String NAME_OF_LOG_FILE = "./NumericalIntegrationLog.log";

    @Before
    public void setUp() {
        txtLogger = new TxtNumericalIntegrationLogger(NAME_OF_LOG_FILE);
    }

    @Test
    public void canFindLogFile() {
        try {
            new BufferedReader(new FileReader(NAME_OF_LOG_FILE));
        } catch (FileNotFoundException e) {
            fail("Error: log file wasn't found");
        }
    }

    @Test
    public void canWriteSomethingToLogFile() {
        String record = "some action";

        txtLogger.addRecord(record);
        String recordFromLogFile = txtLogger.getAllRecords().get(0);

        assertTrue(recordFromLogFile.contains(record));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] records = {"the first action", "the second action", "the third action"};
        boolean areRecordsLogged = true;

        for (int i = 0; i < records.length; i++) {
            txtLogger.addRecord(records[i]);
        }
        List<String> recordsFromLogFile = txtLogger.getAllRecords();
        for (int i = 0; i < recordsFromLogFile.size(); i++) {
            if (!recordsFromLogFile.get(i).contains(records[i])) {
                areRecordsLogged = false;
            }
        }

        assertTrue(areRecordsLogged);
    }

    @Test
    public void canSaveDateAndTimeOfAction() {
        txtLogger.addRecord("some action");

        String recordFromLogFile = txtLogger.getAllRecords().get(0);

        assertTrue(Pattern.matches("^.\\d{2}-\\D{3}-\\d{4} \\d{2}:\\d{2}:\\d{2}.*",
               recordFromLogFile));
    }
}
