package ru.unn.agile.StatisticValueCalculator.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLoggerOfStatisticCalculator implements ILoggerOfStatisticCalculator {
    private ArrayList<String> log = new ArrayList<>();

    @Override
    public void addMessage(final String description) {
        log.add(description);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
