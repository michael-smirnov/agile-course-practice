package ru.unn.agile.BitArray.viewmodel;

import java.util.List;

/**
 * Created by Jura on 19.12.2015.
 */
public interface IBitArrayLogger {
    void log(final String message);

    List<String> getLog();
}
