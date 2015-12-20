package ru.unn.agile.WeightConverter.infrastructure;

import ru.unn.agile.WeightConverter.viewmodel.ViewModel;
import ru.unn.agile.WeightConverter.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        WeightConverterTxtLogger realLogger =
            new WeightConverterTxtLogger("./ViewModel_Logger_Tests_Weight_Converter.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
