package ru.unn.agile.NewtonMethod;

import org.junit.Before;
import ru.unn.NewtonMethod.viewModel.NewtonMethodViewModel;
import ru.unn.NewtonMethod.viewModel.NewtonMethodViewModelTests;
import ru.unn.agile.NewtonMethod.infrastructure.NewtonMethodTxtLogger;

public class NewtonMethodViewModelWithTxtLoggerTests extends NewtonMethodViewModelTests {
    @Before
    public void setUp() {
        final String logFileName = "./NewtonMethod-viewModelWithTxtLogger-tests.log";
        NewtonMethodTxtLogger txtLogger = new NewtonMethodTxtLogger(logFileName);
        super.setViewModel(new NewtonMethodViewModel(txtLogger));
    }
}
