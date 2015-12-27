package ru.unn.agile.BitArray.infrastructure;

import ru.unn.agile.BitArray.viewmodel.IBitArrayLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BitArrayLogger implements IBitArrayLogger {
    private final BufferedWriter writerToLog;
    private final String fileName;

    public BitArrayLogger(final String fileName) {
        this.fileName = fileName;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writerToLog = logWriter;
    }

    @Override
    public void log(final String message) {
        try {
            writerToLog.write(new Date() + ": " + message);
            writerToLog.newLine();
            writerToLog.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader readerFromLog;
        ArrayList<String> log = new ArrayList<String>();
        try {
            readerFromLog = new BufferedReader(new FileReader(fileName));
            String string = readerFromLog.readLine();

            while (string != null) {
                log.add(string);
                string = readerFromLog.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }
}
