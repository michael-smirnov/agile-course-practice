package ru.unn.agile.StatisticValueCalculator.infrastructure;

import ru.unn.agile.StatisticValueCalculator.viewmodel.StatisticCalculatorViewModel;
import ru.unn.agile.StatisticValueCalculator.viewmodel.StatisticCalculatorViewModelTestsOnLogging;

public class StatisticCalculatorViewModelTestsWithTxtLogger
        extends StatisticCalculatorViewModelTestsOnLogging {
    @Override
    public void setUp() {
        StatisticCalculatorViewModel viewModel = new StatisticCalculatorViewModel();
        viewModel.setLogger(new TxtLoggerOfStatisticCalculator("StatisticCalculatorLogger.log"));
        super.setViewModel(viewModel);
    }
}
