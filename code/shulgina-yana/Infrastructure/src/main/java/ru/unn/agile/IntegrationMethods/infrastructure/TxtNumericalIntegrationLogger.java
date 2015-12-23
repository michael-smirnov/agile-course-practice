package ru.unn.agile.IntegrationMethods.infrastructure;

import ru.unn.agile.IntegrationMethods.viewmodel.NumericalIntegrationLogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtNumericalIntegrationLogger implements NumericalIntegrationLogger {
    private final String nameOfLogFile;
    private BufferedWriter bufferedWriter;

    public TxtNumericalIntegrationLogger(final String nameOfLogFile) {
        this.nameOfLogFile = nameOfLogFile;
        try {
            File logFile = new File(nameOfLogFile);
            FileOutputStream outputStreamForLogFile = new FileOutputStream(logFile);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStreamForLogFile));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void addRecord(final String newRecord) {
        try {
            bufferedWriter.write("[" + dateAndTime() + "] " + newRecord);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<String> getAllRecords() {
        ArrayList<String> records = new ArrayList<String>();
        try {
            File logFile = new File(nameOfLogFile);
            FileInputStream inputStreamForLogFile = new FileInputStream(logFile);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStreamForLogFile));
            String currentRecord = bufferedReader.readLine();
            while (currentRecord != null) {
                records.add(currentRecord);
                currentRecord = bufferedReader.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return records;
    }

    private String dateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(calendar.getTime());
    }
}
