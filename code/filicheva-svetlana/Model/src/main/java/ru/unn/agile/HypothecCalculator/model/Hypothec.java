package ru.unn.agile.HypothecCalculator.model;

import java.util.GregorianCalendar;
import java.util.Calendar;

public class Hypothec {

    private final double creditSum;
    private final double monthlyPercent;
    private final double monthlyFee;
    private final double flatFee;

    private final int countOfMonths;

    private final MonthlyFeeType monthlyFeeType;
    private final CreditType creditType;
    private final CurrencyType currencyType;
    private final GregorianCalendar startDate;

    public static final double MAX_NUMBER_OF_PERCENTS = 100.0;
    public static final int MONTHS_COUNT_IN_YEAR = 12;

    public Hypothec(final Builder builder) {
        this.creditSum = builder.houseCost - builder.downPayment;

        switch (builder.periodType) {
            case MONTH:
                this.countOfMonths = builder.creditPeriod;
                break;
            case YEAR:
                this.countOfMonths = builder.creditPeriod * MONTHS_COUNT_IN_YEAR;
                break;
            default:
                this.countOfMonths = 0;
                break;
        }

        switch (builder.interestRateType) {
            case MONTHLY:
                this.monthlyPercent = builder.interestRate / MAX_NUMBER_OF_PERCENTS;
                break;
            case YEARLY:
                this.monthlyPercent = builder.interestRate
                        / (MONTHS_COUNT_IN_YEAR * MAX_NUMBER_OF_PERCENTS);
                break;
            default:
                this.monthlyPercent = 0.0;
                break;
        }

        switch (builder.flatFeeType) {
            case PERCENT:
                this.flatFee = this.creditSum * builder.flatFee / MAX_NUMBER_OF_PERCENTS;
                break;
            case CONSTANT_SUM:
                this.flatFee = builder.flatFee;
                break;
            default:
                this.flatFee = 0.0;
                break;
        }

        this.creditType = builder.creditType;
        this.monthlyFee = builder.monthlyFee;
        this.monthlyFeeType = builder.monthlyFeeType;
        this.currencyType = builder.currencyType;
        this.startDate = builder.startDate;

    }

    public double getCreditSum() {
        return creditSum;
    }

    public double getMonthlyPercent() {
        return monthlyPercent;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public double getFlatFee() {
        return flatFee;
    }

    public int getCountOfMonths() {
        return countOfMonths;
    }

    public MonthlyFeeType getMonthlyFeeType() {
        return monthlyFeeType;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public static class Builder {

        private final double houseCost;
        private final int creditPeriod;

        private double downPayment = 0.0;
        private double interestRate = 0.0;
        private double monthlyFee = 0.0;
        private double flatFee = 0.0;

        private PeriodType periodType = PeriodType.MONTH;
        private InterestRateType interestRateType = InterestRateType.MONTHLY;
        private CreditType creditType = CreditType.ANNUITY;
        private MonthlyFeeType monthlyFeeType = MonthlyFeeType.CONSTANT_SUM;
        private FlatFeeType flatFeeType = FlatFeeType.CONSTANT_SUM;
        private CurrencyType currencyType = CurrencyType.RUBLE;

        private GregorianCalendar startDate
                = new GregorianCalendar(EARLIEST_VALID_YEAR, Calendar.JANUARY, 1);

        private static final int EARLIEST_VALID_YEAR = 1991;
        private static final int LATEST_VALID_YEAR = 2100;

        public Builder(final double houseCost,
                       final int creditPeriod) throws HypothecInputException {

            if (houseCost < 0) {
                throw new HypothecInputException(
                        HypothecInputException.NEGATIVE_HOUSE_COST);
            }
            if (creditPeriod <= 0) {
                throw new HypothecInputException(
                        HypothecInputException.NOT_POSITIVE_CREDIT_PERIOD);
            }

            this.houseCost = houseCost;
            this.creditPeriod = creditPeriod;
        }

        public Builder() {
            houseCost = 0.0;
            creditPeriod = 0;
        }

        public Hypothec build() {
            return new Hypothec(this);
        }

        public Builder downPayment(final double downPayment) throws HypothecInputException {
            if (downPayment < 0) {
                throw new HypothecInputException(HypothecInputException.NEGATIVE_DOWN_PAYMENT);
            }
            if (downPayment > houseCost) {
                throw new HypothecInputException(
                        HypothecInputException.DOWN_PAYMENT_IS_MORE_THAN_HOUSE_COST);
            }
            this.downPayment = downPayment;
            return this;
        }

        public Builder periodType(final PeriodType periodType) {
            this.periodType = periodType;
            return this;
        }

        public Builder interestRate(final double interestRate) throws HypothecInputException {
            if (interestRate < 0) {
                throw new HypothecInputException(HypothecInputException.NEGATIVE_INTEREST_RATE);
            }
            this.interestRate = interestRate;
            return this;
        }

        public Builder interestRateType(final InterestRateType interestRateType) {
            this.interestRateType = interestRateType;
            return this;
        }

        public Builder creditType(final CreditType creditType) {
            this.creditType = creditType;
            return this;
        }

        public Builder monthlyFee(final double monthlyFee) throws HypothecInputException {
            if (monthlyFee < 0) {
                throw new HypothecInputException(HypothecInputException.NEGATIVE_MONTHLY_FEE);
            }
            this.monthlyFee = monthlyFee;
            return this;
        }

        public Builder monthlyFeeType(final MonthlyFeeType monthlyFeeType) {
            this.monthlyFeeType = monthlyFeeType;
            return this;
        }

        public Builder flatFee(final double flatFee) throws HypothecInputException {
            if (flatFee < 0) {
                throw new HypothecInputException(HypothecInputException.NEGATIVE_FLAT_FEE);
            }
            this.flatFee = flatFee;
            return this;
        }

        public Builder flatFeeType(final FlatFeeType flatFeeType) {
            this.flatFeeType = flatFeeType;
            return this;
        }

        public Builder currency(final CurrencyType currencyType) {
            this.currencyType = currencyType;
            return this;
        }

        public Builder startDate(final GregorianCalendar startDate) throws HypothecInputException {
            if (startDate.get(Calendar.YEAR) < EARLIEST_VALID_YEAR) {
                throw new HypothecInputException(HypothecInputException.BAD_DATA);
            }
            if (startDate.get(Calendar.YEAR) > LATEST_VALID_YEAR) {
                throw new HypothecInputException(HypothecInputException.BAD_DATA);
            }
            this.startDate = startDate;
            return this;
        }
    }

    public enum CurrencyType {
        DOLLAR("$"),
        EURO("€"),
        RUBLE("руб.");

        private final String name;

        CurrencyType(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum PeriodType {
        MONTH("месяцев"),
        YEAR("лет");

        private final String name;

        PeriodType(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum InterestRateType {
        MONTHLY("% ежемесячно"),
        YEARLY("% ежегодно");

        private final String name;

        InterestRateType(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum FlatFeeType {
        PERCENT("% от суммы кредита"),
        CONSTANT_SUM("фиксированная сумма");

        private final String name;

        FlatFeeType(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum MonthlyFeeType {
        CREDIT_SUM_PERCENT("% от суммы кредита"),
        CREDIT_BALANCE_PERCENT("% от остатка долга"),
        CONSTANT_SUM("фиксированная сумма");

        private final String name;

        MonthlyFeeType(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum CreditType {
        DIFFERENTIATED("дифференцированный"),
        ANNUITY("аннуитетный");

        private final String name;

        CreditType(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

