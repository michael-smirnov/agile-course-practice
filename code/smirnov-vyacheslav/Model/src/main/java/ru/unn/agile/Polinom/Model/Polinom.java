package ru.unn.agile.Polinom.Model;

import java.math.*;

public class Polinom {
    private double[] coefficients;
    private static final int PRECISION = 9;

    public Polinom() {
        coefficients = new double[1];
    }

    public Polinom(final double[] coefficients) {
        this.coefficients = coefficients;
        reduceIfHighDegreeIsNull();
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public void add(final Polinom operand) {
        double[] operandCoefficients = operand.getCoefficients();
        extendPolinom(max(coefficients.length, operandCoefficients.length));

        for (int i = 0; i < operandCoefficients.length; i++) {
            coefficients[i] += operandCoefficients[i];
            coefficients[i] = roundByPrecision(coefficients[i]);
        }
        reduceIfHighDegreeIsNull();
    }

    public void subtract(final Polinom operand) {
        double[] operandCoefficients = operand.getCoefficients();
        for (int i = 0; i < operandCoefficients.length; i++) {
            operandCoefficients[i] *= -1;
        }
        add(new Polinom(operandCoefficients));
    }

    public void multiply(final Polinom operand) {
        double[] firstCoefficients = getCoefficients();
        double[] secondCoefficients = operand.getCoefficients();
        coefficients = new double[1];
        extendPolinom(firstCoefficients.length + secondCoefficients.length);

        for (int i = 0; i < firstCoefficients.length; i++) {
            for (int j = 0; j < secondCoefficients.length; j++) {
                coefficients[i + j] += firstCoefficients[i] * secondCoefficients[j];
                coefficients[i + j] = roundByPrecision(coefficients[i + j]);
            }
        }
        reduceIfHighDegreeIsNull();
    }

    public void divide(final Polinom operand) {
        double[] dividerCoefficients = operand.getCoefficients();
        Polinom quotient = new Polinom();

        int dividendHighDegree = getHighDegree();
        int dividerHighDegree = operand.getHighDegree();

        if (dividerHighDegree == 0 && roundByPrecision(dividerCoefficients[0]) == 0.0) {
            throw new IllegalArgumentException("Divider can't be zero!");
        }

        if (dividerHighDegree > dividendHighDegree) {
            throw new IllegalArgumentException(
                "Divider's degree can't be large than dividend's!");
        }

        boolean lastTime = false;
        while (dividendHighDegree >= dividerHighDegree && !lastTime) {
            if (dividendHighDegree == 0) {
                lastTime = true;
            }

            int stepCoefficient = dividendHighDegree - dividerHighDegree;
            double[] stepQuotientCoefficients = new double[stepCoefficient + 1];
            stepQuotientCoefficients[stepCoefficient] = coefficients[dividendHighDegree]
            / dividerCoefficients[dividerHighDegree];
            Polinom stepQuotient = new Polinom(stepQuotientCoefficients);

            quotient.add(stepQuotient);
            stepQuotient.multiply(operand);
            subtract(stepQuotient);
            reduceIfHighDegreeIsNull();
            dividendHighDegree = getHighDegree();
        }

        coefficients = quotient.getCoefficients();
    }

    private double roundByPrecision(final double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(PRECISION, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private int max(final int first, final int second) {
        return first > second ? first : second;
    }

    private int getHighDegree() {
        return coefficients.length - 1;
    }

    private void extendPolinom(final int newSize) {
        if (newSize == coefficients.length) {
            return;
        }

        double[] newCoefficients = new double[newSize];

        for (int i = 0; i < coefficients.length; i++) {
            newCoefficients[i] = coefficients[i];
        }

        coefficients = newCoefficients;
    }

    private void reduceIfHighDegreeIsNull() {
        int highNotNullCoefficient = getHighDegree();
        if (roundByPrecision(coefficients[highNotNullCoefficient]) != 0.0) {
            return;
        }

        while (roundByPrecision(coefficients[highNotNullCoefficient]) == 0.0
            && highNotNullCoefficient > 0) {
            highNotNullCoefficient--;
        }

        double[] reducedCoefficient = new double[++highNotNullCoefficient];

        for (int i = 0; i < highNotNullCoefficient; i++) {
            reducedCoefficient[i] = coefficients[i];
        }
        coefficients = reducedCoefficient;
    }
}
