package ru.unn.NewtonMethod.viewModel;

import java.util.List;

public interface INewtonMethodLogger {
    String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    void log(final String message);

    List<String> getLog();
}
