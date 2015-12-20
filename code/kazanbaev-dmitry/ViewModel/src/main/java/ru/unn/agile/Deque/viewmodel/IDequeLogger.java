package ru.unn.agile.Deque.viewmodel;

import java.util.List;

public interface IDequeLogger {
    void log(final String message);

    List<String> getLog();
}
