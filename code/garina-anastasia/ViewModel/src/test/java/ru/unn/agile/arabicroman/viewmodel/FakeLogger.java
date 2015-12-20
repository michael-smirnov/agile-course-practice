package ru.unn.agile.arabicroman.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private ArrayList<String> logMessages = new ArrayList<>();

    @Override
    public void addLogMessage(final String logMessage) {
        logMessages.add(logMessage);
    }

    @Override
    public List<String> getLogMessages() {
        return logMessages;
    }
}
