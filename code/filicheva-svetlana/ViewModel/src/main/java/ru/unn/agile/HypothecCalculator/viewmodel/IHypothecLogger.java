package ru.unn.agile.HypothecCalculator.viewmodel;


import java.util.List;

public interface IHypothecLogger {
    void addMessage(final String message);

    List<String> getLog();
}
