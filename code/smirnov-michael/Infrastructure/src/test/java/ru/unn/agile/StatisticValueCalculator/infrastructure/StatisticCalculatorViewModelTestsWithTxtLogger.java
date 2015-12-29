package ru.unn.agile.StatisticValueCalculator.infrastructure;

import org.junit.After;
import org.junit.Before;
import ru.unn.agile.StatisticValueCalculator.viewmodel.StatisticCalculatorViewModel;
import ru.unn.agile.StatisticValueCalculator.viewmodel.StatisticCalculatorViewModelTestsOnLogging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StatisticCalculatorViewModelTestsWithTxtLogger
        extends StatisticCalculatorViewModelTestsOnLogging {
    private final String logPath = "./Logger-with-view-model-tests_" + this.hashCode() + ".log";

    @Before
    public void setUp() {
        StatisticCalculatorViewModel viewModel = new StatisticCalculatorViewModel(
                new TxtLoggerOfStatisticCalculator(logPath));
        super.setViewModel(viewModel);
    }

    @After
    public void disposeLogger() {
        super.disposeLogger();
        try {
            Files.delete(Paths.get(logPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
