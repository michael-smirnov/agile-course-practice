package ru.unn.agile.arabicroman.infrastructure;

import ru.unn.agile.arabicroman.viewmodel.ViewModelWithLoggerTests;
import ru.unn.agile.arabicroman.viewmodel.ArabicRomanConverterViewModel;

public class TextLoggerTestsForViewModel extends ViewModelWithLoggerTests {
    @Override
    public void setUp() {
        TextLogger actualTextLogger = new TextLogger("./TextLoggerTestsForViewModel.log");
        setViewModelWithLogger(new ArabicRomanConverterViewModel(actualTextLogger));
    }
}
