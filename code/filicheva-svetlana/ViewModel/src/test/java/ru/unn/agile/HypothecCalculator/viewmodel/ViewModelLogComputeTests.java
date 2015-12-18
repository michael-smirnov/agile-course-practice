package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.HypothecsCalculator.model.Hypothec;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ViewModelLogComputeTests {
    private String message;
    private FakeHypothecLogger fakeLogger;
    private ViewModel viewModel;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{ {
                "Произведены расчеты для кредита со следующими параметрами:"
        },
                {
                "Стоимость недвижимости: 1000 руб."
        }, {
                "Первоначальный взнос: 0 руб."
        }, {
                "Срок ипотеки: 18 месяцев"
        }, {
                "Процентная ставка: 1.2 % ежемесячно"
        }, {
                "Единовременные комиссии: 0 % от суммы кредита"
        }, {
                "Ежемесячные комиссии: 0 фиксированная сумма"
        }, {
                "Начало выплат: 12.2015"
        }, {
                "Тип кредита: аннуитетный"
        }
        });
    }

    @Before
    public void setUp() {
        fakeLogger = new FakeHypothecLogger();
        viewModel = new ViewModel(fakeLogger);
        loadExample();
    }

    private void loadExample() {
        viewModel.setHouseCost("1000");
        viewModel.setCountOfPeriods("18");
        viewModel.setDownPayment("0");
        viewModel.setInterestRate("1.2");
        viewModel.setFlatFee("0");
        viewModel.setMonthlyFee("0");
        viewModel.setStartMonth("12");
        viewModel.setStartYear("2015");
        viewModel.setCurrencyType(Hypothec.CurrencyType.RUBLE);
        viewModel.setPeriodType(Hypothec.PeriodType.MONTH);
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
        String lastMessage = log.get(log.size() - 1);
        assertThat(lastMessage, containsString(message));
    }

}




