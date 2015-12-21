package ru.unn.agile.HexBinOctCalculator.infrastructure;

import ru.unn.agile.HexBinOctCalculator.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RealLogger implements ILogger {
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private final String filename;
    private final PrintWriter writer;

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return dateFormat.format(calendar.getTime());
    }

    public RealLogger(final String filename) {
        this.filename = filename;
        PrintWriter logWriter = null;
        try {
            logWriter = new PrintWriter(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writer.println(now() + " > " + s);
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
            String receivedLine = reader.readLine();

            while (receivedLine != null) {
                log.add(receivedLine);
                receivedLine = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }
}
