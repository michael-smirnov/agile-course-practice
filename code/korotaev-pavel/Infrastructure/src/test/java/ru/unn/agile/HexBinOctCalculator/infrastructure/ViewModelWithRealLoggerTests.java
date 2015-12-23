package ru.unn.agile.HexBinOctCalculator.infrastructure;

import ru.unn.agile.HexBinOctCalculator.viewmodel.ViewModel;
import ru.unn.agile.HexBinOctCalculator.viewmodel.ViewModelTests;

public class ViewModelWithRealLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        RealLogger realLogger = new RealLogger("./ViewModelWithRealLoggerTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
