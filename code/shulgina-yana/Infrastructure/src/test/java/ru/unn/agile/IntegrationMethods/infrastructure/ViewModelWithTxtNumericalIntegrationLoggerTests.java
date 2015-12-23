package ru.unn.agile.IntegrationMethods.infrastructure;

import ru.unn.agile.IntegrationMethods.viewmodel.ViewModel;
import ru.unn.agile.IntegrationMethods.viewmodel.ViewModelTests;

import static org.junit.Assert.assertNotNull;

public class ViewModelWithTxtNumericalIntegrationLoggerTests extends ViewModelTests {
    private static final String NAME_OF_LOG_FILE =
            "./ViewModelWithTxtLoggerTests-lab3-shulgina.log";

    @Override
    public void setUp() {
        TxtNumericalIntegrationLogger realLogger =
                new TxtNumericalIntegrationLogger(NAME_OF_LOG_FILE);
        super.setViewModel(new ViewModel(realLogger));
    }

    @Override
    public void canViewModelAcceptLogger() {
        TxtNumericalIntegrationLogger realLogger =
                new TxtNumericalIntegrationLogger(NAME_OF_LOG_FILE);
        ViewModel viewModelWithRealLogger = new ViewModel(realLogger);

        assertNotNull(viewModelWithRealLogger);
    }
}
