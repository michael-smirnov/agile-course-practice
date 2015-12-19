package ru.unn.agile.BitArray.infrastructure;

import ru.unn.agile.BitArray.viewmodel.ViewModel;
import ru.unn.agile.BitArray.viewmodel.ViewModelTests;

public class ViewModelWithBitArrayLoggerTests extends ViewModelTests {
    @Override
    public void initializeViewModel() {
        BitArrayLogger logger = new BitArrayLogger("./ViewModelWithBitArrayLoggerTests.log");
        setViewModel(new ViewModel(logger));
    }
}
