package ru.unn.agile.Complex.infrastructure;

import ru.unn.agile.Complex.viewmodel.ILogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Logger implements ILogger {
    private final String fileName;
    private BufferedWriter writer;

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
        return dateFormat.format(calendar.getTime());
    }

    public Logger(final String fileName) {
        this.fileName = fileName;

        writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void addToLog(final String message) {
        try {
            writer.write(getCurrentTime() + " : " + message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                log.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return log;
    }
}
