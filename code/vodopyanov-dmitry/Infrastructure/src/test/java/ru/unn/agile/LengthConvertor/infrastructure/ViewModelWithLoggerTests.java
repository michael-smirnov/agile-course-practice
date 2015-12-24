package ru.unn.agile.LengthConvertor.infrastructure;

import ru.unn.agile.LengthConvertor.viewmodel.ViewModel;
import ru.unn.agile.LengthConvertor.viewmodel.ViewModelTests;

public class ViewModelWithLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        LengthConvertorLogger realLengthConvertorLogger =
            new LengthConvertorLogger("./ViewModelWithLoggerTests.xml");
        super.setOuterViewModel(new ViewModel(realLengthConvertorLogger));
    }
}
