package ru.unn.agile.CurrencyConverter.infrastructure;

import ru.unn.agile.CurrencyConverter.viewmodel.ViewModel;
import ru.unn.agile.CurrencyConverter.viewmodel.ViewModelTests;

public class ViewModelWithLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        CurrencyConverterLogger realLogger =
            new CurrencyConverterLogger("./ViewModel_Logger_Tests.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
