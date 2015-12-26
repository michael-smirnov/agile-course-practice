package ru.unn.agile.IntersectionOfSegments.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CanSetDefaultValues {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValuesForFirstSegment() {
        assertEquals("", viewModel.seg1Point1XProperty().get());
        assertEquals("", viewModel.seg1Point1YProperty().get());
        assertEquals("", viewModel.seg1Point2XProperty().get());
        assertEquals("", viewModel.seg1Point2YProperty().get());
    }

    @Test
    public void canSetDefaultValuesForSecondSegment() {
        assertEquals("", viewModel.seg2Point1XProperty().get());
        assertEquals("", viewModel.seg2Point1YProperty().get());
        assertEquals("", viewModel.seg2Point2XProperty().get());
        assertEquals("", viewModel.seg2Point2YProperty().get());
    }

    @Test
    public void canSetDefaultValuesForResult() {
        assertEquals("", viewModel.resultProperty().get());
    }

    @Test
    public void canSetDefaultValuesForStatus() {
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.calculationDisabledProperty().get());
    }
}
