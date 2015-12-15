package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ViewModelLoggerTests {
    private ViewModel viewModel;
    private FakeHypothecLogger fakeLogger;

    @Before
    public void setUp() {
        fakeLogger = new FakeHypothecLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeHypothecLogger fakeLogger = new FakeHypothecLogger();
        ViewModel viewModel = new ViewModel(fakeLogger);

        assertNotNull(viewModel);
    }

    @Test (expected = IllegalArgumentException.class)
    public void cantCreateViewModelWithoutLogger() {
        ViewModel viewModel = new ViewModel(null);
    }

    @Test
    public void isLogEmptyAtTheStart() {
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenHouseCostChanged() {
        String rightMessage = "Установлено новое значение параметра \"Стоимость недвижимости\": 100";

        viewModel.setHouseCost("100");
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenHouseCostIsSame() {
        viewModel.setHouseCost("");
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
   }
}
