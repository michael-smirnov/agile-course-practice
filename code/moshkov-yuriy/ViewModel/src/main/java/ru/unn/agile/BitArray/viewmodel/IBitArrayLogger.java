package ru.unn.agile.BitArray.viewmodel;

import java.util.List;

public interface IBitArrayLogger {
    void log(final String message);

    List<String> getLog();
}
