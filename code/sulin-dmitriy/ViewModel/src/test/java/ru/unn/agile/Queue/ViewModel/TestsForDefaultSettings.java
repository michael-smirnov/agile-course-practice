package ru.unn.agile.Queue.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestsForDefaultSettings {

    private LabQueueViewModel viewModel;
    @Before
    public void setUpViewModel() {
        viewModel = new LabQueueViewModel();
    }

    @Test
    public void byDefaultFindButtonIsDisabled() {
        assertFalse(viewModel.isFindButtonEnabled());
    }

    @Test
    public void byDefaultPopButtonIsDisabled() {
        assertFalse(viewModel.isPopButtonEnabled());
    }

    @Test
    public void byDefaultSizeIsZero() {
        assertEquals(0, viewModel.getSize());
    }

    @Test
    public void byDefaultHeadElementIsEmpty() {
        assertEquals("", viewModel.getHeadElement());
    }

    @Test
    public void byDefaultInputIsEmpty() {
        assertEquals("", viewModel.getElement());
    }

    @Test
    public void byDefaultOutputIsEmpty() {
        assertEquals("", viewModel.getResult());
    }
}
