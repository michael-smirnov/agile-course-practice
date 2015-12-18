package ru.unn.agile.HypothecCalculator.view;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.HypothecCalculator.viewmodel.ViewModel;
import ru.unn.agile.HypothecCalculator.viewmodel.IHypothecLogger;
import ru.unn.agile.HypothecCalculator.viewmodel.ViewModelLogComputeTests;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ViewModelTxtLogComputeTests extends ViewModelLogComputeTests {
    @Override
    public void setUp() {
        IHypothecLogger realLogger = new TxtHypothecLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }

    public ViewModelTxtLogComputeTests(String s) {
        super(s);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{ {
                "Произведены расчеты для кредита со следующими параметрами:"
        }, {
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
}
