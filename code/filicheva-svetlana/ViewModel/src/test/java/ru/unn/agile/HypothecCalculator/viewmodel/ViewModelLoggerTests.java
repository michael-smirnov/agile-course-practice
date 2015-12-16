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
        new ViewModel(null);
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
    public void noMessagesWhenHouseCostIsTheSame() {
        viewModel.setHouseCost("");
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
   }

    @Test
    public void isMessageAddedWhenDownPaymentChanged() {
        String rightMessage = "Установлено новое значение параметра \"Первоначальный взнос\": 20";

        viewModel.setDownPayment("20");
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenDownPaymentIsTheSame() {
        viewModel.setDownPayment("0");
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenCountOfPeriodsChanged() {
        String rightMessage = "Установлено новое значение параметра \"Срок ипотеки\": 12";

        viewModel.setCountOfPeriods("12");
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenCountOfPeriodsIsTheSame() {
        viewModel.setCountOfPeriods("");
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }



}
