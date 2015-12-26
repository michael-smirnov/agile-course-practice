package ru.unn.agile.AreaConverter.infrastructure;

import ru.unn.agile.AreaConverter.viewmodel.ViewModel;
import ru.unn.agile.AreaConverter.viewmodel.ViewModelLoggerTest;

public class ViewModelWithAreaConverterTxtLoggerTest extends ViewModelLoggerTest {
    @Override
    public void setUp() {
        AreaConverterTxtLogger realLogger =
                new AreaConverterTxtLogger("./ViewModelWithAreaConverterTxtLoggerTest.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
