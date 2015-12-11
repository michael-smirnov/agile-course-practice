package ru.unn.agile.Deque.viewmodel;

import java.util.ArrayList;

public interface IDequeLogger {
    void log(final String message);

    ArrayList<String> getLog();
}
