package ru.unn.agile.StatisticValueCalculator.infrastructure;

import ru.unn.agile.StatisticValueCalculator.viewmodel.ILoggerOfStatisticCalculator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLoggerOfStatisticCalculator implements ILoggerOfStatisticCalculator {
    private static final String DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String logPath;
    private BufferedWriter logWriter;

    private static String getFormattedCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_TIME_FORMAT, Locale.ENGLISH);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public TxtLoggerOfStatisticCalculator(final String logPath) {
        this.logPath = logPath;

        try {
            logWriter = new BufferedWriter(new FileWriter(logPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(final String message) {
        try {
            logWriter.write(getFormattedCurrentTime() + " > " + message);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> log = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(logPath));

            String currentLine = reader.readLine();
            while (currentLine != null) {
                log.add(currentLine);
                currentLine = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return log;
    }
}
