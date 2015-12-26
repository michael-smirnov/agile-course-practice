package ru.unn.agile.HypothecCalculator.model;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class GraphicOfPaymentsMaker {
    private final Hypothec hypothec;
    private final CreditCalculator creditCalculator;

    private static final String[] COLUMN_NAMES = {
            "<html>№ <br>платежа",
            "<html>Дата <br>платежа",
            "<html>Сумма <br>платежа",
            "<html>Платеж по <br>основному долгу",
            "<html>Платеж по <br>процентам",
            "<html>Ежемесячная <br>комиссия",
            "<html>Остаток <br>основной <br>задолженности"
    };

    public GraphicOfPaymentsMaker(final Hypothec hypothec) {
        this.hypothec = hypothec;
        creditCalculator = new CreditCalculator(hypothec);
    }

    public DefaultTableModel getTableModel() {

        Object[][] paymentsData = new Object[hypothec.getCountOfMonths()][COLUMN_NAMES.length];
        for (int i = 1; i <= hypothec.getCountOfMonths(); i++) {
            paymentsData[i - 1] = getTableRow(i);
        }

        return new DefaultTableModel(paymentsData, COLUMN_NAMES);
    }

    private Object[] getTableRow(final int rowNumber) {

        GregorianCalendar date = (GregorianCalendar) hypothec.getStartDate().clone();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.yyyy", Locale.ENGLISH);
        date.add(Calendar.MONTH, rowNumber - 1);

        double payment = creditCalculator.computeMonthlyPayment(rowNumber);
        double mainDebtPayment = creditCalculator.computeMainDebtPayment(rowNumber);
        double thisMonthFee = creditCalculator.computeMonthlyFee(rowNumber);
        double percentPayment =
                Rounder.roundMoneySum(payment - mainDebtPayment - thisMonthFee);
        double creditBalance =
                Rounder.roundMoneySum(creditCalculator.computeCreditBalance(rowNumber));

        Object[] row = new Object[] {
                rowNumber,
                dateFormat.format(date.getTime()),
                payment,
                mainDebtPayment,
                percentPayment,
                thisMonthFee,
                creditBalance
        };

        return row;
    }
}
