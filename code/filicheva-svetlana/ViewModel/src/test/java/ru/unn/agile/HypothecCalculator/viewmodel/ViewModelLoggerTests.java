package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.HypothecsCalculator.model.Hypothec;

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
        String houseCost = "100";
        String rightMessage = "Установлено новое значение параметра \"Стоимость недвижимости\": " + houseCost;

        viewModel.setHouseCost(houseCost);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenHouseCostIsTheSame() {
        String defaultHouseCost = viewModel.getHouseCost();

        viewModel.setHouseCost(defaultHouseCost);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
   }

    @Test
    public void isMessageAddedWhenDownPaymentChanged() {
        String downPayment = "20";
        String rightMessage = "Установлено новое значение параметра \"Первоначальный взнос\": " + downPayment;

        viewModel.setDownPayment(downPayment);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenDownPaymentIsTheSame() {
        String defaultDownPayment = viewModel.getDownPayment();

        viewModel.setDownPayment(defaultDownPayment);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenCountOfPeriodsChanged() {
        String countOfPeriods = "12";
        String rightMessage = "Установлено новое значение параметра \"Срок ипотеки\": " + countOfPeriods;

        viewModel.setCountOfPeriods(countOfPeriods);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenCountOfPeriodsIsTheSame() {
        String defaultCountOfPeriods = viewModel.getCountOfPeriods();

        viewModel.setCountOfPeriods(defaultCountOfPeriods);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenInterestRateChanged() {
        String interestRate = "rereddd";
        String rightMessage = "Установлено новое значение параметра \"Процентная ставка\": " + interestRate;

        viewModel.setInterestRate(interestRate);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenInterestRateIsTheSame() {
        String defaultInterestRate = viewModel.getInterestRate();

        viewModel.setInterestRate(defaultInterestRate);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenFlatFeeChanged() {
        String flatFee = "qwerty";
        String rightMessage = "Установлено новое значение параметра \"Единовременные комиссии\": " + flatFee;

        viewModel.setFlatFee(flatFee);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenFlatFeeIsTheSame() {
        String defaultFlatFee = viewModel.getFlatFee();

        viewModel.setFlatFee(defaultFlatFee);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenMonthlyFeeChanged() {
        String monthlyFee = "1200";
        String rightMessage = "Установлено новое значение параметра \"Ежемесячные комиссии\": " + monthlyFee;

        viewModel.setMonthlyFee(monthlyFee);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenMonthlyFeeIsTheSame() {
        String defaultMonthlyFee = viewModel.getMonthlyFee();

        viewModel.setMonthlyFee(defaultMonthlyFee);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenStartMonthChanged() {
        String startMonth = "ййй";
        String rightMessage = "Установлено новое значение параметра \"Месяц начала выплат\": " + startMonth;

        viewModel.setStartMonth(startMonth);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenStartMonthIsTheSame() {
        String defaultStartMonth = viewModel.getStartMonth();

        viewModel.setStartMonth(defaultStartMonth);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenStartYearChanged() {
        String startYear = "12345";
        String rightMessage = "Установлено новое значение параметра \"Год начала выплат\": " + startYear;

        viewModel.setStartYear(startYear);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenStartYearIsTheSame() {
        String defaultStartYear = viewModel.getStartYear();

        viewModel.setStartYear(defaultStartYear);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenCurrencyTypeChanged() {
        Hypothec.CurrencyType currencyType = Hypothec.CurrencyType.EURO;
        String rightMessage = "Установлено новое значение параметра \"Тип валюты\": " + currencyType;

        viewModel.setCurrencyType(currencyType);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenCurrencyTypeIsTheSame() {
        Hypothec.CurrencyType defaultCurrencyType = viewModel.getCurrencyType();

        viewModel.setCurrencyType(defaultCurrencyType);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenPeriodTypeChanged() {
        Hypothec.PeriodType periodType = Hypothec.PeriodType.YEAR;
        String rightMessage = "Установлено новое значение параметра \"Тип периода времени\": " + periodType;

        viewModel.setPeriodType(periodType);
        List<String> log = viewModel.getLog();

        assertEquals(rightMessage, log.get(0));
    }

    @Test
    public void noMessagesWhenPeriodTypeIsTheSame() {
        Hypothec.PeriodType defaultPeriodType = viewModel.getPeriodType();

        viewModel.setPeriodType(defaultPeriodType);
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }
}
