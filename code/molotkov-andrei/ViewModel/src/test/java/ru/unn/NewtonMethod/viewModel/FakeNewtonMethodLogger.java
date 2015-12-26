package ru.unn.NewtonMethod.viewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FakeNewtonMethodLogger implements INewtonMethodLogger {
    private List<String> log = new ArrayList<>();

    @Override
    public void log(final String message) {
        log.add("<" + getCurrentDateAndTime() + "> " + message);
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    public String getCurrentDateAndTime() {
        SimpleDateFormat currentDateAndTime = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return currentDateAndTime.format(Calendar.getInstance().getTime());
    }
}
