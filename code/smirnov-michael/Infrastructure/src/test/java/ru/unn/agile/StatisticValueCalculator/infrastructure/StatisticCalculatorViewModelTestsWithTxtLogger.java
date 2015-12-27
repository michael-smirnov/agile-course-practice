package ru.unn.agile.StatisticValueCalculator.infrastructure;

import org.junit.Before;
import ru.unn.agile.StatisticValueCalculator.viewmodel.StatisticCalculatorViewModel;
import ru.unn.agile.StatisticValueCalculator.viewmodel.StatisticCalculatorViewModelTestsOnLogging;

public class StatisticCalculatorViewModelTestsWithTxtLogger
        extends StatisticCalculatorViewModelTestsOnLogging {
    @Before
    public void setUp() {
        StatisticCalculatorViewModel viewModel = new StatisticCalculatorViewModel(
                new TxtLoggerOfStatisticCalculator("StatisticCalculatorLogger.log"));
        super.setViewModel(viewModel);
    }
}
