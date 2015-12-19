package ru.unn.agile.BitArray.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jura on 19.12.2015.
 */
public class FakeBitArrayLogger implements IBitArrayLogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(String message) {
        log.add(message);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
