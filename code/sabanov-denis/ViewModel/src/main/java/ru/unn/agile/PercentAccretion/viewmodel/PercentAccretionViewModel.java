package ru.unn.agile.PercentAccretion.viewmodel;

import ru.unn.agile.PercentAccretion.Model.PercentAccretionFactory;
import ru.unn.agile.PercentAccretion.Model.PercentAccretion;
import ru.unn.agile.PercentAccretion.Model.PercentData;

public class PercentAccretionViewModel {
    public enum PercentAccretionErrors {
        FIELD_IS_EMPTY("Please fill fields!"),
        INCORRECT_VALUES("Please enter correct values!"),
        SUCCESS("");

        private String errorMessage;

        PercentAccretionErrors(final String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getMessage() {
            return errorMessage;
        }
    }
    private boolean calculateButtonEnabled;
    private boolean initialSumIsCorrect;
    private boolean percentRateIsCorrect;
    private boolean countOfYearsIsCorrect;
    private PercentAccretionFactory.AccretionType operation;
    private final PercentData data;
    private String errorMessage;
    private String resultSum;
    private static final String EMPTY_STRING = "";

    public String getResultSum() {
        return resultSum;
    }

    public boolean isCalculateButtonEnabled() {
        if (checkFieldsHaveRightValues()) {
            calculateButtonEnabled = true;
            errorMessage = PercentAccretionErrors.SUCCESS.getMessage();
        } else {
            calculateButtonEnabled = false;
        }
        return calculateButtonEnabled;
    }

    public PercentData getData() {
        return data;
    }

    public void setPercentType(final String value) {
        if (PercentAccretionFactory.AccretionType.
                SIMPLE_PERCENT_SUM.toString().equals(value)) {
            operation = PercentAccretionFactory.AccretionType.SIMPLE_PERCENT_SUM;
        }
        if (PercentAccretionFactory.AccretionType.
                COMPLEX_PERCENT_SUM.toString().equals(value)) {
            operation = PercentAccretionFactory.AccretionType.COMPLEX_PERCENT_SUM;
        }
        resultSum = EMPTY_STRING;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setInitialSum(final String value) {
        resultSum = EMPTY_STRING;
        if (checkValue(value)) {
            initialSumIsCorrect = false;
            return;
        }
        data.setInitialSum(Double.valueOf(value));
        initialSumIsCorrect = true;
        clearErrorMessage();
    }

    public void setPercentRate(final String value) {
        resultSum = EMPTY_STRING;
        if (checkValue(value)) {
            percentRateIsCorrect = false;
            return;
        }
        data.setPercentRate(Double.valueOf(value));
        percentRateIsCorrect = true;
        clearErrorMessage();
    }

    public void setCountOfYears(final String value) {
        resultSum = EMPTY_STRING;
        if (checkValue(value)) {
            countOfYearsIsCorrect = false;
            return;
        }
        data.setCountOfYears(Integer.valueOf(value));
        countOfYearsIsCorrect = true;
        clearErrorMessage();
    }

    public void calculateResultSum() {
        PercentAccretionFactory percentAccretionFactory = new PercentAccretionFactory();
        PercentAccretion percentCounter = percentAccretionFactory.create(operation);
        resultSum = String.valueOf(percentCounter.calculate(data));
    }

    public PercentAccretionViewModel() {
        data = new PercentData();
        calculateButtonEnabled = false;
        initialSumIsCorrect = false;
        percentRateIsCorrect = false;
        countOfYearsIsCorrect = false;
        operation = PercentAccretionFactory.AccretionType.COMPLEX_PERCENT_SUM;
        errorMessage = PercentAccretionErrors.FIELD_IS_EMPTY.getMessage();
    }

    private boolean checkValue(final String value) {
        if (EMPTY_STRING.equals(value)) {
            errorMessage = PercentAccretionErrors.FIELD_IS_EMPTY.getMessage();
            return true;
        }
        if (!value.matches("\\d+")) {
            errorMessage = PercentAccretionErrors.INCORRECT_VALUES.getMessage();
            return true;
        }
        return false;
    }

    private boolean checkFieldsHaveRightValues() {
        return initialSumIsCorrect && percentRateIsCorrect && countOfYearsIsCorrect;
    }

    private void clearErrorMessage() {
        if (checkFieldsHaveRightValues()) {
            errorMessage = PercentAccretionErrors.SUCCESS.getMessage();
        }
    }
}
