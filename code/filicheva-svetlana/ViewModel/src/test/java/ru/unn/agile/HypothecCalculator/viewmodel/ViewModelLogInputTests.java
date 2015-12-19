package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.HypothecsCalculator.model.Hypothec;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

public class ViewModelLogInputTests {
    private ViewModel viewModel;
    private FakeHypothecLogger fakeLogger;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        fakeLogger = new FakeHypothecLogger();
        viewModel = new ViewModel(fakeLogger);
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Стоимость недвижимости\": " + houseCost;

        viewModel.setHouseCost(houseCost);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Первоначальный взнос\": " + downPayment;

        viewModel.setDownPayment(downPayment);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Срок ипотеки\": " + countOfPeriods;

        viewModel.setCountOfPeriods(countOfPeriods);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Процентная ставка\": " + interestRate;

        viewModel.setInterestRate(interestRate);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Единовременные комиссии\": " + flatFee;

        viewModel.setFlatFee(flatFee);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Ежемесячные комиссии\": " + monthlyFee;

        viewModel.setMonthlyFee(monthlyFee);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Месяц начала выплат\": " + startMonth;

        viewModel.setStartMonth(startMonth);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Год начала выплат\": " + startYear;

        viewModel.setStartYear(startYear);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Тип валюты\": " + currencyType;

        viewModel.setCurrencyType(currencyType);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
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
        String rightMessage = "Установлено новое значение "
                + "параметра \"Тип периода времени\": " + periodType;

        viewModel.setPeriodType(periodType);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
    }

    @Test
    public void noMessagesWhenPeriodTypeIsTheSame() {
        Hypothec.PeriodType defaultPeriodType = viewModel.getPeriodType();

        viewModel.setPeriodType(defaultPeriodType);

        List<String> log = viewModel.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenInterestRateTypeChanged() {
        Hypothec.InterestRateType interestRateType = Hypothec.InterestRateType.YEARLY;
        String rightMessage = "Установлено новое значение "
                + "параметра \"Тип процентной ставки\": " + interestRateType;

        viewModel.setInterestRateType(interestRateType);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
    }

    @Test
    public void noMessagesWhenInterestRateTypeIsTheSame() {
        Hypothec.InterestRateType defaultInterestRateType = viewModel.getInterestRateType();

        viewModel.setInterestRateType(defaultInterestRateType);

        List<String> log = viewModel.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenFlatFeeTypeChanged() {
        Hypothec.FlatFeeType flatFeeType = Hypothec.FlatFeeType.PERCENT;
        String rightMessage = "Установлено новое значение "
                + "параметра \"Тип единовременной комиссии\": " + flatFeeType;

        viewModel.setFlatFeeType(flatFeeType);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
    }

    @Test
    public void noMessagesWhenFlatFeeTypeIsTheSame() {
        Hypothec.FlatFeeType defaultFlatFeeType = viewModel.getFlatFeeType();

        viewModel.setFlatFeeType(defaultFlatFeeType);

        List<String> log = viewModel.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenMonthlyFeeTypeChanged() {
        Hypothec.MonthlyFeeType monthlyFeeType = Hypothec.MonthlyFeeType.CONSTANT_SUM;
        String rightMessage = "Установлено новое значение "
                + "параметра \"Тип ежемесячной комиссии\": " + monthlyFeeType;

        viewModel.setMonthlyFeeType(monthlyFeeType);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
    }

    @Test
    public void noMessagesWhenMonthlyFeeTypeIsTheSame() {
        Hypothec.MonthlyFeeType defaultMonthlyFeeType = viewModel.getMonthlyFeeType();

        viewModel.setMonthlyFeeType(defaultMonthlyFeeType);

        List<String> log = viewModel.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenCreditTypeChanged() {
        Hypothec.CreditType creditType = Hypothec.CreditType.ANNUITY;
        String rightMessage = "Установлено новое значение"
                + " параметра \"Тип кредита\": " + creditType;

        viewModel.setCreditType(creditType);

        String message = viewModel.getLog().get(0);
        assertThat(message, containsString(rightMessage));
    }

    @Test
    public void noMessagesWhenCreditTypeIsTheSame() {
        Hypothec.CreditType defaultCreditType = viewModel.getCreditType();

        viewModel.setCreditType(defaultCreditType);

        List<String> log = viewModel.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void isMessageAddedWhenCalculate() {
        viewModel.setHouseCost("1000");
        viewModel.setCountOfPeriods("18");
        viewModel.setInterestRate("1.2");
        viewModel.compute();

        List<String> log = viewModel.getLog();
        assertTrue(log.size() > 3);
    }

}
