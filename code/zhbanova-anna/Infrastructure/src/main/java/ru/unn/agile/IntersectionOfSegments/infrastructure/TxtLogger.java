package ru.unn.agile.IntersectionOfSegments.infrastructure;

import ru.unn.agile.IntersectionOfSegments.viewmodel.ILogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private final String logFileName;
    private final BufferedWriter logWriter;

    private static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return simpleDateFormat.format(cal.getTime());
    }

    public TxtLogger(final String logFileName) {
        this.logFileName = logFileName;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logWriter = writer;
    }

    @Override
    public void log(final String msg) {
        try {
            logWriter.write(getCurrentDate() + " > " + msg);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        List<String> log = new ArrayList<>();

        try {
            log = Files.readAllLines(Paths.get(logFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }
}
