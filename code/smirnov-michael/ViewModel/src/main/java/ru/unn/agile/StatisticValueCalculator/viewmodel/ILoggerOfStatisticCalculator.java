package ru.unn.agile.StatisticValueCalculator.viewmodel;

import java.util.List;

public interface ILoggerOfStatisticCalculator {
    void set(String message);
    List<String> getLog();
}
