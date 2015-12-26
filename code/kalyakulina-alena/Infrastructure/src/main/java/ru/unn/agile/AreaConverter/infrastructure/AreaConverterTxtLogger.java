package ru.unn.agile.AreaConverter.infrastructure;

import ru.unn.agile.AreaConverter.viewmodel.AreaConverterLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AreaConverterTxtLogger implements AreaConverterLogger {
    private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private final BufferedWriter logWriter;
    private final String fileName;

    public AreaConverterTxtLogger(final String fileName) {
        this.fileName = fileName;

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        logWriter = writer;
    }

    @Override
    public void logMessage(final String string) {
        try {
            logWriter.write(currentTime() + " > " + string);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader logReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            logReader = new BufferedReader(new FileReader(fileName));
            String line = logReader.readLine();

            while (line != null) {
                log.add(line);
                line = logReader.readLine();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return log;
    }

    private static String currentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }
}
