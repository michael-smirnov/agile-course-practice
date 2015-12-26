package ru.unn.agile.HypothecCalculator.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreditCalculatorTest {

    private final CreditCalculator creditCalculator;
    private final RightValues rightValues;
    private final double delta = 0.01;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(parametersOfTests);
    }

    @Test
    public void canGetLowestPayment() {
        final double monthlyPayment = creditCalculator.computeLowestMonthlyPayment();

        assertEquals(rightValues.getLowestPayment(), monthlyPayment, delta);
    }

    @Test
    public void canGetHighestPayment() {
        final double monthlyPayment = creditCalculator.computeHighestMonthlyPayment();

        assertEquals(rightValues.getHighestPayment(), monthlyPayment, delta);
    }

    @Test
    public void canGetOverpayment() {
        final double overpayment = creditCalculator.computeOverpayment();

        assertEquals(rightValues.getOverpayment(), overpayment, delta);
    }

    @Test
    public void canGetOverpaymentWithFees() {
        final double overpaymentWithFees = creditCalculator.computeOverpaymentWithFees();

        assertEquals(rightValues.getOverpaymentWithFees(), overpaymentWithFees, delta);
    }

    public CreditCalculatorTest(final CreditCalculator creditCalculator,
                                final RightValues rightValues) {

        this.creditCalculator = creditCalculator;
        this.rightValues = rightValues;
    }

    private static Object[][] parametersOfTests;

    static {
        try {
            parametersOfTests = new Object[][]{
                    {
                            new CreditCalculator(new Hypothec.Builder(18000.0, 18).build()),
                            new RightValues(1000.0, 1000.0, 0.0, 0.0)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(15000.0, 10)
                                    .downPayment(5000.0).build()),
                            new RightValues(1000.0, 1000.0, 0.0, 0.0)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 1)
                                    .periodType(Hypothec.PeriodType.YEAR).build()),
                            new RightValues(1000.0, 1000.0, 0.0, 0.0)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(0.83333).build()),
                            new RightValues(1054.99, 1054.99, 659.89, 659.89)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(10)
                                    .interestRateType(Hypothec.InterestRateType.YEARLY).build()),
                            new RightValues(1054.99, 1054.99, 659.89, 659.89)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(0.83333)
                                    .creditType(Hypothec.CreditType.DIFFERENTIATED).build()),
                            new RightValues(1100.0, 1008.33, 650.0, 650.0)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(0.83333).
                                            monthlyFee(1000.0).build()),
                            new RightValues(2054.99, 2054.99, 659.89, 12659.88)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(0.83333).monthlyFee(1.0)
                                    .monthlyFeeType(Hypothec.MonthlyFeeType.CREDIT_SUM_PERCENT)
                                    .build()),
                            new RightValues(1175.0, 1175.0, 659.89, 2099.88)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(0.83333).monthlyFee(1.0)
                                    .monthlyFeeType(Hypothec.MonthlyFeeType.CREDIT_BALANCE_PERCENT)
                                    .build()),
                            new RightValues(1165.44, 1055.0, 659.89, 1331.75)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(0.83333).monthlyFee(1.0)
                                    .monthlyFeeType(Hypothec.MonthlyFeeType.CREDIT_BALANCE_PERCENT)
                                    .creditType(Hypothec.CreditType.DIFFERENTIATED).build()),
                            new RightValues(1210.0, 1008.33, 650.0, 1310.0)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(0.83333).
                                            monthlyFee(1000.0).flatFee(12000.0).build()),
                            new RightValues(2054.99, 2054.99, 659.89, 24659.88)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(12000.0, 12)
                                    .interestRate(0.83333).
                                            monthlyFee(1000.0).flatFee(100.0)
                                    .flatFeeType(Hypothec.FlatFeeType.PERCENT)
                                    .build()),
                            new RightValues(2054.99, 2054.99, 659.89, 24659.88)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(18000.0, 18)
                                    .currency(Hypothec.CurrencyType.DOLLAR).build()),
                            new RightValues(1000.0, 1000.0, 0.0, 0.0)
                    },
                    {
                            new CreditCalculator(new Hypothec.Builder(18000.0, 18)
                                    .startDate(new GregorianCalendar(1992, Calendar.MARCH, 10))
                                    .build()),
                            new RightValues(1000.0, 1000.0, 0.0, 0.0)
                    }
            };
        } catch (HypothecInputException e) {
            parametersOfTests = new Object[][]{
                    {
                            new CreditCalculator(new Hypothec.Builder().build()),
                            new RightValues(0.0, 0.0, 0.0, 0.0)
                    }
            };
        }
    }


    public static class RightValues {
        private final double lowestPayment;
        private final double highestPayment;
        private final double overpayment;
        private final double overpaymentWithFees;

        RightValues(final double rightHighestPayment,
                    final double rightLowestPayment,
                    final double rightOverpayment,
                    final double rightOverpaymentWithFees) {

            this.highestPayment = rightHighestPayment;
            this.lowestPayment = rightLowestPayment;
            this.overpayment = rightOverpayment;
            this.overpaymentWithFees = rightOverpaymentWithFees;
        }

        public double getLowestPayment() {
            return lowestPayment;
        }

        public double getHighestPayment() {
            return highestPayment;
        }

        public double getOverpayment() {
            return overpayment;
        }

        public double getOverpaymentWithFees() {
            return overpaymentWithFees;
        }
    }
}
