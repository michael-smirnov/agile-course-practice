package ru.unn.agile.Deque.infrastructure;

import ru.unn.agile.Deque.viewmodel.DequeViewModel;
import ru.unn.agile.Deque.viewmodel.DequeViewModelTest;

public class DequeViewModelWithDequeLoggerTest extends DequeViewModelTest {
    @Override
    public void initializeDequeViewModel() {
        DequeLogger realLogger = new DequeLogger("./DequeViewModelWithDequeLoggerTest.log");
        setViewModel(new DequeViewModel(realLogger));
    }
}
