package ru.unn.agile.LengthConvertor.viewmodel;

import java.util.List;

public interface ISimpleLogger {
    void addLogLine(final String s);

    List<String> getLog();
}
