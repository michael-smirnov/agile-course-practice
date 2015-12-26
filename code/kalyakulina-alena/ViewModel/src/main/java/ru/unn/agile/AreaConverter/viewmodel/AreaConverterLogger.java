package ru.unn.agile.AreaConverter.viewmodel;

import java.util.List;

public interface AreaConverterLogger {
    void logMessage(final String string);

    List<String> getLog();
}
