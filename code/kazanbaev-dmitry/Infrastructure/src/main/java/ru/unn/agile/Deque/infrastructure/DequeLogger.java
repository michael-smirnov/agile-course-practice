package ru.unn.agile.Deque.infrastructure;

import ru.unn.agile.Deque.viewmodel.IDequeLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DequeLogger implements IDequeLogger {
    private FileWriter writer;
    private final String pathToFile;

    public DequeLogger(final String pathToFile) {
        this.pathToFile = pathToFile;

        try {
            writer = new FileWriter(pathToFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(final String message) {
        try {
            writer.write(new Date() + " > " + message + "\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(pathToFile));
            String message = reader.readLine();

            while (message != null) {
                log.add(message);
                message = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }
}
