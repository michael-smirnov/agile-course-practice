package ru.unn.agile.HypothecCalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.HypothecCalculator.model.Hypothec;
import ru.unn.agile.HypothecCalculator.model.HypothecInputException;

import static org.junit.Assert.assertEquals;

public class ViewModelTextFieldsTest {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    private void loadExample() {
        viewModel.setHouseCost("2500000");
        viewModel.setCountOfPeriods("15");
        viewModel.setDownPayment("0");
        viewModel.setInterestRate("10.0");
        viewModel.setFlatFee("0");
        viewModel.setMonthlyFee("2");
        viewModel.setStartMonth("1");
        viewModel.setStartYear("1992");
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
        assertEquals(false, viewModel.isButtonEnabled());
        assertEquals("", viewModel.getHouseCost());
        assertEquals("0", viewModel.getDownPayment());
        assertEquals("", viewModel.getCountOfPeriods());
        assertEquals("", viewModel.getInterestRate());
        assertEquals("0", viewModel.getFlatFee());
        assertEquals("0", viewModel.getMonthlyFee());
        assertEquals(Hypothec.CurrencyType.DOLLAR, viewModel.getCurrencyType());
        assertEquals(Hypothec.PeriodType.MONTH, viewModel.getPeriodType());
        assertEquals(Hypothec.InterestRateType.MONTHLY, viewModel.getInterestRateType());
        assertEquals(Hypothec.FlatFeeType.CONSTANT_SUM, viewModel.getFlatFeeType());
        assertEquals(Hypothec.MonthlyFeeType.CREDIT_SUM_PERCENT, viewModel.getMonthlyFeeType());
        assertEquals(Hypothec.CreditType.DIFFERENTIATED, viewModel.getCreditType());
        assertEquals("11", viewModel.getStartMonth());
        assertEquals("2015", viewModel.getStartYear());
    }

    @Test
    public void canLoadExample() {
        loadExample();
        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
        assertEquals(true, viewModel.isButtonEnabled());
    }

