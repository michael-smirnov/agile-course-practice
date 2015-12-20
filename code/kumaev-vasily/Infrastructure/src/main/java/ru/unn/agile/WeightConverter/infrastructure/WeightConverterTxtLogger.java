package ru.unn.agile.WeightConverter.infrastructure;

import ru.unn.agile.WeightConverter.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeightConverterTxtLogger implements ILogger {
    private final BufferedWriter writer;
    private final String nameLogFile;

    public static String getActualDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return dataFormat.format(calendar.getTime());
    }

    public WeightConverterTxtLogger(final String nameLogFile) {
        BufferedWriter logWriter = null;
        this.nameLogFile = nameLogFile;

        try {
            logWriter = new BufferedWriter(new FileWriter(nameLogFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String logMessage) {
        try {
            writer.write(getActualDateAndTime() + " > " + logMessage);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(nameLogFile));
            String line = bufferedReader.readLine();

            while (line != null) {
                log.add(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
