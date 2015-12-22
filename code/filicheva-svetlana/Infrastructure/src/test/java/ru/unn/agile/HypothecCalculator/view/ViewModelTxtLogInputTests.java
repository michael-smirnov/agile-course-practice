package ru.unn.agile.HypothecCalculator.view;

import ru.unn.agile.HypothecCalculator.viewmodel.ViewModel;
import ru.unn.agile.HypothecCalculator.viewmodel.ViewModelLogInputTests;

public class ViewModelTxtLogInputTests extends ViewModelLogInputTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger("log/ViewModelTxtLogInputTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }

}
