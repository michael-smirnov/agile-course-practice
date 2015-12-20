package ru.unn.agile.HypothecsCalculator.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreditCalculatorTableTest {

    private final DefaultTableModel rightGraphic;
    private final Hypothec.CreditType creditType;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(parametersOfTests);
    }

    public CreditCalculatorTableTest(final DefaultTableModel table,
                                     final Hypothec.CreditType creditType) {
        this.rightGraphic = table;
        this.creditType = creditType;
    }

    @Test
    public void canCreateTable() {
        GraphicOfPaymentsMaker graphicOfPayments = new GraphicOfPaymentsMaker(getCredit());

        DefaultTableModel graphic = graphicOfPayments.getTableModel();

        for (int i = 0; i < rightGraphic.getColumnCount(); i++) {
             for (int j = 0; j < rightGraphic.getRowCount(); j++) {
                assertEquals(rightGraphic.getValueAt(j, i), graphic.getValueAt(j, i));
            }
            assertEquals(rightGraphic.getColumnName(i), graphic.getColumnName(i));
        }
    }

    private Hypothec getCredit() {
        Hypothec hypothec;
        try {
            hypothec = new Hypothec.Builder(4000.0, 4)
                    .interestRate(2.0)
                    .interestRateType(Hypothec.InterestRateType.MONTHLY)
                    .monthlyFee(1.0)
                    .monthlyFeeType(Hypothec.MonthlyFeeType.CREDIT_BALANCE_PERCENT)
                    .startDate(new GregorianCalendar(2015, Calendar.NOVEMBER, 4))
                    .currency(Hypothec.CurrencyType.RUBLE)
                    .creditType(creditType)
                    .build();
        } catch (Exception e) {
            hypothec = new Hypothec(new Hypothec.Builder());
        }
        return hypothec;
    }

    private static Object[][] parametersOfTests = new Object[][]{
            {
                    new DefaultTableModel(new Object[][]{
                            {1, "11.2015", 1110.0, 1000.0, 80.0, 30.0, 3000.0},
                            {2, "12.2015", 1080.0, 1000.0, 60.0, 20.0, 2000.0},
                            {3, "01.2016", 1050.0, 1000.0, 40.0, 10.0, 1000.0},
                            {4, "02.2016", 1020.0, 1000.0, 20.0, 0.0, 0.0}},
                            new String[]{
                                    "<html>№ <br>платежа",
                                    "<html>Дата <br>платежа",
                                    "<html>Сумма <br>платежа",
                                    "<html>Платеж по <br>основному долгу",
                                    "<html>Платеж по <br>процентам",
                                    "<html>Ежемесячная <br>комиссия",
                                    "<html>Остаток"
                                            + " <br>основной"
                                            + " <br>задолженности"}),
                    Hypothec.CreditType.DIFFERENTIATED
            },
            {
                    new DefaultTableModel(new Object[][]{
                            {1, "11.2015", 1080.8, 970.5, 80.0, 30.3, 3029.5},
                            {2, "12.2015", 1070.9, 989.9, 60.6, 20.4, 2039.6},
                            {3, "01.2016", 1060.8, 1009.7, 40.8, 10.3, 1029.9},
                            {4, "02.2016", 1050.5, 1029.9, 20.6, 0.0, 0.0} },
                            new String[]{
                                    "<html>№ <br>платежа",
                                    "<html>Дата <br>платежа",
                                    "<html>Сумма <br>платежа",
                                    "<html>Платеж по <br>основному долгу",
                                    "<html>Платеж по <br>процентам",
                                    "<html>Ежемесячная <br>комиссия",
                                    "<html>Остаток"
                                            + " <br>основной"
                                            + " <br>задолженности"}),
                    Hypothec.CreditType.ANNUITY
            }
    };

}
