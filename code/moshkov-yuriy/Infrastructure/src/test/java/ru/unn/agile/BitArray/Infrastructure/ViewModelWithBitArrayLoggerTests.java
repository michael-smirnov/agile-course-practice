package ru.unn.agile.BitArray.Infrastructure;

import ru.unn.agile.BitArray.viewmodel.ViewModel;
import ru.unn.agile.BitArray.viewmodel.ViewModelTests;

/**
 * Created by Jura on 19.12.2015.
 */
public class ViewModelWithBitArrayLoggerTests extends ViewModelTests {
    @Override
    public void initializeViewModel() {
        BitArrayLogger logger = new BitArrayLogger("./ViewModelWithBitArrayLoggerTests.log");
        setViewModel(new ViewModel(logger));
    }
}
