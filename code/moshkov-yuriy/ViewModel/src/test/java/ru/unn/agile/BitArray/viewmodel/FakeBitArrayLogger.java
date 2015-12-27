package ru.unn.agile.BitArray.viewmodel;

import java.util.ArrayList;
import java.util.List;


public class FakeBitArrayLogger implements IBitArrayLogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(final String message) {
        log.add(message);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
