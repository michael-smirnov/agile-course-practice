package ru.unn.agile.arabicroman.viewmodel;

import java.util.List;

public interface ILogger {

    void addLogMessage(final String logMessage);
    List<String> getLogMessages();
}
