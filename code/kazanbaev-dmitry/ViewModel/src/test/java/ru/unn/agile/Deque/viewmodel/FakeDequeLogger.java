package ru.unn.agile.Deque.viewmodel;

import java.util.ArrayList;

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
    public ArrayList<String> getLog() {
        return log;
    }
}
