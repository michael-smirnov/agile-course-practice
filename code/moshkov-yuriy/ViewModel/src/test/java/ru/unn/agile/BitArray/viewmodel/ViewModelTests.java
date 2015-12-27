package ru.unn.agile.BitArray.viewmodel;

import org.junit.Before;
import org.junit.Test;

import ru.unn.agile.BitArray.model.BitArray;
import ru.unn.agile.BitArray.viewmodel.ViewModel.Operation;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;
    private BitArray testBitArray;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void initializeViewModel() {
        setViewModel(new ViewModel(new FakeBitArrayLogger()));
        testBitArray = new BitArray(5);
    }

    @Test
    public void canCreateViewModelWithFakeLogger() {
        assertNotNull(viewModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotCreateViewModelWithNullLogger() {
        ViewModel viewModel = new ViewModel(null);
    }

    @Test
    public void isEmptyLogByDefault() {
        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void isDoOperationNotEnabledByDefault() {
        assertFalse(viewModel.isDoOperationEnabled());
    }

    @Test
    public void isInitArrayBtnNotEnabledByDefault() {
        assertFalse(viewModel.isInitializeArrayButtonEnabled());
    }

    @Test
    public void isEmptyNotificationByDefault() {
        assertEquals(viewModel.getNotification(), ViewModel.Notification.EMPTY_STRING);
    }

    @Test
    public void isInitArrayNotEnabledWhenInputEmptyString() {
        viewModel.setArraySize("");

        assertFalse(viewModel.isInitializeArrayButtonEnabled());
    }

    @Test
    public void isInitArrayNotEnabledWhenInputInvalidNumber() {
        viewModel.setArraySize("aaaa");

        assertFalse(viewModel.isInitializeArrayButtonEnabled());
    }

    @Test
    public void isInvalidNumberNotificationWhenInputInvalidNumber() {
        viewModel.setArraySize("aaaa");

        assertEquals(viewModel.getNotification(), ViewModel.Notification.INVALID_NUMBER);
    }

    @Test
    public void isEmptyNotificationWhenInputValidNumber() {
        viewModel.setArraySize("11");

        assertEquals(viewModel.getNotification(), ViewModel.Notification.EMPTY_STRING);
    }

    @Test
    public void isInitArrayEnabledWhenInputValidNumber() {
        viewModel.setArraySize("11");

        assertTrue(viewModel.isInitializeArrayButtonEnabled());
    }

    @Test
    public void isFirstBitArrayNotNullWhenInitArray() {
        viewModel.setArraySize("5");

        viewModel.initializeArray();

        assertNotNull(viewModel.gitFirstBitArray());
    }

    @Test
    public void isFirstBitArraySizeEquals5WhenInputSize5() {
        viewModel.setArraySize("5");

        viewModel.initializeArray();

        assertEquals(viewModel.gitFirstBitArray().getSize(), 5);
    }

    @Test
    public void isSecondBitArraySizeEquals5WhenInputSize5() {
        viewModel.setArraySize("5");

        viewModel.initializeArray();

        assertEquals(viewModel.getSecondBitArray().getSize(), 5);
    }

    @Test
    public void isResultBitArraySizeEquals5WhenInputSize5() {
        viewModel.setArraySize("5");

        viewModel.initializeArray();

        assertEquals(viewModel.getResultBitArray().getSize(), 5);
    }

    @Test
    public void isDoOperationEnabledWhenInitArrays() {
        viewModel.setArraySize("5");

        viewModel.initializeArray();

        assertTrue(viewModel.isDoOperationEnabled());
    }

    @Test
    public void isDefaultOperationOr() {
        assertEquals(Operation.OR, viewModel.getOperation());
    }

    @Test
    public void canSetOperation() {
        viewModel.setOperation(Operation.AND);

        assertEquals(Operation.AND, viewModel.getOperation());
    }

    @Test
    public void canSetFirstBitArray() {
        viewModel.setFirstBitArray(testBitArray);

        assertEquals(testBitArray, viewModel.getFirstBitArray());
    }

    @Test
    public void canSetSecondBitArray() {
        viewModel.setSecondBitArray(testBitArray);

        assertEquals(testBitArray, viewModel.getSecondBitArray());
    }

    @Test
    public void canDoOrOperation() {
        initArraysForOperations();

        viewModel.doOperation();

        assertNotNull(viewModel.getResultBitArray());
    }

    @Test
    public void canDoAndOperation() {
        viewModel.setOperation(Operation.AND);
        initArraysForOperations();

        viewModel.doOperation();

        assertNotNull(viewModel.getResultBitArray());
    }

    @Test
    public void canDoNotOperation() {
        viewModel.setOperation(Operation.NOT);
        initArraysForOperations();

        viewModel.doOperation();

        assertNotNull(viewModel.getResultBitArray());
    }

    @Test
    public void canDoXorOperation() {
        viewModel.setOperation(Operation.XOR);
        initArraysForOperations();

        viewModel.doOperation();

        assertNotNull(viewModel.getResultBitArray());
    }

    @Test
    public void initArrayAddsMessageToLog() {
        viewModel.setArraySize("5");

        viewModel.initializeArray();

        assertFalse(viewModel.getLog().isEmpty());
    }

    @Test
    public void setSizeArrayAddsMessageToLog() {
        viewModel.setArraySize("5");
        viewModel.logUpdatedSize();

        String message = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(message.matches(".*" + ViewModel.LogMessages.UPDATE_ARRAY_SIZE + ".*"));
    }

    @Test
    public void initArrayAddsMessageInitArrayWithSize() {
        viewModel.setArraySize("5");

        viewModel.initializeArray();
        String message = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(message.matches(".*" + ViewModel.LogMessages.INIT_ARRAY_WITH_SIZE + ".*"));
    }

    @Test
    public void doOperationAddsMessageToLog() {
        viewModel.setOperation(Operation.XOR);
        initArraysForOperations();

        viewModel.doOperation();

        assertFalse(viewModel.getLog().isEmpty());
    }

    @Test
    public void doOperationAddsMessageDidOperation() {
        viewModel.setOperation(Operation.XOR);
        initArraysForOperations();

        viewModel.doOperation();
        String message = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(message.matches(".*" + ViewModel.LogMessages.OPERATION_DID + ".*"));
    }

    @Test
    public void changeOperationAddsMessageToLog() {
        viewModel.setOperation(Operation.XOR);

        assertFalse(viewModel.getLog().isEmpty());
    }

    @Test
    public void changeOperationNotAddsMessageToLogWithSameOperation() {
        viewModel.setOperation(Operation.XOR);
        int sizeFirst = viewModel.getLog().size();

        viewModel.setOperation(Operation.XOR);

        assertEquals(viewModel.getLog().size(), sizeFirst);
    }

    @Test
    public void changeOperationAddsMessageChangeOperation() {
        viewModel.setOperation(Operation.XOR);
        String message = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(message.matches(".*" + ViewModel.LogMessages.OPERATION_CHANGED + ".*"));
    }

    @Test
    public void updatedFirstBitArrayAddsMessageUpdatedFirstBitArray() {
        initArraysForOperations();
        viewModel.logUpdatedFirstBitArray();

        String message = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(message.matches(".*" + ViewModel.LogMessages.UPDATE_FIRST_BIT_ARRAY + ".*"));
    }

    @Test
    public void updatedSecondBitArrayAddsMessageUpdatedSecondBitArray() {
        initArraysForOperations();
        viewModel.logUpdatedSecondBitArray();

        String message = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(message.matches(".*" + ViewModel.LogMessages.UPDATE_SECOND_BIT_ARRAY + ".*"));
    }

    private void initArraysForOperations() {
        viewModel.setArraySize("5");
        viewModel.initializeArray();
        BitArray firstBitArray = viewModel.getFirstBitArray();
        BitArray secondBitArray = viewModel.getSecondBitArray();
        firstBitArray.setAll(true);
        secondBitArray.setAll(false);
        viewModel.setFirstBitArray(firstBitArray);
        viewModel.setSecondBitArray(secondBitArray);
    }
}
