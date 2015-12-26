package ru.unn.agile.IntersectionOfSegments.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateButtonAndStatusTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
        setInputData();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void statusIsReadyWhenFieldsAreCorrectFill() {
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenSomeFieldInvalid() {
        viewModel.seg1Point1XProperty().set("a");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.seg1Point1XProperty().set("");

        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsSuccessWhenCalculatingSuccessful() {
        viewModel.calculate();

        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenClearBadValue() {
        viewModel.seg1Point1XProperty().set("a");
        viewModel.seg1Point1XProperty().set("");

        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        viewModel.seg1Point1XProperty().set("a");

        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledIfNotEnoughCorrectData() {
        viewModel.seg1Point1XProperty().set("");

        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWhenFieldsAreCorrectFill() {
        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    private void setInputData() {
        viewModel.seg2Point1XProperty().set("0");
        viewModel.seg2Point1YProperty().set("0");
        viewModel.seg2Point2XProperty().set("5");
        viewModel.seg2Point2YProperty().set("5");

        viewModel.seg1Point1XProperty().set("0");
        viewModel.seg1Point1YProperty().set("5");
        viewModel.seg1Point2XProperty().set("5");
        viewModel.seg1Point2YProperty().set("0");
    }
}
