package ru.unn.agile.LengthConvertor.infrastructure;

import ru.unn.agile.LengthConvertor.viewmodel.ISimpleLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LengthConvertorLogger implements ISimpleLogger {
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private final BufferedWriter writer;
    private final String filename;

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.GERMAN);
        return simpleDateFormat.format(calendar.getTime());
    }

    public LengthConvertorLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));

            logWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            logWriter.newLine();
            logWriter.write("<logger>");
            logWriter.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void addLogLine(final String s) {
        try {
            writer.write("  <event time=\"" + "[" + now() + "]" + "\" message=\"" + s + "\" />");
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> logMessage = new ArrayList<>();
        Pattern pattern = Pattern.compile("^  <event time=\"(?<date>.*)"
                                        + "\" message=\"(?<message>.*)\" />$");
        try {
            reader = new BufferedReader(new FileReader(filename));
            String logLine = reader.readLine();

            while (logLine != null) {
                Matcher matcher = pattern.matcher(logLine);
                if (matcher.matches()) {
                    logMessage.add(matcher.group("date") + " " + matcher.group("message"));
                }
                logLine = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logMessage;
    }
}
