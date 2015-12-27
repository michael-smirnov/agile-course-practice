package ru.unn.agile.arabicroman.viewmodel;

import java.util.List;

public interface ILogger {

    void add(final String logMessage);
    List<String> getMessages();
}
