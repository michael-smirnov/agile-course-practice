package ru.unn.agile.HypothecCalculator.viewmodel;


import java.util.ArrayList;
import java.util.List;

public class FakeHypothecLogger implements IHypothecLogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void addMessage(final String message) {
        log.add(message);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
