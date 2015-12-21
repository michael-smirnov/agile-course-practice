package ru.unn.agile.CurrencyConverter.infrastructure;

import ru.unn.agile.CurrencyConverter.viewmodel.ILogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CurrencyConverterLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private final PrintWriter writer;
    private final String filename;

    private static String now() {
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
    public void log(final String str) {
        try {
            writer.write(now() + " > " + str);
            writer.println();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        Scanner scan;
        ArrayList<String> log = new ArrayList<String>();
        try {
            scan = new Scanner(new FileReader(filename));
            String str = scan.nextLine();

            while (str != null) {
                log.add(str);
                str = scan.nextLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
