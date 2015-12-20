package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.HypothecsCalculator.model.Hypothec;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Collection;


import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ViewModelTableTest {

    private final DefaultTableModel rightTable;
    private final Hypothec.CreditType creditType;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(parametersOfTests);
    }

    public ViewModelTableTest(final DefaultTableModel table,
                                     final Hypothec.CreditType creditType) {
        this.rightTable = table;
        this.creditType = creditType;
    }

    @Test
    public void canCreateTable() {
        ViewModel viewModel = getViewModel();
        viewModel.setCreditType(creditType);
        viewModel.compute();
        DefaultTableModel graphic = viewModel.getGraphicOfPayments();

        for (int i = 0; i < rightTable.getColumnCount(); i++) {
            for (int j = 0; j < rightTable.getRowCount(); j++) {
                assertEquals(rightTable.getValueAt(j, i), graphic.getValueAt(j, i));
            }
            assertEquals(rightTable.getColumnName(i), graphic.getColumnName(i));
        }

    }

    private ViewModel getViewModel() {
        ViewModel viewModel = new ViewModel();
        viewModel.setHouseCost("3300");
        viewModel.setCountOfPeriods("3");
        viewModel.setDownPayment("300");
        viewModel.setInterestRate("3");
        viewModel.setFlatFee("30");
        viewModel.setMonthlyFee("0");
        return viewModel;
    }

    private static Object[][] parametersOfTests = new Object[][]{
            {
                    new DefaultTableModel(new Object[][]{
                            {1, "11.2015", 1060.59, 970.59, 90.00 , 0.0, 2029.41},
                            {2, "12.2015", 1060.59, 999.71, 60.88, 0.0, 1029.70},
                            {3, "01.2016", 1060.59, 1029.70, 30.89, 0.0, 0.0}},
                            new String[]{
                                    "<html>№ <br>платежа",
                                    "<html>Дата <br>платежа",
                                    "<html>Сумма <br>платежа",
                                    "<html>Платеж по <br>основному долгу",
                                    "<html>Платеж по <br>процентам",
                                    "<html>Ежемесячная <br>комиссия",
                                    "<html>Остаток <br>основной "
                                            + "<br>задолженности"}),
                    Hypothec.CreditType.ANNUITY
            },
            {
                    new DefaultTableModel(new Object[][]{
                            {1, "11.2015", 1090.00, 1000.0, 90.00, 0.0, 2000.0},
                            {2, "12.2015", 1060.00, 1000.0, 60.00, 0.0, 1000.0},
                            {3, "01.2016", 1030.00, 1000.0, 30.00, 0.0, 0.0}},
                            new String[]{
                                    "<html>№ <br>платежа",
                                    "<html>Дата <br>платежа",
                                    "<html>Сумма <br>платежа",
                                    "<html>Платеж по <br>основному долгу",
                                    "<html>Платеж по <br>процентам",
                                    "<html>Ежемесячная <br>комиссия",
                                    "<html>Остаток <br>основной "
                                            + "<br>задолженности"}),
                    Hypothec.CreditType.DIFFERENTIATED
            }
    };

}
