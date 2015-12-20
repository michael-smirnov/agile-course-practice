package ru.unn.agile.BitArray.viewmodel;

import org.junit.Before;
import org.junit.Test;

import ru.unn.agile.BitArray.model.BitArray;
import ru.unn.agile.BitArray.viewmodel.ViewModel.Operation;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;
    private BitArray testBitArray;

    @Before
    public void initializeViewModel() {
        viewModel = new ViewModel();
        testBitArray = new BitArray(5);
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
        assertTrue(viewModel.gitFirstBitArray().getSize() == 5);
    }

    @Test
    public void isSecondBitArraySizeEquals5WhenInputSize5() {
        viewModel.setArraySize("5");
        viewModel.initializeArray();
        assertTrue(viewModel.getSecondBitArray().getSize() == 5);
    }

    @Test
    public void isResultBitArraySizeEquals5WhenInputSize5() {
        viewModel.setArraySize("5");
        viewModel.initializeArray();
        assertTrue(viewModel.getResultBitArray().getSize() == 5);
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
