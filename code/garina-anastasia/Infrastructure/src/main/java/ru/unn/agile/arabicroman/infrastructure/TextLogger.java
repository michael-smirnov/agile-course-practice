package ru.unn.agile.arabicroman.infrastructure;

import ru.unn.agile.arabicroman.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class TextLogger implements ILogger {
    private final String fileName;
    private BufferedWriter loggerWriter;

    public TextLogger(final String fileName) {
        this.fileName = fileName;
        try {
            loggerWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(final String logMessage) {
        try {
            loggerWriter.write(currentDateAndTime() + " > " + logMessage);
            loggerWriter.newLine();
            loggerWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getMessages() {
        BufferedReader loggerReader;
        ArrayList<String> logMessages = new ArrayList<String>();
        try {
            loggerReader = new BufferedReader(new FileReader(fileName));
            String message = loggerReader.readLine();
            while (message != null) {
                logMessages.add(message);
                message = loggerReader.readLine();
            }
            loggerReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return logMessages;
    }

    private String currentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        return date.format(calendar.getTime());
    }
}
