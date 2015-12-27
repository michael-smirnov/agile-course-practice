package ru.unn.agile.WeightConverter.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final ArrayList<String> weightConverterLog = new ArrayList<>();

    @Override
    public void log(final String s) {
        weightConverterLog.add(s);
    }

    @Override
    public List<String> getLog() {
        return weightConverterLog;
    }
}
