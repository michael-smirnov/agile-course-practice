package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.HypothecCalculator.model.Hypothec;

import static org.junit.Assert.assertEquals;

public class ViewModelComputingTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
        loadExample();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    private void loadExample() {
        viewModel.setHouseCost("1200");
        viewModel.setCountOfPeriods("12");
        viewModel.setDownPayment("200");
        viewModel.setInterestRate("1");
        viewModel.setFlatFee("0");
        viewModel.setMonthlyFee("2");
        viewModel.setStartMonth("1");
        viewModel.setStartYear("1992");
        viewModel.setCurrencyType(Hypothec.CurrencyType.DOLLAR);
        viewModel.setPeriodType(Hypothec.PeriodType.MONTH);
        viewModel.setInterestRateType(Hypothec.InterestRateType.MONTHLY);
        viewModel.setMonthlyFeeType(Hypothec.MonthlyFeeType.CREDIT_SUM_PERCENT);
        viewModel.setFlatFeeType(Hypothec.FlatFeeType.CONSTANT_SUM);
        viewModel.setCreditType(Hypothec.CreditType.ANNUITY);
    }

    @Test
    public void canComputeMonthlyPaymentForAnnuityCredit() {
        viewModel.compute();
        assertEquals("108.85", viewModel.getMonthlyPayment());
    }

    @Test
    public void canComputeMonthlyPaymentForZeroDownPayment() {
        viewModel.setHouseCost("1000");
        viewModel.setDownPayment("0");
        viewModel.compute();
        assertEquals("108.85", viewModel.getMonthlyPayment());
    }

    @Test
    public void canComputeMonthlyPaymentForYearPeriodType() {
        viewModel.setCountOfPeriods("1");
        viewModel.setPeriodType(Hypothec.PeriodType.YEAR);
        viewModel.compute();
        assertEquals("108.85", viewModel.getMonthlyPayment());
    }

    @Test
    public void canComputeMonthlyPaymentForYearlyInterestRateType() {
        viewModel.setInterestRate("12");
        viewModel.setInterestRateType(Hypothec.InterestRateType.YEARLY);
        viewModel.compute();
        assertEquals("108.85", viewModel.getMonthlyPayment());
    }

    @Test
    public void canComputeMonthlyPaymentForMonthlyFeeTypeCreditBalancePercent() {
        viewModel.setMonthlyFeeType(Hypothec.MonthlyFeeType.CREDIT_BALANCE_PERCENT);
        viewModel.setMonthlyFee("1");
        viewModel.compute();
        assertEquals("98.06 ... 88.85", viewModel.getMonthlyPayment());
    }

    @Test
    public void canComputeMonthlyPaymentForMonthlyFeeTypeConstSum() {
        viewModel.setMonthlyFeeType(Hypothec.MonthlyFeeType.CONSTANT_SUM);
        viewModel.setMonthlyFee("10");
        viewModel.compute();
        assertEquals("98.85", viewModel.getMonthlyPayment());
    }

    @Test
    public void canComputeMonthlyPaymentForDifferentiatedCredit() {
        viewModel.setCreditType(Hypothec.CreditType.DIFFERENTIATED);
        viewModel.compute();
        assertEquals("113.33 ... 104.17", viewModel.getMonthlyPayment());
    }

    @Test
    public void canComputeOverpaymentForAnnuityCredit() {
        viewModel.compute();
        assertEquals("66.19", viewModel.getOverpayment());
    }

    @Test
    public void canComputeOverpaymentForDifferentiatedCredit() {
        viewModel.setCreditType(Hypothec.CreditType.DIFFERENTIATED);
        viewModel.compute();
        assertEquals("65.0", viewModel.getOverpayment());
    }

    @Test
    public void canComputeOverpaymentWithFeesForAnnuityCredit() {
        viewModel.compute();
        assertEquals("306.2", viewModel.getOverpaymentWithFees());
    }

    @Test
    public void canComputeOverpaymentWithFeesForDifferentiatedCredit() {
        viewModel.setCreditType(Hypothec.CreditType.DIFFERENTIATED);
        viewModel.compute();
        assertEquals("305.0", viewModel.getOverpaymentWithFees());
    }

    @Test
    public void canComputeOverpaymentWithFeesForMonthlyFeeTypeCreditBalancePercent() {
        viewModel.setMonthlyFeeType(Hypothec.MonthlyFeeType.CREDIT_BALANCE_PERCENT);
        viewModel.setMonthlyFee("1");
        viewModel.compute();
        assertEquals("122.39", viewModel.getOverpaymentWithFees());
    }

    @Test
    public void canComputeOverpaymentWithFeesForMonthlyFeeTypeConstSum() {
        viewModel.setMonthlyFeeType(Hypothec.MonthlyFeeType.CONSTANT_SUM);
        viewModel.setMonthlyFee("10");
        viewModel.compute();
        assertEquals("186.2", viewModel.getOverpaymentWithFees());
    }

    @Test
    public void canComputeOverpaymentWithFeesForFlatFeeConstSum() {
        viewModel.setFlatFee("100");
        viewModel.compute();
        assertEquals("406.2", viewModel.getOverpaymentWithFees());
    }

    @Test
    public void canComputeOverpaymentWithFeesForFlatFeeTypeCreditPercent() {
        viewModel.setFlatFeeType(Hypothec.FlatFeeType.PERCENT);
        viewModel.setFlatFee("12");
        viewModel.compute();
        assertEquals("426.2", viewModel.getOverpaymentWithFees());
    }
}
