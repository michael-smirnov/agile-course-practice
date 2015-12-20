package ru.unn.agile.AreaConverter.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class AreaConverterFakeLogger implements AreaConverterLogger {

    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void logMessage(final String string) {
        log.add(string);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
