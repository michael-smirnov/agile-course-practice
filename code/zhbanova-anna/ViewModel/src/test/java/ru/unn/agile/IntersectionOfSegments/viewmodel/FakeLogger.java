package ru.unn.agile.IntersectionOfSegments.viewmodel;

import java.util.ArrayList;
import java.util.List;


public class FakeLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(final String msg) {
        log.add(msg);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
