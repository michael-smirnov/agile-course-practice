package ru.unn.agile.StatisticValueCalculator.viewmodel;

import java.util.List;

public interface ILoggerOfStatisticCalculator {
    void addMessage(String description);
    List<String> getLog();
}
