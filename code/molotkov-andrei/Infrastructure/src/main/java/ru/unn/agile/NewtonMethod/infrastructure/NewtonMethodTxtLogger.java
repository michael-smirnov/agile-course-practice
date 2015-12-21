package ru.unn.agile.NewtonMethod.infrastructure;

import ru.unn.NewtonMethod.viewModel.INewtonMethodLogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewtonMethodTxtLogger implements INewtonMethodLogger {
    private BufferedWriter logWriter;
    private final String logFileName;

    public NewtonMethodTxtLogger(final String logFileName) {
        this.logFileName = logFileName;
        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(final String message) {
        try {
            logWriter.write("< " + getCurrentDateTime() + " > " + message);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader newtonMethodLogReader;
        List<String> log = new ArrayList<>();
        try {
            newtonMethodLogReader = new BufferedReader(new FileReader(logFileName));
            String line = newtonMethodLogReader.readLine();

            while (line != null) {
                log.add(line);
                line = newtonMethodLogReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return log;
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
