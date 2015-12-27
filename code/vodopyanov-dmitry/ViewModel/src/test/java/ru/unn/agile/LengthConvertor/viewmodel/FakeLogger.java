package ru.unn.agile.LengthConvertor.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ISimpleLogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void addLogLine(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
