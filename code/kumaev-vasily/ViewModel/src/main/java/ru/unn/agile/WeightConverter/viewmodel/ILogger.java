package ru.unn.agile.WeightConverter.viewmodel;

import java.util.List;

public interface ILogger {
    void log(final String s);

    List<String> getLog();
}