    @Test
    public void isStatusWaitingWhenHouseCostFieldIsEmpty() {
        loadExample();
        viewModel.setHouseCost("");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenHouseCostHasBadFormat() {
        loadExample();
        viewModel.setHouseCost("qw");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenHouseCostHasWrongValue() {
        loadExample();
        viewModel.setHouseCost("-100");

        viewModel.checkInput();

        assertEquals(HypothecInputException.NEGATIVE_HOUSE_COST, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenHouseCostIsRight() {
        loadExample();
        viewModel.setHouseCost("100");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isButtonDisabledWhenHouseCostFieldIsEmpty() {
        loadExample();
        viewModel.setHouseCost("");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenHouseCostHasBadFormat() {
        loadExample();
        viewModel.setHouseCost("qw");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenHouseCostHasWrongValue() {
        loadExample();
        viewModel.setHouseCost("-100");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isStatusWaitingWhenCountOfPeriodsFieldIsEmpty() {
        loadExample();
        viewModel.setCountOfPeriods("");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenCountOfPeriodsHasBadFormat() {
        loadExample();
        viewModel.setCountOfPeriods("pgffff");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenCountOfPeriodsHasWrongValue() {
        loadExample();
        viewModel.setCountOfPeriods("0");

        viewModel.checkInput();

        assertEquals(HypothecInputException.NOT_POSITIVE_CREDIT_PERIOD, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenCountOfPeriodsIsRight() {
        loadExample();
        viewModel.setCountOfPeriods("1");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isButtonDisabledWhenCountOfPeriodsFieldIsEmpty() {
        loadExample();
        viewModel.setCountOfPeriods("");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenCountOfPeriodsHasBadFormat() {
        loadExample();
        viewModel.setCountOfPeriods("fdddddw");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledCountOfPeriodsHasWrongValue() {
        loadExample();
        viewModel.setCountOfPeriods("0");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isStatusWaitingWhenDownPaymentFieldIsEmpty() {
        loadExample();
        viewModel.setDownPayment("");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenDownPaymentHasBadFormat() {
        loadExample();
        viewModel.setDownPayment("eee");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenDownPaymentHasWrongValue() {
        loadExample();
        viewModel.setDownPayment("-400");

        viewModel.checkInput();

        assertEquals(HypothecInputException.NEGATIVE_DOWN_PAYMENT, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenDownPaymentIsRight() {
        loadExample();
        viewModel.setDownPayment("10");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenDownPaymentIsMoreThanHouseCost() {
        loadExample();
        viewModel.setHouseCost("100");
        viewModel.setDownPayment("120");

        viewModel.checkInput();

        assertEquals(
                HypothecInputException.DOWN_PAYMENT_IS_MORE_THAN_HOUSE_COST,
                viewModel.getStatus());
    }

    @Test
    public void isButtonDisabledWhenDownPaymentFieldIsEmpty() {
        loadExample();
        viewModel.setDownPayment("");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenDownPaymentHasBadFormat() {
        loadExample();
        viewModel.setDownPayment("eeee");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenDownPaymentHasWrongValue() {
        loadExample();
        viewModel.setDownPayment("-100");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenDownPaymentIsMoreThanHouseCost() {
        loadExample();
        viewModel.setHouseCost("100");
        viewModel.setDownPayment("120");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isStatusWaitingWhenInterestRateFieldIsEmpty() {
        loadExample();
        viewModel.setInterestRate("");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenInterestRateHasBadFormat() {
        loadExample();
        viewModel.setInterestRate("qqffw");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenInterestRateHasWrongValue() {
        loadExample();
        viewModel.setInterestRate("-10");

        viewModel.checkInput();

        assertEquals(HypothecInputException.NEGATIVE_INTEREST_RATE, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenInterestRateIsRight() {
        loadExample();
        viewModel.setInterestRate("150");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isButtonDisabledWhenInterestRateFieldIsEmpty() {
        loadExample();
        viewModel.setInterestRate("");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenInterestRateHasBadFormat() {
        loadExample();
        viewModel.setInterestRate("kgh");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenInterestRateHasWrongValue() {
        loadExample();
        viewModel.setInterestRate("-8");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isStatusWaitingWhenFlatFeeFieldIsEmpty() {
        loadExample();
        viewModel.setFlatFee("");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenFlatFeeHasBadFormat() {
        loadExample();
        viewModel.setFlatFee("wwwzzzz");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenFlatFeeHasWrongValue() {
        loadExample();
        viewModel.setFlatFee("-1.0");

        viewModel.checkInput();

        assertEquals(HypothecInputException.NEGATIVE_FLAT_FEE, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFlatFeeIsRight() {
        loadExample();
        viewModel.setFlatFee("150.0");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isButtonDisabledWhenFlatFeeFieldIsEmpty() {
        loadExample();
        viewModel.setFlatFee("");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenFlatFeeHasBadFormat() {
        loadExample();
        viewModel.setFlatFee("yyy");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenFlatFeeHasWrongValue() {
        loadExample();
        viewModel.setFlatFee("-34.9");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isStatusWaitingWhenMonthlyFeeFieldIsEmpty() {
        loadExample();
        viewModel.setMonthlyFee("");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenMonthlyFeeHasBadFormat() {
        loadExample();
        viewModel.setMonthlyFee("tutuh");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenMonthlyFeeHasWrongValue() {
        loadExample();
        viewModel.setMonthlyFee("-1.20");

        viewModel.checkInput();

        assertEquals(HypothecInputException.NEGATIVE_MONTHLY_FEE, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenMonthlyFeeIsRight() {
        loadExample();
        viewModel.setMonthlyFee("1330.0");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isButtonDisabledWhenMonthlyFeeFieldIsEmpty() {
        loadExample();
        viewModel.setMonthlyFee("");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenMonthlyFeeHasBadFormat() {
        loadExample();
        viewModel.setMonthlyFee("veryverybad");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenMonthlyFeeHasWrongValue() {
        loadExample();
        viewModel.setMonthlyFee("-4.9");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isStatusWaitingWhenStartMonthFieldIsEmpty() {
        loadExample();
        viewModel.setStartMonth("");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenStartMonthHasBadFormat() {
        loadExample();
        viewModel.setStartMonth("12.9");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenStartMonthHasWrongValue() {
        loadExample();
        viewModel.setStartMonth("13");

        viewModel.checkInput();

        assertEquals(HypothecInputException.BAD_MONTH, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenStartMonthIsRight() {
        loadExample();
        viewModel.setStartMonth("3");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isButtonDisabledWhenStartMonthFieldIsEmpty() {
        loadExample();
        viewModel.setStartMonth("");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenStartMonthHasBadFormat() {
        loadExample();
        viewModel.setStartMonth("ufff");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenStartMonthHasWrongValue() {
        loadExample();
        viewModel.setStartMonth("-4");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isStatusWaitingWhenStartYearFieldIsEmpty() {
        loadExample();
        viewModel.setStartYear("");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenStartYearHasBadFormat() {
        loadExample();
        viewModel.setStartYear("hg17.3");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusErrorWhenStartYearHasWrongValue() {
        loadExample();
        viewModel.setStartYear("1313");

        viewModel.checkInput();

        assertEquals(HypothecInputException.BAD_DATA, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenStartYearIsRight() {
        loadExample();
        viewModel.setStartYear("1993");

        viewModel.checkInput();

        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isButtonDisabledWhenStartYearFieldIsEmpty() {
        loadExample();
        viewModel.setStartYear("");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenStartYearHasBadFormat() {
        loadExample();
        viewModel.setStartYear("15.2");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }

    @Test
    public void isButtonDisabledWhenStartYearHasWrongValue() {
        loadExample();
        viewModel.setStartYear("1990");

        viewModel.checkInput();

        assertEquals(false, viewModel.isButtonEnabled());
    }
}
