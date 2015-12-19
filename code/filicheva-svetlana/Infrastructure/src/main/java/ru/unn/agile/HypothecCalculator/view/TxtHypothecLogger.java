package ru.unn.agile.HypothecCalculator.view;

import ru.unn.agile.HypothecCalculator.viewmodel.IHypothecLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtHypothecLogger implements IHypothecLogger {
    private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private final BufferedWriter writer;
    private final String filename;

    public TxtHypothecLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    private static String currentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        String time = simpleDateFormat.format(calendar.getTime());
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public void addMessage(final String message) {
        try {
            writer.write(currentTime() + " > " + message);
            writer.newLine();
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
}
