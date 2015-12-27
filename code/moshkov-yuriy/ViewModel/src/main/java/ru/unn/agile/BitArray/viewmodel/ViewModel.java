package ru.unn.agile.BitArray.viewmodel;

import ru.unn.agile.BitArray.model.BitArray;

import java.util.List;

public class ViewModel {
    private BitArray firstBitArray;
    private BitArray secondBitArray;
    private BitArray resultBitArray;

    private boolean isDoOperationButtonEnabled;
    private String inputSizeArray;
    private boolean isInitializeArrayButtonEnabled;
    private Operation operation;
    private String notification;
    private IBitArrayLogger logger;

    public ViewModel() {
        isDoOperationButtonEnabled = false;
        firstBitArray = new BitArray(0);
        secondBitArray = new BitArray(0);
        resultBitArray = new BitArray(0);
        operation = Operation.OR;
        operation.setViewModel(this);
        notification = Notification.EMPTY_STRING;
    }

    public ViewModel(final IBitArrayLogger logger) {
        this();

        setLogger(logger);
    }


    public boolean isDoOperationEnabled() {
        return isDoOperationButtonEnabled;
    }

    public void setArraySize(final String arraySize) {
        this.inputSizeArray = arraySize;
        int size;
        try {
            size = Integer.parseInt(inputSizeArray);
            isInitializeArrayButtonEnabled = size > 0;
            notification = Notification.EMPTY_STRING;
        } catch (NumberFormatException exception) {
            isInitializeArrayButtonEnabled = false;
            notification = Notification.INVALID_NUMBER;
        }
    }

    public boolean isInitializeArrayButtonEnabled() {
        return isInitializeArrayButtonEnabled;
    }

    public BitArray gitFirstBitArray() {
        return firstBitArray;
    }

    public void logUpdatedSize() {
        logger.log(LogMessages.UPDATE_ARRAY_SIZE + inputSizeArray);
    }

    public void initializeArray() {
        int size = Integer.parseInt(inputSizeArray);
        firstBitArray = new BitArray(size);
        secondBitArray = new BitArray(size);
        resultBitArray = new BitArray(size);

        isDoOperationButtonEnabled = true;
        logger.log(LogMessages.INIT_ARRAY_WITH_SIZE + size);
    }

    public void setOperation(final Operation operation) {
        if (!this.operation.toString().equals(operation.toString())) {
            logger.log(LogMessages.OPERATION_CHANGED + operation.toString());
        }

        this.operation = operation;
        operation.setViewModel(this);
    }

    public BitArray getFirstBitArray() {
        return firstBitArray;
    }

    public BitArray getSecondBitArray() {
        return secondBitArray;
    }

    public BitArray getResultBitArray() {
        return resultBitArray;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setFirstBitArray(final BitArray firstBitArray) {
        this.firstBitArray = firstBitArray;
    }

    public void doOperation() {
        operation.doOperation();

        logger.log(LogMessages.OPERATION_DID + operation.toString());
    }

    public void setSecondBitArray(final BitArray secondBitArray) {
        this.secondBitArray = secondBitArray;
    }

    public String getNotification() {
        return notification;
    }

    public void setLogger(final IBitArrayLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public void logUpdatedFirstBitArray() {
        logger.log(LogMessages.UPDATE_FIRST_BIT_ARRAY + firstBitArray.toString());
    }

    public void logUpdatedSecondBitArray() {
        logger.log(LogMessages.UPDATE_SECOND_BIT_ARRAY + secondBitArray.toString());
    }

    public enum Operation {
        OR("OR") {
            @Override
            public void doOperation() {
                viewModel.resultBitArray = viewModel.firstBitArray.or(
                        viewModel.secondBitArray);
            }
        },
        AND("AND") {
            @Override
            public void doOperation() {
                viewModel.resultBitArray = viewModel.firstBitArray.and(
                        viewModel.secondBitArray);
            }
        },
        NOT("NOT") {
            @Override
            public void doOperation() {
                viewModel.resultBitArray = viewModel.firstBitArray.not();
            }
        },
        XOR("XOR") {
            @Override
            public void doOperation() {
                viewModel.resultBitArray = viewModel.firstBitArray.xor(
                        viewModel.secondBitArray);
            }
        };

        private static ViewModel viewModel;
        private final String name;

        private Operation(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

        public void setViewModel(final ViewModel viewModel) {
            Operation.viewModel = viewModel;
        }

        public abstract void doOperation();
    }

    public final class Notification {
        public static final String INVALID_NUMBER = "Please input number > 0";
        public static final String EMPTY_STRING = "";

        private Notification() { }
    }

    final class LogMessages {
        public static final String OPERATION_DID = "Operation did ";
        public static final String OPERATION_CHANGED = "Operation changed to ";
        public static final String INIT_ARRAY_WITH_SIZE = "Init arrays with size ";
        public static final String UPDATE_ARRAY_SIZE = "Size of arrays updated to ";
        public static final String UPDATE_FIRST_BIT_ARRAY = "First bit array updated to ";
        public static final String UPDATE_SECOND_BIT_ARRAY = "Second bit array updated to ";

        private LogMessages() { }
    }
}
