package ru.unn.agile.AreaConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.AreaConverter.model.AreaMeasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewModelTest {

    private ViewModel viewModel;

    @Before
    public void setUp() {
        AreaConverterFakeLogger logger = new AreaConverterFakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getInputArea());
        assertEquals("", viewModel.getResultArea());
        assertEquals(Status.WAITING, viewModel.getStatus());
        assertEquals(AreaMeasure.SQUARE_METER, viewModel.getFrom());
        assertEquals(AreaMeasure.SQUARE_METER, viewModel.getTo());
        assertEquals(false, viewModel.isConvertButtonEnabled());
        assertEquals(false, viewModel.isInputChanged());
    }

    @Test
    public void canSetInputArea() {
        final String inputArea = "10.0";

        viewModel.setInputArea(inputArea);

        assertEquals(inputArea, viewModel.getInputArea());
    }

    @Test
    public void canSetStatusReadyWhenInputAreaIsFilledCorrectly() {
        viewModel.setInputArea("1.0");

        viewModel.parseInput();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isConvertButtonEnableWhenInputAreaIsFilled() {
        viewModel.setInputArea("5.0");

        viewModel.parseInput();

        assertEquals(true, viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canSetStatusWaitingWhenInputAreaIsNotFilled() {
        viewModel.setInputArea("");

        viewModel.parseInput();

        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isConvertButtonDisableWhenInputAreaIsNotFilled() {
        viewModel.setInputArea("");

        viewModel.parseInput();

        assertEquals(false, viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canSetStatusWrongFormatWhenInputAreaIsIncorrect() {
        viewModel.setInputArea("z");

        viewModel.parseInput();

        assertEquals(Status.WRONG_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isConvertButtonDisableWhenInputAreaIsIncorrect() {
        viewModel.setInputArea("m");

        viewModel.parseInput();

        assertEquals(false, viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canSetStatusNegativeAreaWhenInputAreaIsNegative() {
        viewModel.setInputArea("-1.0");

        viewModel.parseInput();

        assertEquals(Status.NEGATIVE_AREA, viewModel.getStatus());
    }

    @Test
    public void isConvertButtonDisableWhenInputAreaIsNegative() {
        viewModel.setInputArea("-100.0");

        viewModel.parseInput();

        assertEquals(false, viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canSetAreaMeasureFrom() {
        viewModel.setFrom(AreaMeasure.ARE);

        assertEquals(AreaMeasure.ARE, viewModel.getFrom());
    }

    @Test
    public void canSetAreaMeasureTo() {
        viewModel.setTo(AreaMeasure.HECTARE);

        assertEquals(AreaMeasure.HECTARE, viewModel.getTo());
    }

    @Test
    public void canSetStatusSuccessWhenConvertingIsDone() {
        viewModel.setInputArea("7.0");
        viewModel.setFrom(AreaMeasure.SQUARE_METER);
        viewModel.setTo(AreaMeasure.SQUARE_KILOMETER);

        viewModel.convert();

        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void isInputChangedWhenInputNewAreaValue() {
        viewModel.setInputArea("56.0");

        assertTrue(viewModel.isInputChanged());
    }
}
