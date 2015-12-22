package ru.unn.agile.HypothecCalculator.viewmodel;

import java.util.List;

public interface ILogger {
    void addMessage(final String message);

    List<String> getLog();
}
