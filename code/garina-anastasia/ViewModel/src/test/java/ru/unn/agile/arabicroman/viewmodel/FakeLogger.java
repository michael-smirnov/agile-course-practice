package ru.unn.agile.arabicroman.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private ArrayList<String> logMessages = new ArrayList<>();

    @Override
    public void add(final String logMessage) {
        logMessages.add(logMessage);
    }

    @Override
    public List<String> getMessages() {
        return logMessages;
    }
}
