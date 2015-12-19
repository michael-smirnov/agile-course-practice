package ru.unn.agile.HypothecCalculator.view;

import ru.unn.agile.HypothecCalculator.viewmodel.ViewModel;
import ru.unn.agile.HypothecCalculator.viewmodel.IHypothecLogger;
import ru.unn.agile.HypothecCalculator.viewmodel.ViewModelLogInputTests;

public class ViewModelTxtLogInputTests extends ViewModelLogInputTests {
    @Override
    public void setUp() {
        IHypothecLogger realLogger = new TxtHypothecLogger("log/ViewModelTxtLogInputTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
