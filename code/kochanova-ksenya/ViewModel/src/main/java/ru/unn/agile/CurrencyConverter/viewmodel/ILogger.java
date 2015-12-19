package ru.unn.agile.CurrencyConverter.viewmodel;

import java.util.List;

public interface ILogger {
    void record(final String str);

    List<String> getRecord();
}
