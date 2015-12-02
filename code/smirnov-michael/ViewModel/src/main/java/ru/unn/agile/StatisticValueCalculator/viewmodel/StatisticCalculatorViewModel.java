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

    private int selectedRowInStatisticData = -1;
    private ILoggerOfStatisticCalculator logger;

    public StatisticCalculatorViewModel() {
        selectedStatistic =
                new SimpleObjectProperty<>(StatisticValue.ENUMERATION);
        setSelectedStatistic(selectedStatistic.get());

        inputRow.addListener(valueChangeListener);
        inputStatisticParameter.addListener(parameterChangeListener);
        selectedStatistic.addListener(new SelectedStatisticListener());

        ArrayList<String> data = new ArrayList<>(Arrays.asList("1.0", "2.1", "3.2",
                "2.1", "1.0", "-5.4", "2.4", "0.0"));

        for (Integer i = 1; i <= data.size(); i++) {
            statisticData.add(new Pair<>(i.toString(), data.get(i - 1)));
        }
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

    public void setSelectedStatistic(final StatisticValue selectedStatisticInfo) {
        selectedStatistic.set(selectedStatisticInfo);
        StatisticParameter parameterName = selectedStatisticInfo.getParameterName();
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
    public void setLogger(ILoggerOfStatisticCalculator logger) {
        this.logger = logger;
    }

    public void addRowToStatisticData() {
        Integer numberOfAddValue = statisticData.size() + 1;
        statisticData.add(new Pair<>(numberOfAddValue.toString(), inputRow.getValue()));
        calculationIsDisabled.set(false);
    }
    public void makeRowInDataNotSelected() {
        selectedRowInStatisticData = -1;
        deleteDataRowIsDisabled.set(true);
    }
    public void selectRowInStatisticData(final Integer rowNumber) {
        if (rowNumber >= 0 && rowNumber < statisticData.size()) {
            selectedRowInStatisticData = rowNumber;
            deleteDataRowIsDisabled.set(false);
        } else {
            makeRowInDataNotSelected();
        }
    }
    public void deleteSelectedRowInStatisticData() {
        if (!deleteDataRowIsDisabled.get()) {
            statisticData.remove(selectedRowInStatisticData);
            calculationIsDisabled.set(statisticData.isEmpty());
            reformIndexesInStatisticData();
        }
    }
    public void clearStatisticData() {
        statisticData.clear();
        calculationIsDisabled.set(true);
    }
    public void calculateSelectedStatistic() {
        ArrayList<Double> data = new ArrayList<>();

        for (Pair<String, String> item : statisticData) {
            data.add(Double.parseDouble(item.getValue()));
        }

        IStatisticValueCalculator calculator;
        switch (selectedStatistic.get()) {
            case ENUMERATION:
                calculator = new MeanCalculator();
                break;

            case VARIANCE:
                calculator = new VarianceCalculator();
                break;

            case PROBABILITY:
                double event = Double.parseDouble(inputStatisticParameter.get());
                calculator = new ProbabilityOfEventCalculator(event);
                break;

            case ROW_MOMENT:
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
    }
    public void inputFieldFocusChanged(Boolean oldFocusStatus, Boolean newFocusStatus) {
        if (!oldFocusStatus && newFocusStatus || logger == null) {
            return;
        }
        if(valueChangeListener.isChanged()) {
            logger.addMessage("[Changed input row value]: " + inputRow.get());
            valueChangeListener.resetChangedState();
        }
    }


    private void reformIndexesInStatisticData() {
        for (Integer i = 1; i <= statisticData.size(); i++) {
            Pair<String, String> oldRow = statisticData.get(i - 1);
            Pair<String, String> newRow = new Pair<>(i.toString(), oldRow.getValue());
            statisticData.set(i - 1, newRow);
        }
    }

    private class AddValueChangeListener implements ChangeListener<String> {
        private String currentAddValue;
        private String previousAddValue;
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            previousAddValue = currentAddValue;
            currentAddValue = newValue;

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

        Boolean isChanged() {
            return !currentAddValue.equals(previousAddValue);
        }
        void resetChangedState() {
            previousAddValue = currentAddValue;
        }
    }

    private class SelectedStatisticListener implements ChangeListener<StatisticValue> {
        @Override
        public void changed(final ObservableValue<? extends StatisticValue> observable,
                            final StatisticValue oldValue, final StatisticValue newValue) {
            parameterChangeListener.changed(inputStatisticParameter, "",
                    inputStatisticParameter.get());
        }
    }

    private class AddStatisticParameterChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            inputStatisticParameterError.set(InputNote.VALID_INPUT);
            calculationIsDisabled.set(statisticData.isEmpty());

            if (selectedStatistic.getValue().getParameterName() == null) {
                return;
            }

            if (newValue.isEmpty()) {
                calculationIsDisabled.set(true);
                return;
            }

            StatisticParameter currentParameterName =
                    getSelectedStatistic()
                            .getParameterName();
            if (currentParameterName == StatisticParameter.ORDER) {
                try {
                    Integer order = Integer.parseInt(newValue);
                    if (order <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException exception) {
                    inputStatisticParameterError.set(InputNote.NOT_A_POSITIVE_INTEGER);
                    calculationIsDisabled.set(true);
                }
            }

            try {
                Double.parseDouble(newValue);
            } catch (NumberFormatException exception) {
                inputStatisticParameterError.set(InputNote.NOT_A_NUMBER);
                calculationIsDisabled.set(true);
            }
        }
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
