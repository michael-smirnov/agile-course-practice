package ru.unn.agile.CurrencyConverter.infrastructure;

import ru.unn.agile.CurrencyConverter.viewmodel.ILogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CurrencyConverterLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private final PrintWriter writer;
    private final String filename;

    private static String timeAndDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.US);
        return date.format(calendar.getTime());
    }

    public CurrencyConverterLogger(final String filename) {
        this.filename = filename;

        PrintWriter logWriter = null;
        try {
            logWriter = new PrintWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writer.println(timeAndDate() + " > " + s);
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        Scanner scanner;
        ArrayList<String> log = new ArrayList<String>();
        try {
            scanner = new Scanner(new FileReader(filename));
            String line = scanner.nextLine();

            while (line != null) {
                log.add(line);
                line = scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
