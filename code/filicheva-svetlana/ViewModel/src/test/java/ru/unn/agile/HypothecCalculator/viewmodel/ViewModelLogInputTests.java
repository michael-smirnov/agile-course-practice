package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.HypothecCalculator.model.Hypothec;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

public class ViewModelLogInputTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModel);
    }

    @Test (expected = IllegalArgumentException.class)
    public void cantCreateViewModelWithoutLogger() {
        new ViewModel(null);
    }

    @Test
    public void isMessageCorrectWhenHouseCostChanged() {
        String houseCost = "100";
        String rightMessage = "Установлено новое значение "
                + "параметра \"Стоимость недвижимости\": "
                + houseCost;

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
    public void isMessageCorrectWhenDownPaymentChanged() {
        String downPayment = "20";
        String rightMessage = "Установлено новое значение "
                + "параметра \"Первоначальный взнос\": "
                + downPayment;

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
    public void isMessageCorrectWhenCountOfPeriodsChanged() {
        String countOfPeriods = "12";
        String rightMessage = "Установлено новое значение "
                + "параметра \"Срок ипотеки\": "
                + countOfPeriods;

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
    public void isMessageCorrectWhenInterestRateChanged() {
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
    public void isMessageCorrectWhenFlatFeeChanged() {
        String flatFee = "qwerty";
        String rightMessage = "Установлено новое значение "
                + "параметра \"Единовременные комиссии\": "
                + flatFee;

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
    public void isMessageCorrectWhenMonthlyFeeChanged() {
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
    public void isMessageCorrectWhenStartMonthChanged() {
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
    public void isMessageCorrectWhenStartYearChanged() {
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
    public void isMessageCorrectWhenCurrencyTypeChanged() {
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
    public void isMessageCorrectWhenPeriodTypeChanged() {
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
    public void isMessageCorrectWhenInterestRateTypeChanged() {
        Hypothec.InterestRateType interestRateType = Hypothec.InterestRateType.YEARLY;
        String rightMessage = "Установлено новое значение "
                + "параметра \"Тип процентной ставки\": "
                + interestRateType;

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
    public void isMessageCorrectWhenFlatFeeTypeChanged() {
        Hypothec.FlatFeeType flatFeeType = Hypothec.FlatFeeType.PERCENT;
        String rightMessage = "Установлено новое значение "
                + "параметра \"Тип единовременной комиссии\": "
                + flatFeeType;

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
    public void isMessageCorrectWhenMonthlyFeeTypeChanged() {
        Hypothec.MonthlyFeeType monthlyFeeType = Hypothec.MonthlyFeeType.CONSTANT_SUM;
        String rightMessage = "Установлено новое значение "
                + "параметра \"Тип ежемесячной комиссии\": "
                + monthlyFeeType;

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
    public void isMessageCorrectWhenCreditTypeChanged() {
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
    public void isMessageCorrectWhenCalculate() {
        viewModel.setHouseCost("1000");
        viewModel.setCountOfPeriods("18");
        viewModel.setInterestRate("1.2");
        viewModel.compute();

        List<String> log = viewModel.getLog();
        assertTrue(log.size() > 3);
    }
}
