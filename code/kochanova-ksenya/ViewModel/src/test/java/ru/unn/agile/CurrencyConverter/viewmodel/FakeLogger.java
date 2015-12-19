package ru.unn.agile.CurrencyConverter.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final ArrayList<String> record = new ArrayList<>();

    @Override
    public void record(final String str) {
        record.add(str);
    }

    @Override
    public List<String> getRecord() {
        return record;
    }
}
