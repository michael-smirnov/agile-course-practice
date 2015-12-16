package ru.unn.agile.HexBinOctCalculator.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.HexBinOctCalculator.Model.Number;
import ru.unn.agile.HexBinOctCalculator.Model.NumeralSystem;
import ru.unn.agile.HexBinOctCalculator.Model.HexBinOctCalculator.Operation;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty value1 = new SimpleStringProperty();
    private final StringProperty value2 = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<NumeralSystem>> systems =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(NumeralSystem.values()));
    private final ObjectProperty<NumeralSystem> system1 = new SimpleObjectProperty<>();
    private final ObjectProperty<NumeralSystem> system2 = new SimpleObjectProperty<>();
    private final ObjectProperty<NumeralSystem> finalSystem = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();
    private final BooleanProperty calcDisabled = new SimpleBooleanProperty();

    private final StringProperty logs = new SimpleStringProperty();
    private final StringProperty calcResult = new SimpleStringProperty();
    private final StringProperty calcStatus = new SimpleStringProperty();

    private ILogger logger;
    private List<ValueCachingChangeListener> valueChangedListeners;

    public void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger is null");
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
        value1.set("");
        system1.set(NumeralSystem.BIN);
        value2.set("");
        system2.set(NumeralSystem.BIN);
        finalSystem.set(NumeralSystem.HEX);
        operation.set(Operation.ADD);
        calcResult.set("");
        calcStatus.set(Status.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(value1, value2);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calcDisabled.bind(couldCalculate.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(value1);
            add(value2);
        } };

        valueChangedListeners = new ArrayList<>();
        for (StringProperty field : fields) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void calculate() {
        if (calcDisabled.get()) {
            throw new IllegalStateException("Calculation is disabled");
        }

        Number first = new Number(value1.get(), system1.get());
        Number second = new Number(value2.get(), system2.get());

        calcResult.set(operation.get().apply(first, second,
                finalSystem.get()).getValue().toString());
        calcStatus.set(Status.SUCCESS.toString());

        StringBuilder message = new StringBuilder(LogMessages.CALCULATE_BUTTON_WAS_PRESSED);
        message.append("Entered numbers")
                .append(": Value1 = ").append(value1.get())
                .append("; System1 = ").append(system1.get())
                .append("; Value2 = ").append(value2.get())
                .append("; System2 = ").append(system2.get())
                .append(" Operation: ").append(operation.get().toString()).append(".");
        logger.log(message.toString());
        updateLogs();
    }

    public void operationChanged(final Operation previousValue, final Operation replacedValue) {
        if (previousValue.equals(replacedValue)) {
            return;
        }
        StringBuilder message = new StringBuilder(LogMessages.OPERATION_WAS_CHANGED);
        message.append(replacedValue.toString());
        logger.log(message.toString());
        updateLogs();
    }

    public void resultSystemChanged(final NumeralSystem previousValue,
                                    final NumeralSystem replacedValue) {
        if (previousValue.equals(replacedValue)) {
            return;
        }
        StringBuilder message = new StringBuilder(LogMessages.RESULT_NUMERAL_SYSTEM_WAS_CHANGED);
        message.append(replacedValue.toString());
        logger.log(message.toString());
        updateLogs();
    }

    public void fieldChanged(final Boolean previousValue, final Boolean replacedValue) {
        if (!previousValue && replacedValue) {
            return;
        }
        for (ValueCachingChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                logToString();
                updateLogs();

                listener.cache();
                break;
            }
        }
    }

    public void systemChanged(final NumeralSystem previousValue,
                              final NumeralSystem replacedValue) {
        if (previousValue.equals(replacedValue)) {
            return;
        }

        logToString();
        updateLogs();
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public StringProperty value1Property() {
        return value1;
    }
    public StringProperty value2Property() {
        return value2;
    }
    public ObjectProperty<NumeralSystem> system1Property() {
        return system1;
    }
    public ObjectProperty<NumeralSystem> system2Property() {
        return system2;
    }
    public ObjectProperty<NumeralSystem> finalSystemProperty() {
        return finalSystem;
    }
    public ObjectProperty<ObservableList<NumeralSystem>> systemsProperty() {
        return systems;
    }
    public final ObservableList<NumeralSystem> getSystems() {
        return systems.get();
    }
    public ObjectProperty<ObservableList<Operation>> operationsProperty() {
        return operations;
    }
    public final ObservableList<Operation> getOperations() {
        return operations.get();
    }
    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }
    public BooleanProperty calcDisabledProperty() {
        return calcDisabled;
    }
    public final boolean getCalcDisabled() {
        return calcDisabled.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }
    public final String getLogs() {
        return logs.get();
    }
    public StringProperty calcResultProperty() {
        return calcResult;
    }
    public final String getCalcResult() {
        return calcResult.get();
    }
    public StringProperty calcStatusProperty() {
        return calcStatus;
    }
    public final String getCalcStatus() {
        return calcStatus.get();
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (value1.get().isEmpty() || value2.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!value1.get().isEmpty()) {
                Long.parseLong(value1.get(), system1.get().getValue());
            }
            if (!value2.get().isEmpty()) {
                Long.parseLong(value2.get(), system2.get().getValue());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }

        return inputStatus;
    }

    private void updateLogs() {
        List<String> totalLog = logger.getLog();
        String record = new String();
        for (String log : totalLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private void logToString() {
        StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
        message.append("Entered numbers are: [")
                .append(value1.get()).append("; ")
                .append(system1.get()).append("; ")
                .append(value2.get()).append("; ")
                .append(system2.get()).append("]");
        logger.log(message.toString());
    }

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String previusValue = new String();
        private String currentValue = new String();
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String previousValue, final String replacedValue) {
            if (previousValue.equals(replacedValue)) {
                return;
            }
            calcStatus.set(getInputStatus().toString());
            currentValue = replacedValue;
        }
        public boolean isChanged() {
            return !previusValue.equals(currentValue);
        }
        public void cache() {
            previusValue = currentValue;
        }
    }
}

enum Status {
    WAITING("Enter values and choose numeral systems"),
    READY("Press 'Calculate'"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;
    private Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

final class LogMessages {
    public static final String CALCULATE_BUTTON_WAS_PRESSED = "Calculate. ";
    public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
    public static final String RESULT_NUMERAL_SYSTEM_WAS_CHANGED =
            "Result numeral system was changed to ";
    public static final String EDITING_FINISHED = "Input data were updated. ";

    private LogMessages() {

    }
}
