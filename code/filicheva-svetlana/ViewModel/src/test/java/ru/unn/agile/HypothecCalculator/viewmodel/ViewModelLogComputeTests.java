package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.HypothecCalculator.model.Hypothec;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ViewModelLogComputeTests {
    private String message;
    private ViewModel viewModel;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(parametersOfTests);
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
        loadExample();
    }

    public void loadExample() {
        viewModel.setHouseCost("1000");
        viewModel.setCountOfPeriods("5");
        viewModel.setDownPayment("100");
        viewModel.setInterestRate("1.2");
        viewModel.setFlatFee("10");
        viewModel.setMonthlyFee("10");
        viewModel.setStartMonth("12");
        viewModel.setStartYear("2012");
        viewModel.setCurrencyType(Hypothec.CurrencyType.RUBLE);
        viewModel.setPeriodType(Hypothec.PeriodType.YEAR);
        viewModel.setInterestRateType(Hypothec.InterestRateType.MONTHLY);
        viewModel.setMonthlyFeeType(Hypothec.MonthlyFeeType.CONSTANT_SUM);
        viewModel.setFlatFeeType(Hypothec.FlatFeeType.PERCENT);
        viewModel.setCreditType(Hypothec.CreditType.ANNUITY);
    }

    public ViewModelLogComputeTests(final String message) {
        this.message = message;
    }

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Test
    public void isComputeMessageContainsParameter() {
        viewModel.compute();

        List<String> log = viewModel.getLog();
        String lastMessage = "";
        int computeMessageSize = 9;
        for (int i = 1; i <= computeMessageSize; i++) {
            lastMessage = log.get(log.size() - i) + " " + lastMessage;
        }
        assertThat(lastMessage, containsString(message));
    }

    private static Object[][] parametersOfTests = new Object[][]{
            {
                    "Произведены расчеты для кредита "
                            + "со следующими параметрами:"
            }, {
                    "Стоимость недвижимости: 1000 руб."
            }, {
                    "Первоначальный взнос: 100 руб."
            }, {
                    "Срок ипотеки: 5 лет"
            }, {
                    "Процентная ставка: 1.2 % ежемесячно"
            }, {
                    "Единовременные комиссии: 10 "
                            + "% от суммы кредита"
            }, {
                    "Ежемесячные комиссии: 10 "
                            + "фиксированная сумма"
            }, {
                    "Начало выплат: 12.2012"
            }, {
                    "Тип кредита: аннуитетный"
            }
    };
}
