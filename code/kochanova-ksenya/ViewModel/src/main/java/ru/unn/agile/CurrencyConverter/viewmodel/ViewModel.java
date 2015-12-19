package ru.unn.agile.CurrencyConverter.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.CurrencyConverter.Model.Currency;
import ru.unn.agile.CurrencyConverter.Model.UnitCurrency;


import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty inputValue = new SimpleStringProperty();
    private final StringProperty outputValue = new SimpleStringProperty();

    private final ObjectProperty<UnitCurrency> inputUnit = new SimpleObjectProperty<>();
    private final ObjectProperty<UnitCurrency> outputUnit = new SimpleObjectProperty<>();

    private final BooleanProperty convertingDisable = new SimpleBooleanProperty();

    private final ObjectProperty<ObservableList<UnitCurrency>> units =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(UnitCurrency.values()));

    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private ILogger logger;
    private List<ValueCachingChangeListener> valueChangedListeners;
    public void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }
    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        inputValue.set("");
        outputValue.set("");
        status.set(Status.WAITING.toString());
        inputUnit.set(UnitCurrency.DOLLAR);
        outputUnit.set(UnitCurrency.RUBLE);

        BooleanBinding couldConvert = new BooleanBinding() {
            {
                super.bind(inputValue);
            }
            @Override
            protected boolean computeValue() {
                if (getInputStatus() == Status.READY) {
                    return true;
                }
                return false;
            }
        };
        convertingDisable.bind(couldConvert.not());

        final List<StringProperty> value = new ArrayList<StringProperty>() { {
            add(inputValue);
        } };
        valueChangedListeners = new ArrayList<>();
        for (StringProperty val : value) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            val.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void convert() {
        if (convertingDisable.get()) {
            return;
        }

        Currency inputCurrency = new Currency(inputValue.getValue(), inputUnit.get());
        outputValue.set(outputUnit.get().convertCurrency(inputCurrency,
                outputUnit.get()).toString());
        status.set(Status.DONE.toString());
        String message = LogMessages.CONVERT_WAS_PRESSED.toString();
        message += "Data:"
                + " inputValue = " + inputValue.get()
                + " inputUnit: " + inputUnit.get().toString()
                + " outputUnit: " + outputUnit.get().toString();
        logger.log(message.toString());
        updateLogs();
    }

    public void onUnitChanged(final UnitCurrency previousUnit, final UnitCurrency newUnit) {
        if (previousUnit.equals(newUnit)) {
            return;
        }
        String message = LogMessages.CURRENCY_UNIT_WAS_CHANGED.toString();
        message += newUnit.toString();
        logger.log(message.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean previousValue, final Boolean newValue) {
        if (!previousValue && newValue) {
            return;
        }

        for (ValueCachingChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                String message = LogMessages.EDITING_WAS_FINISHED.toString();
                message += "Data: "
                        + " inputValue = " + inputValue.get()
                        + " inputUnit: " + inputUnit.get().toString()
                        + " outputUnit: " + outputUnit.get().toString();
                logger.log(message.toString());
                updateLogs();
                listener.cache();
                break;
            }
        }
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public StringProperty logsProperty() {
        return logs;
    }
    public final String getLogs() {
        return logs.get();
    }

    private void updateLogs() {
        List<String> log = logger.getLog();
        String note = new String();
        for (String line : log) {
            note += line + "\n";
        }
        logs.set(note);
    }

    public StringProperty inputValueProperty() {
        return inputValue;
    }

    public StringProperty outputValueProperty() {
        return outputValue;
    }

    public final ObjectProperty<UnitCurrency> inputUnitProperty() {
        return inputUnit;
    }

    public final String getOutputValue() {
        return outputValue.get();
    }

    public final ObjectProperty<UnitCurrency> outputUnitProperty() {
        return outputUnit;
    }

    public ObjectProperty<ObservableList<UnitCurrency>> unitsProperty() {
        return units;
    }

    public final ObservableList<UnitCurrency> getUnits() {
        return units.get();
    }

    public BooleanProperty convertingDisableProperty() {
        return convertingDisable;
    }

    public final boolean getConvertingDisable() {
        return convertingDisable.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (inputValue.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!inputValue.get().isEmpty()) {
               double value = Double.parseDouble(inputValue.get());
                if (value < 0) {
                    inputStatus = Status.WRONG_FORMAT;
                }
            }
        } catch (NumberFormatException e) {
            inputStatus = Status.WRONG_FORMAT;
        }

        return inputStatus;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String valueBefore, final String valueAfter) {
            status.set(getInputStatus().toString());
        }
    }

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String previousValue = new String();
        private String currentValue = new String();
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String previousValue, final String newValue) {
            if (previousValue.equals(newValue)) {
                return;
            }
            status.set(getInputStatus().toString());
            currentValue = newValue;
        }
        public boolean isChanged() {
            return !previousValue.equals(currentValue);
        }
        public void cache() {
            previousValue = currentValue;
        }
    }
}

enum Status {
    WAITING("Please, input value and choose currency units"),
    READY("Press 'Convert'"),
    WRONG_FORMAT("Wrong format, please, use 0.00 format"),
    DONE("Done");

    private final String name;
    private Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
enum LogMessages {
    CONVERT_WAS_PRESSED("Converting. "),
    CURRENCY_UNIT_WAS_CHANGED("Currency unit was changed to "),
    EDITING_WAS_FINISHED("Input data was updated. ");

    private final String message;
    private LogMessages(final String message) {
        this.message = message;
    }
    public String toString() {
        return message;
    }
}
