package ru.unn.agile.WeightConverter.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.WeightConverter.Model.WeightConverter;
import ru.unn.agile.WeightConverter.Model.WeightUnit;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty value = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private final ObjectProperty<WeightUnit> inputUnit = new SimpleObjectProperty<>();
    private final ObjectProperty<WeightUnit> outputUnit = new SimpleObjectProperty<>();

    private final BooleanProperty conversionDisabled = new SimpleBooleanProperty();

    private final ObjectProperty<ObservableList<WeightUnit>> units =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(WeightUnit.values()));

    private ILogger logger;
    private List<ValueCachingChangeListener> valueChangedListeners;

    public void setLogger(final ILogger newLogger) {
        if (newLogger == null) {
            throw new IllegalArgumentException("Logger is NULL");
        }
        this.logger = newLogger;
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger newLogger) {
        setLogger(newLogger);
        init();
    }

    private void init() {
        outputUnit.set(WeightUnit.KILOGRAM);
        inputUnit.set(WeightUnit.GRAM);
        result.set("");
        value.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldConvert = new BooleanBinding() {
            {
                super.bind(value);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        conversionDisabled.bind(couldConvert.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(value);
        } };

        valueChangedListeners = new ArrayList<>();
        for (StringProperty field : fields) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void convert() {
        if (conversionDisabled.get()) {
            return;
        }

        WeightConverter weightConverter = new WeightConverter();

        result.set(weightConverter.convert(value.get(), inputUnit.get(), outputUnit.get()));
        status.set(Status.SUCCESS.toString());

        StringBuilder message = new StringBuilder(LogMessages.CONVERT_BUTTON_WAS_PRESSED);
        message.append("Entered parameters")
                .append(": value = ").append(value.get())
                .append("; input unit = ").append(inputUnit.get())
                .append("; output unit = ").append(outputUnit.get());
        logger.log(message.toString());
        updateLogs();
    }

    public void unitsChanged(final WeightUnit oldUnit, final WeightUnit newUnit) {
        if (oldUnit.equals(newUnit)) {
            return;
        }
        StringBuilder message = new StringBuilder(LogMessages.WEIGHT_UNITS_WERE_CHANGED);
        message.append("[ input = ").append(inputUnit.get())
                .append("; output = ").append(outputUnit.get())
                .append("]");
        logger.log(message.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueCachingChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
                message.append("Input value are: [")
                        .append(value.get()).append("]");
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
    public StringProperty valueProperty() {
        return value;
    }
    public StringProperty statusProperty() {
        return status;
    }
    public StringProperty resultProperty() {
        return result;
    }
    public final String getResult() {
        return result.get();
    }
    public final String getLogs() {
        return logs.get();
    }
    public final String getStatus() {
        return status.get();
    }
    public final ObjectProperty<WeightUnit> inputUnitProperty() {
        return inputUnit;
    }
    public final ObjectProperty<WeightUnit> outputUnitProperty() {
        return outputUnit;
    }
    public ObjectProperty<ObservableList<WeightUnit>> unitsProperty() {
        return units;
    }
    public final ObservableList<WeightUnit> getUnits() {
        return units.get();
    }
    public final boolean getConversionDisabled() {
        return conversionDisabled.get();
    }
    public BooleanProperty conversionDisabledProperty() {
        return conversionDisabled;
    }
    public StringProperty logsProperty() {
        return logs;
    }

    private Status getInputStatus() {
        Status statusInput = Status.READY;
        if (value.get().isEmpty()) {
            statusInput = Status.WAITING;
        }
        try {
            if (!value.get().isEmpty()) {
                double val = Double.parseDouble(value.get());
                if (val < 0) {
                    statusInput = Status.BAD_FORMAT;
                }
            }
        } catch (NumberFormatException e) {
            statusInput = Status.BAD_FORMAT;
        }

        return statusInput;
    }

    private void updateLogs() {
        String recording = new String();
        List<String> fullLog = logger.getLog();
        for (String log : fullLog) {
            recording += log + "\n";
        }
        logs.set(recording);
    }

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String currentValue = new String();
        private String previousValue = new String();
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            currentValue = newValue;
            status.set(getInputStatus().toString());
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
    READY("Press 'Convert' or Enter"),
    WAITING("Please provide input data"),
    SUCCESS("Success"),
    BAD_FORMAT("Bad format");

    private final String name;
    private Status(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}

final class LogMessages {
    public static final String CONVERT_BUTTON_WAS_PRESSED = "Convert. ";
    public static final String WEIGHT_UNITS_WERE_CHANGED = "Weight units were changed: ";
    public static final String EDITING_FINISHED = "Input value was updated. ";

    private LogMessages() {
    }
}
