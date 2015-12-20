package ru.unn.agile.IntersectionOfSegments.viewmodel;

import java.util.List;

public interface ILogger {
    String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";

    void log(final String msg);
    List<String> getLog();
}
