package ru.unn.agile.NewtonMethod.infrastructure;

import ru.unn.NewtonMethod.viewModel.INewtonMethodLogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewtonMethodTxtLogger implements INewtonMethodLogger {
    private final String logFileName;
    private OutputStreamWriter logStreamWriter;

    public NewtonMethodTxtLogger(final String logFileName) {
        this.logFileName = logFileName;
        FileOutputStream logFileOutputStream;
        try {
            logFileOutputStream = new FileOutputStream(new File(logFileName));
            logStreamWriter = new OutputStreamWriter(logFileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(final String message) {
        try {
            logStreamWriter.write("< " + getCurrentDateTime() + " > " + message + "\n");
            logStreamWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        FileInputStream logFileInputStream;
        BufferedReader newtonMethodLogReader;
        List<String> log = new ArrayList<>();
        try {
            logFileInputStream = new FileInputStream(new File(logFileName));
            newtonMethodLogReader = new BufferedReader(new InputStreamReader(logFileInputStream));

            String line;
            while ((line = newtonMethodLogReader.readLine()) != null) {
                log.add(line);
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
