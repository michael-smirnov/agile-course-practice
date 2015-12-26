package ru.unn.agile.HypothecCalculator.infrastructure;

import ru.unn.agile.HypothecCalculator.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private final FileWriter writer;
    private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private final String filename;

    public TxtLogger(final String filename) {
        this.filename = filename;

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(filename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fileWriter = null;
        }

        writer = fileWriter;
    }

    @Override
    public void addMessage(final String message) {
        try {
            writer.write(currentTime() + " > " + message);
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                log.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

    private static String currentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return simpleDateFormat.format(calendar.getTime());
    }

}
