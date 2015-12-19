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

    private final BooleanProperty convertationDisable = new SimpleBooleanProperty();

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
        convertationDisable.bind(couldConvert.not());

        final List<StringProperty> data = new ArrayList<StringProperty>() { {
            add(inputValue);
        } };
        valueChangedListeners = new ArrayList<>();
        for (StringProperty val : data) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            val.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void convert() {
        if (convertationDisable.get()) {
            return;
        }

        Currency inputCurrency = new Currency(inputValue.getValue(), inputUnit.get());
        outputValue.set(outputUnit.get().convertCurrency(inputCurrency,
                outputUnit.get()).toString());
        status.set(Status.DONE.toString());
        String message = new String(LogMessages.CONVERT_WAS_PRESSED);
        message += "Data:"
                + " inputValue = " + inputValue.get()
                + " inputUnit: " + inputUnit.get().toString()
                + " outputUnit: " + outputUnit.get().toString();
        logger.record(message.toString());
        updateLogs();
    }

    public void onUnitChanged(final UnitCurrency previousUnit, final UnitCurrency newUnit) {
        if (previousUnit.equals(newUnit)) {
            return;
        }
        String message = new String(LogMessages.CURRENCY_UNIT_WAS_CHANGED);
        message += newUnit.toString();
        logger.record(message.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean previousValue, final Boolean newValue) {
        if (!previousValue && newValue) {
            return;
        }

        for (ValueCachingChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                String message = new String(LogMessages.EDIT_WAS_FINISHED);
                message += "Data: "
                        + " inputValue = " + inputValue.get()
                        + " inputUnit: " + inputUnit.get().toString()
                        + " outputUnit: " + outputUnit.get().toString();
                logger.record(message.toString());
                updateLogs();
                listener.cache();
                break;
            }
        }
    }

    public final List<String> getRecord() {
        return logger.getRecord();
    }

    public StringProperty logsProperty() {
        return logs;
    }
    public final String getLogs() {
        return logs.get();
    }

    private void updateLogs() {
        List<String> record = logger.getRecord();
        String note = new String();
        for (String line : record) {
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

    public BooleanProperty convertationDisableProperty() {
        return convertationDisable;
    }

    public final boolean getConvertationDisable() {
        return convertationDisable.get();
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
final class LogMessages {
    public static final String CONVERT_WAS_PRESSED = "Convertation. ";
    public static final String CURRENCY_UNIT_WAS_CHANGED = "Currency unit was changed to ";
    public static final String EDIT_WAS_FINISHED = "Input data was updated. ";

    private LogMessages() { }
}
