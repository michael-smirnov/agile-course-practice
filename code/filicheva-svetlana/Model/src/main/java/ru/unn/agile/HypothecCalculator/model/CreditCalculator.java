package ru.unn.agile.HypothecCalculator.model;

public final class CreditCalculator {

    private final Hypothec hypothec;

    public CreditCalculator(final Hypothec hypothec) {
        this.hypothec = hypothec;
    }

    public double computeHighestMonthlyPayment() {

        return computeMonthlyPayment(1);
    }

    public double computeLowestMonthlyPayment() {

        return computeMonthlyPayment(hypothec.getCountOfMonths());
    }

    public double computeOverpayment() {
        double creditCoefficient = 0.0;

        for (int i = 1; i <= hypothec.getCountOfMonths(); i++) {
            creditCoefficient += computePaymentCoefficient(i);
        }

        double overpayment = (creditCoefficient - 1.0) * hypothec.getCreditSum();

        return Rounder.roundMoneySum(overpayment);
    }

    public double computeOverpaymentWithFees() {
        double allPayments = 0.0;

        for (int i = 1; i <= hypothec.getCountOfMonths(); i++) {
            allPayments += computeMonthlyPayment(i);
        }

        double overpaymentWithFees = allPayments - hypothec.getCreditSum() + hypothec.getFlatFee();

        return Rounder.roundMoneySum(overpaymentWithFees);
    }

    public double computeMonthlyPayment(final int numberOfMonth) {

        double monthlyPayment = hypothec.getCreditSum() * computePaymentCoefficient(numberOfMonth)
                + computeMonthlyFee(numberOfMonth);

        return Rounder.roundMoneySum(monthlyPayment);
    }

    public double computeMonthlyFee(final int numberOfMonth) {

        double fee = 0.0;

        switch (hypothec.getMonthlyFeeType()) {
            case CONSTANT_SUM:
                fee = hypothec.getMonthlyFee();
                break;
            case CREDIT_BALANCE_PERCENT:
                fee = computeCreditBalance(numberOfMonth) * hypothec.getMonthlyFee()
                        / hypothec.MAX_NUMBER_OF_PERCENTS;
                break;
            case CREDIT_SUM_PERCENT:
                fee = hypothec.getCreditSum() * hypothec.getMonthlyFee()
                        / hypothec.MAX_NUMBER_OF_PERCENTS;
                break;
            default:
                throw new IllegalArgumentException("Illegal monthly fee type");
        }

        return Rounder.roundMoneySum(fee);
    }

    public double computeCreditBalance(final int numberOfMonth) {
        double balance = 0.0;

        switch (hypothec.getCreditType()) {
            case DIFFERENTIATED:
                balance = hypothec.getCreditSum() * (1.0 - (double) numberOfMonth
                        / hypothec.getCountOfMonths());
                break;
            case ANNUITY:
                balance = annuityCreditBalance(numberOfMonth);
                break;
            default:
                throw new IllegalArgumentException("Illegal credit type");
        }

        return balance;
    }

    public double computeMainDebtPayment(final int numberOfMonth) {
        double sum = 0.0;

        switch (hypothec.getCreditType()) {
            case DIFFERENTIATED:
                sum = hypothec.getCreditSum() / hypothec.getCountOfMonths();
                break;
            case ANNUITY:
                sum = hypothec.getCreditSum() * computeAnnuityCoefficient()
                        - annuityCreditBalance(numberOfMonth - 1) * hypothec.getMonthlyPercent();
                break;
            default:
                throw new IllegalArgumentException("Illegal credit type");
        }

        return Rounder.roundMoneySum(sum);
    }

    private double computePaymentCoefficient(final int numberOfMonth) {
        double paymentCoefficient = 0.0;

        switch (hypothec.getCreditType()) {
            case ANNUITY:
                paymentCoefficient = computeAnnuityCoefficient();
                break;
            case DIFFERENTIATED:
                paymentCoefficient = computeDifferentiatedCoefficient(numberOfMonth);
                break;
            default:
                throw new IllegalArgumentException("Illegal credit type");
        }

        return paymentCoefficient;
    }

    private double computeAnnuityCoefficient() {
        double monthlyPercent = hypothec.getMonthlyPercent();

        if (monthlyPercent == 0.0) {
            return 1.0 / hypothec.getCountOfMonths();
        }

        return monthlyPercent
                * (1.0 + 1.0 / (Math.pow(1 + monthlyPercent, hypothec.getCountOfMonths()) - 1));
    }

    private double computeDifferentiatedCoefficient(final int numberOfMonth) {
        int monthsCount = hypothec.getCountOfMonths();

        return 1.0 / monthsCount
                + hypothec.getMonthlyPercent() * (1.0 - (numberOfMonth - 1.0) / monthsCount);
    }

    private double annuityCreditBalance(final int numberOfMonth) {
        double balance = hypothec.getCreditSum();
        double monthlyPayment = hypothec.getCreditSum() * computeAnnuityCoefficient();

        for (int i = 1; i <= numberOfMonth; i++) {
            balance -= monthlyPayment - balance * hypothec.getMonthlyPercent();
        }

        return balance;
    }
}
