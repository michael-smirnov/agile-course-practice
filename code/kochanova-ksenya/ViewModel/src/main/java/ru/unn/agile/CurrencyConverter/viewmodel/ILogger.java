package ru.unn.agile.CurrencyConverter.viewmodel;

import java.util.List;

public interface ILogger {
    void log(final String str);

    List<String> getLog();
}
