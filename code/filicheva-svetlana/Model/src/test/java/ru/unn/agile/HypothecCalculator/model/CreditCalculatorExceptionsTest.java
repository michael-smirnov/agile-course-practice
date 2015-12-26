package ru.unn.agile.HypothecCalculator.model;

import org.junit.Test;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class CreditCalculatorExceptionsTest {
    private final double creditSum = 18000.0;
    private final int creditPeriod  = 18;

    @Test (expected = HypothecInputException.class)
    public void throwOnNegativeCreditSum() throws HypothecInputException {
        final double negativeCreditSum = -18000.0;

        new CreditCalculator(new Hypothec.Builder(negativeCreditSum, creditPeriod).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnNegativeCreditPeriod() throws HypothecInputException {
        final int negativeCreditPeriod  = -18;

        new CreditCalculator(new Hypothec.Builder(creditSum, negativeCreditPeriod).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnZeroCreditPeriod() throws HypothecInputException {
        final int zeroCreditPeriod  = 0;

        new CreditCalculator(new Hypothec.Builder(creditSum, zeroCreditPeriod).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnNegativeDownPayment() throws HypothecInputException {
        final double negativeDownPayment = -1000.0;

        new CreditCalculator(new Hypothec.Builder(creditSum, creditPeriod)
                .downPayment(negativeDownPayment).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnTooLargeDownPayment() throws HypothecInputException {
        final double tooLargeDownPayment = 20000.0;

        new CreditCalculator(new Hypothec.Builder(creditSum, creditPeriod)
                .downPayment(tooLargeDownPayment).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnNegativeInterestRate() throws HypothecInputException {
        final double negativeInterestRate = -100.0;

        new CreditCalculator(new Hypothec.Builder(creditSum, creditPeriod)
                .interestRate(negativeInterestRate).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnNegativeMonthlyFee() throws HypothecInputException {
        final double negativeMonthlyFee = -100.0;

        new CreditCalculator(new Hypothec.Builder(creditSum, creditPeriod)
                .monthlyFee(negativeMonthlyFee).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnNegativeFlatFee() throws HypothecInputException {
        final double negativeFlatFee = -100.0;

        new CreditCalculator(new Hypothec.Builder(creditSum, creditPeriod)
                .monthlyFee(negativeFlatFee).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnTooEarlyStartDate() throws HypothecInputException {
        final GregorianCalendar tooEarlyDate = new GregorianCalendar(1980, Calendar.MARCH, 10);

        new CreditCalculator(new Hypothec.Builder(creditSum, creditPeriod)
                .startDate(tooEarlyDate).build());
    }

    @Test (expected = HypothecInputException.class)
    public void throwOnTooLargeStartDate() throws HypothecInputException {
        final GregorianCalendar tooLargeDate = new GregorianCalendar(2150, Calendar.MARCH, 10);

        new CreditCalculator(new Hypothec.Builder(creditSum, creditPeriod)
                .startDate(tooLargeDate).build());
    }
}
