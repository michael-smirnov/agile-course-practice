package ru.unn.agile.LengthConvertor.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.LengthConvertor.Model.LengthUnit;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty inputValue = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<LengthUnit>> units =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(LengthUnit.values()));
    private final ObjectProperty<LengthUnit> inputUnit = new SimpleObjectProperty<>();
    private final ObjectProperty<LengthUnit> outputUnit = new SimpleObjectProperty<>();
    private final BooleanProperty convertingDisabled = new SimpleBooleanProperty();

    private final StringProperty outputValue = new SimpleStringProperty();
    private final StringProperty hintMessage = new SimpleStringProperty();

    private List<InputValueChangeListener> valueChangedListeners;

    private ISimpleLogger logger;

    private final StringProperty logs = new SimpleStringProperty();

    public void setLogger(final ISimpleLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger can't be null");
        }
        this.logger = logger;
    }

    public ViewModel() {
        initialize();
    }

    public ViewModel(final ISimpleLogger logger) {
        setLogger(logger);
        initialize();
    }

    private void initialize() {
        inputValue.set("");
        inputUnit.set(LengthUnit.INCH);
        outputValue.set("");
        outputUnit.set(LengthUnit.FOOT);
        hintMessage.set(Status.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(inputValue);
            }
            @Override
            protected boolean computeValue() {
                return getHint() == Status.READY;
            }
        };
        convertingDisabled.bind(couldCalculate.not());

        final List<StringProperty> values = new ArrayList<StringProperty>() { {
            add(inputValue);
        } };

        valueChangedListeners = new ArrayList<>();
        for (StringProperty value : values) {
            final InputValueChangeListener listener = new InputValueChangeListener();
            value.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void convert() {
        outputValue.set(outputUnit.get().convert(inputValue.get(), inputUnit.get()));
        hintMessage.set(Status.SUCCESS.toString());

        StringBuilder message = new StringBuilder(LogMessages.CONVERT_WAS_PRESSED.toString());
        message.append(inputValue.get())
               .append(" ").append(inputUnit.get())
               .append("  =  ").append(outputValue.get())
               .append(" ").append(outputUnit.get());
        logger.addLogLine(message.toString());
        updateLogs();
    }

    public void lengthUnitsChanged(final LengthUnit oldValue, final LengthUnit newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        StringBuilder message = new StringBuilder(LogMessages.LENGTH_UNITS_WERE_CHANGED.toString());
        message.append("[").append(inputUnit.get())
               .append(", ").append(outputUnit.get()).append("]");
        logger.addLogLine(message.toString());
        updateLogs();
    }

    public void valueChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (InputValueChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(
                    LogMessages.INPUT_VALUE_WAS_CHANGED.toString()
                );
                message.append(inputValue.get());
                logger.addLogLine(message.toString());
                updateLogs();

                listener.cache();
                break;
            }
        }
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public StringProperty inputValueProperty() {
        return inputValue;
    }

    public ObjectProperty<ObservableList<LengthUnit>> unitsProperty() {
        return units;
    }

    public final ObservableList<LengthUnit> getUnits() {
        return units.get();
    }

    public ObjectProperty<LengthUnit> inputUnitProperty() {
        return inputUnit;
    }

    public ObjectProperty<LengthUnit> outputUnitProperty() {
        return outputUnit;
    }

    public BooleanProperty convertingDisabledProperty() {
        return convertingDisabled;
    }

    public final boolean getConvertingDisabled() {
        return convertingDisabled.get();
    }

    public StringProperty outputValueProperty() {
        return outputValue;
    }

    public final String getOutputValue() {
        return outputValue.get();
    }

    public StringProperty hintMessageProperty() {
        return hintMessage;
    }

    public final String getHintMessage() {
        return hintMessage.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    private Status getHint() {
        Status hintStatus = Status.READY;
        if (inputValue.get().isEmpty()) {
            hintStatus = Status.WAITING;
        }
        try {
            if (!inputValue.get().isEmpty()) {
                double value = Double.parseDouble(inputValue.get());
                if (value < 0) {
                    hintStatus = Status.BAD_FORMAT;
                }
            }
        } catch (NumberFormatException nfe) {
            hintStatus = Status.BAD_FORMAT;
        }

        return hintStatus;
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String();
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private class InputValueChangeListener implements ChangeListener<String> {
        private String previousValue = new String();
        private String currentValue = new String();
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            hintMessage.set(getHint().toString());
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
    WAITING("Enter value and choose units"),
    READY("Press \"Convert\""),
    BAD_FORMAT("Error: Wrong format"),
    SUCCESS("Converted successfully");

    private final String name;
    private Status(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

enum LogMessages {
    CONVERT_WAS_PRESSED("Convert. "),
    INPUT_VALUE_WAS_CHANGED("Input value was changed to "),
    LENGTH_UNITS_WERE_CHANGED("Length units were changed to ");

    private final String name;
    private LogMessages(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
