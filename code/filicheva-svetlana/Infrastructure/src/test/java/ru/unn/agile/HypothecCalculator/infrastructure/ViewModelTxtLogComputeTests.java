package ru.unn.agile.HypothecCalculator.view;

import ru.unn.agile.HypothecCalculator.viewmodel.ViewModel;
import ru.unn.agile.HypothecCalculator.viewmodel.ViewModelLogComputeTests;

public class ViewModelTxtLogComputeTests extends ViewModelLogComputeTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger("ViewModelTxtLogComputeTests.log");
        super.setViewModel(new ViewModel(realLogger));
        super.loadExample();
    }

    public ViewModelTxtLogComputeTests(final String s) {
        super(s);
    }

}
