package ru.unn.agile.HexBinOctCalculator.infrastructure;

import ru.unn.agile.HexBinOctCalculator.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RealLogger implements ILogger {
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private final String filename;
    private final BufferedWriter writer;

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return dateFormat.format(calendar.getTime());
    }

    public RealLogger(final String filename) {
        this.filename = filename;
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writer.write(now() + " > " + s);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader buffReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            buffReader = new BufferedReader(new FileReader(filename));
            String line = buffReader.readLine();

            while (line != null) {
                log.add(line);
                line = buffReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }
}
