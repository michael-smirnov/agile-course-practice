package ru.unn.agile.Complex.viewmodel;

import java.util.List;

public interface ILogger {
    void addToLog(final String message);

    List<String> getLog();
}

