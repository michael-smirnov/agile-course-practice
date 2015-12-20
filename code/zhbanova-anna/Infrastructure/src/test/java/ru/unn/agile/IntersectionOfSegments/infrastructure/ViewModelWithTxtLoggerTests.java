package ru.unn.agile.IntersectionOfSegments.infrastructure;

import ru.unn.agile.IntersectionOfSegments.viewmodel.ViewModel;
import ru.unn.agile.IntersectionOfSegments.viewmodel.ViewModelWithLoggerTests;

public class ViewModelWithTxtLoggerTests extends ViewModelWithLoggerTests {
    @Override
    public void setUp() {
        TxtLogger txtLogger =
            new TxtLogger("./ViewModel_with_TxtLogger_Tests.log");
        super.setViewModel(new ViewModel(txtLogger));
    }
}
