package ru.unn.agile.WeightConverter.infrastructure;

import ru.unn.agile.WeightConverter.viewmodel.ViewModel;
import ru.unn.agile.WeightConverter.viewmodel.ViewModelTests;

public class ViewModelWithCsvLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        WeightConverterCsvLogger realLogger =
            new WeightConverterCsvLogger("./ViewModel_Logger_Tests_Weight_Converter.csv");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
