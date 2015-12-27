package ru.unn.agile.Deque.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeDequeLogger implements IDequeLogger {
    private ArrayList<String> log;

    public FakeDequeLogger() {
        log = new ArrayList<>();
    }

    @Override
    public void log(final String message) {
        log.add(message);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
