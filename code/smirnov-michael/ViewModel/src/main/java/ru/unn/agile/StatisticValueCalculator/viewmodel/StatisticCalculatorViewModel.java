package ru.unn.agile.StatisticValueCalculator.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import ru.unn.agile.StatisticValueCalculator.model.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatisticCalculatorViewModel {
    private final StringProperty nameOfCalculatedStatistic = new SimpleStringProperty("");
    private final StringProperty valueOfCalculatedStatistic = new SimpleStringProperty("");

    private final ObjectProperty<InputNote> inputRowError =
            new SimpleObjectProperty<>(InputNote.VALID_INPUT);
    private final ObjectProperty<InputNote> inputStatisticParameterError =
            new SimpleObjectProperty<>(InputNote.VALID_INPUT);

    private final ObjectProperty<ObservableList<StatisticValue>> listOfAvailableStatistics =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(StatisticValue.values()));

    private final ObjectProperty<StatisticValue> selectedStatistic;

    private final ObjectProperty<StatisticParameter> parameterNameOfSelectedStatistic
            = new SimpleObjectProperty<>();

    private final BooleanProperty inputStatisticParameterFieldIsVisible
            = new SimpleBooleanProperty();

    private final StringProperty inputRow = new SimpleStringProperty("1.0");
    private final StringProperty inputStatisticParameter = new SimpleStringProperty("0.0");

    private final ObservableList<Pair<String, String>> statisticData
            = FXCollections.observableArrayList();

    private final AddStatisticParameterChangeListener parameterChangeListener
            = new AddStatisticParameterChangeListener();
    private final AddValueChangeListener valueChangeListener = new AddValueChangeListener();

    private final BooleanProperty addInputRowIsDisabled = new SimpleBooleanProperty(false);
    private final BooleanProperty calculationIsDisabled = new SimpleBooleanProperty(false);
    private final BooleanProperty deleteDataRowIsDisabled = new SimpleBooleanProperty(true);
    private final StringProperty logText = new SimpleStringProperty();

    private int selectedRowInStatisticData = -1;
    private ILoggerOfStatisticCalculator logger;
    private Boolean isDataTableReforming = false;

    public StatisticCalculatorViewModel() {
        selectedStatistic =
                new SimpleObjectProperty<>(StatisticValue.MEAN);
        setSelectedStatistic(selectedStatistic.get());

        inputRow.addListener(valueChangeListener);
        inputStatisticParameter.addListener(parameterChangeListener);
        selectedStatistic.addListener(new SelectedStatisticListener());

        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.0", "2.1", "3.2",
                "2.1", "1.0", "-5.4", "2.4", "0.0"));

        for (Integer i = 1; i <= data.size(); i++) {
            statisticData.add(new Pair<>(i.toString(), data.get(i - 1)));
        }

        setLogger(new DefaultLogger());
    }

    public StatisticCalculatorViewModel(final ILoggerOfStatisticCalculator logger) {
        this();

        if (logger == null) {
            throw new NullPointerException("Logger must not be null");
        }

        setLogger(logger);
    }

    public String getNameOfCalculatedStatistic() {
        return nameOfCalculatedStatistic.get();
    }

    public String getValueOfCalculatedStatistic() {
        return valueOfCalculatedStatistic.get();
    }

    public InputNote getInputRowError() {
        return inputRowError.get();
    }

    public InputNote getInputStatisticParameterError() {
        return inputStatisticParameterError.get();
    }

    public ObservableList<StatisticValue> getListOfAvailableStatistics() {
        return listOfAvailableStatistics.get();
    }

    public StatisticValue getSelectedStatistic() {
        return selectedStatistic.get();
    }

    public StatisticParameter getParameterNameOfSelectedStatistic() {
        return parameterNameOfSelectedStatistic.get();
    }

    public boolean getInputStatisticParameterFieldIsVisible() {
        return inputStatisticParameterFieldIsVisible.get();
    }

    public String getInputRow() {
        return inputRow.get();
    }

    public String getInputStatisticParameter() {
        return inputStatisticParameter.get();
    }

    public ObservableList<Pair<String, String>> getStatisticData() {
        return statisticData;
    }

    public boolean getAddInputRowIsDisabled() {
        return addInputRowIsDisabled.get();
    }

    public boolean getCalculationIsDisabled() {
        return calculationIsDisabled.get();
    }

    public boolean getDeleteDataRowIsDisabled() {
        return deleteDataRowIsDisabled.get();
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public String getLogText() {
        return logText.get();
    }

    public StringProperty nameOfCalculatedStatisticProperty() {
        return nameOfCalculatedStatistic;
    }

    public StringProperty valueOfCalculatedStatisticProperty() {
        return valueOfCalculatedStatistic;
    }

    public ObjectProperty<InputNote> inputRowErrorProperty() {
        return inputRowError;
    }

    public ObjectProperty<InputNote> inputStatisticParameterErrorProperty() {
        return inputStatisticParameterError;
    }
    public ObjectProperty<StatisticValue> selectedStatisticProperty() {
        return selectedStatistic;
    }

    public ObjectProperty<StatisticParameter> parameterNameOfSelectedStatisticProperty() {
        return parameterNameOfSelectedStatistic;
    }

    public BooleanProperty inputStatisticParameterFieldIsVisibleProperty() {
        return inputStatisticParameterFieldIsVisible;
    }

    public StringProperty inputRowProperty() {
        return inputRow;
    }

    public StringProperty inputStatisticParameterProperty() {
        return inputStatisticParameter;
    }

    public BooleanProperty addInputRowIsDisabledProperty() {
        return addInputRowIsDisabled;
    }

    public BooleanProperty calculationIsDisabledProperty() {
        return calculationIsDisabled;
    }

    public BooleanProperty deleteDataRowIsDisabledProperty() {
        return deleteDataRowIsDisabled;
    }

    public StringProperty logTextProperty() {
        return logText;
    }

    public void setSelectedStatistic(final StatisticValue selectedStatisticInfo) {
        selectedStatistic.set(selectedStatisticInfo);
        StatisticParameter parameterName = selectedStatisticInfo.getParameter();
        parameterNameOfSelectedStatistic.set(parameterName);

        if (parameterName == null) {
            inputStatisticParameterFieldIsVisible.set(false);
            return;
        }

        inputStatisticParameterFieldIsVisible.set(true);
    }

    public void setInputRow(final String value) {
        inputRow.set(value);
    }

    public void setStatisticData(final ObservableList<Pair<String, String>> data) {
        statisticData.setAll(data);

        if (statisticData.isEmpty()) {
            calculationIsDisabled.set(true);
        }
    }

    public void setLogger(final ILoggerOfStatisticCalculator logger) {
        this.logger = logger;
    }

    public void addRowToStatisticData() {
        Integer numberOfAddValue = statisticData.size() + 1;
        statisticData.add(new Pair<>(numberOfAddValue.toString(), inputRow.getValue()));
        calculationIsDisabled.set(false);
        clearCalculatedStatistic();

        tryToLogging(LogMessages.newValueToDataTableIsAdded(inputRow.get()));
    }

    public void makeRowInDataNotSelected() {
        selectedRowInStatisticData = -1;
        deleteDataRowIsDisabled.set(true);
    }

    public void selectRowInStatisticData(final Integer rowNumber) {
        if (rowNumber > 0 && rowNumber <= statisticData.size()) {
            selectedRowInStatisticData = rowNumber;
            deleteDataRowIsDisabled.set(false);

            tryToLogging(LogMessages.rowInDataTableSelected(rowNumber,
                    statisticData.get(rowNumber - 1).getValue()));
        } else {
            makeRowInDataNotSelected();
        }
    }

    public void deleteSelectedRowInStatisticData() {
        if (!deleteDataRowIsDisabled.get()) {
            tryToLogging(LogMessages.rowInDataTableDeleted(
                    selectedRowInStatisticData,
                    statisticData.get(selectedRowInStatisticData - 1).getValue()));

            statisticData.remove(selectedRowInStatisticData - 1);
            calculationIsDisabled.set(statisticData.isEmpty());
            reformIndexesInStatisticData();
            clearCalculatedStatistic();
        }
    }

    public void clearStatisticData() {
        statisticData.clear();
        calculationIsDisabled.set(true);
        clearCalculatedStatistic();

        tryToLogging(LogMessages.dataTableIsCleared());
    }

    public void calculateSelectedStatistic() {
        ArrayList<Double> data = new ArrayList<>();

        for (Pair<String, String> item : statisticData) {
            data.add(Double.parseDouble(item.getValue()));
        }

        IStatisticValueCalculator calculator;
        switch (selectedStatistic.get()) {
            case MEAN:
                calculator = new MeanCalculator();
                break;

            case VARIANCE:
                calculator = new VarianceCalculator();
                break;

            case PROBABILITY:
                double event = Double.parseDouble(inputStatisticParameter.get());
                calculator = new ProbabilityOfEventCalculator(event);
                break;

            case RAW_MOMENT:
                int order = Integer.parseInt(inputStatisticParameter.get());
                calculator = new RawMomentCalculator(order);
                break;

            case CENTRAL_MOMENT:
                order = Integer.parseInt(inputStatisticParameter.get());
                calculator = new CentralMomentCalculator(order);
                break;

            default:
                throw new InvalidParameterException();
        }

        Double statisticValue = calculator.calculate(data);
        nameOfCalculatedStatistic.set(selectedStatistic.get().toString());
        valueOfCalculatedStatistic.set(statisticValue.toString());

        tryToLogging(LogMessages.statisticValueCalculated(
                selectedStatistic.get(),
                valueOfCalculatedStatistic.get(),
                inputStatisticParameter.get()));
    }

    public void onInputFieldFocusChanged(final Boolean wasFocusedEarlier,
                                         final Boolean isFocusedNow) {
        if (!wasFocusedEarlier && isFocusedNow) {
            return;
        }
        if (valueChangeListener.isChanged()) {
            tryToLogging(LogMessages.inputRowValueIsSet(inputRow.get()));
            valueChangeListener.resetChangedState();
        }
        if (parameterChangeListener.isChanged()) {
            tryToLogging(LogMessages.inputParameterValueIsSet(
                    parameterNameOfSelectedStatistic.get(), inputStatisticParameter.get()));
            parameterChangeListener.resetChangedState();
        }
    }

    private void tryToLogging(final String message) {
        if (!isDataTableReforming) {
            logger.set(message);
            updateLogProperty();
        }
    }

    private void clearCalculatedStatistic() {
        nameOfCalculatedStatistic.set("");
        valueOfCalculatedStatistic.set("");
    }

    private void updateLogProperty() {
        List<String> logMessages = logger.getLog();
        String logText = "";

        for (String message : logMessages) {
            logText += message + "\n";
        }

        this.logText.set(logText);
    }

    private void reformIndexesInStatisticData() {
        isDataTableReforming = true;

        for (Integer i = 1; i <= statisticData.size(); i++) {
            Pair<String, String> oldRow = statisticData.get(i - 1);
            Pair<String, String> newRow = new Pair<>(i.toString(), oldRow.getValue());
            statisticData.set(i - 1, newRow);
        }

        isDataTableReforming = false;
    }

    private void checkValidationOfStatisticParameterValue() {
        StatisticParameter parameterName = getSelectedStatistic().getParameter();
        String parameterValue = inputStatisticParameter.get();

        inputStatisticParameterError.set(InputNote.VALID_INPUT);
        if (parameterName == StatisticParameter.ORDER) {
            try {
                Integer order = Integer.parseInt(parameterValue);
                if (order <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException exception) {
                inputStatisticParameterError.set(InputNote.NOT_A_POSITIVE_INTEGER);
                calculationIsDisabled.set(true);
            }
        }

        try {
            Double.parseDouble(parameterValue);
        } catch (NumberFormatException exception) {
            inputStatisticParameterError.set(InputNote.NOT_A_NUMBER);
            calculationIsDisabled.set(true);
        }
    }

    private class ValueChangedListener {
        private String currentValue = "";
        private String previousValue = "";

        public void changeValue(final String newValue) {
            previousValue = currentValue;
            currentValue = newValue;
        }
        public Boolean isChanged() {
            return !currentValue.equals(previousValue);
        }
        public void resetChangedState() {
            previousValue = currentValue;
        }
    }

    private class AddValueChangeListener extends ValueChangedListener
            implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            super.changeValue(newValue);

            inputRowError.set(InputNote.VALID_INPUT);
            addInputRowIsDisabled.set(false);

            if (newValue.isEmpty()) {
                addInputRowIsDisabled.set(true);
                return;
            }

            try {
                Double.parseDouble(newValue);
            } catch (NumberFormatException exception) {
                inputRowError.set(InputNote.NOT_A_NUMBER);
                addInputRowIsDisabled.set(true);
            }
        }
    }

    private class SelectedStatisticListener extends ValueChangedListener
            implements ChangeListener<StatisticValue> {
        @Override
        public void changed(final ObservableValue<? extends StatisticValue> observable,
                            final StatisticValue oldValue, final StatisticValue newValue) {
            super.changeValue(newValue.name());
            if (super.isChanged()) {
                tryToLogging(LogMessages.statisticValueSelected(newValue));

                StatisticParameter parameter = newValue.getParameter();
                if (parameter == StatisticParameter.EVENT) {
                    inputStatisticParameter.set("0.0");
                } else if (parameter == StatisticParameter.ORDER) {
                    inputStatisticParameter.set("1");
                }

                super.resetChangedState();
            }
        }
    }

    private class AddStatisticParameterChangeListener extends ValueChangedListener
            implements ChangeListener<String> {

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            super.changeValue(newValue);
            calculationIsDisabled.set(statisticData.isEmpty());

            if (selectedStatistic.getValue().getParameter() == null) {
                return;
            }

            if (newValue.isEmpty()) {
                calculationIsDisabled.set(true);
                return;
            }

            checkValidationOfStatisticParameterValue();
        }
    }
}

class DefaultLogger implements ILoggerOfStatisticCalculator {

    @Override
    public void set(final String description) {
        final String message = "This method doesn't do anything";
    }

    @Override
    public List<String> getLog() {
        return Collections.singletonList("Real logger is not set");
    }
}

enum InputNote {
    VALID_INPUT(""),
    NOT_A_NUMBER("The adding value must be a number"),
    NOT_A_POSITIVE_INTEGER("The adding value must be integer > 0");

    private String message;

    InputNote(final String errorMessage) {
        message = errorMessage;
    }

    @Override
    public String toString() {
        return message;
    }
}
