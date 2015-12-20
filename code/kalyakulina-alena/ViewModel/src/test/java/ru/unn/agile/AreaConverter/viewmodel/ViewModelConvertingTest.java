package ru.unn.agile.AreaConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.AreaConverter.model.AreaMeasure;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ViewModelConvertingTest {

    private final String inputArea;
    private final String resultArea;
    private final AreaMeasure from;
    private final AreaMeasure to;

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

    public ViewModelConvertingTest(final String inputArea,
                                   final String resultArea,
                                   final AreaMeasure from,
                                   final AreaMeasure to) {
        this.inputArea = inputArea;
        this.resultArea = resultArea;
        this.from = from;
        this.to = to;
    }

    @Parameterized.Parameters
    public static Collection measures() {
        return Arrays.asList(new Object[][]{
                {"0.0", "0.0", AreaMeasure.ARE, AreaMeasure.HECTARE},
                {"1.0", "1.0E-6", AreaMeasure.SQUARE_METER, AreaMeasure.SQUARE_KILOMETER},
                {"10.0", "0.1", AreaMeasure.SQUARE_METER, AreaMeasure.ARE},
                {"0.5", "5.0E-5", AreaMeasure.SQUARE_METER, AreaMeasure.HECTARE},
                {"7.5", "7500000.0", AreaMeasure.SQUARE_KILOMETER, AreaMeasure.SQUARE_METER},
                {"25.0", "250000.0", AreaMeasure.SQUARE_KILOMETER, AreaMeasure.ARE},
                {"0.01", "1.0", AreaMeasure.SQUARE_KILOMETER, AreaMeasure.HECTARE},
                {"500.0", "50000.0", AreaMeasure.ARE, AreaMeasure.SQUARE_METER},
                {"6.4", "6.4E-4", AreaMeasure.ARE, AreaMeasure.SQUARE_KILOMETER},
                {"10.0", "0.1", AreaMeasure.ARE, AreaMeasure.HECTARE},
                {"0.2", "2000.0", AreaMeasure.HECTARE, AreaMeasure.SQUARE_METER},
                {"78.0", "0.78", AreaMeasure.HECTARE, AreaMeasure.SQUARE_KILOMETER},
                {"0.01", "1.0", AreaMeasure.HECTARE, AreaMeasure.ARE}
        });
    }

    @Test
    public void isCorrectConverting() {
        viewModel.setInputArea(inputArea);
        viewModel.setFrom(from);
        viewModel.setTo(to);
        viewModel.convert();
        assertEquals(resultArea, viewModel.getResultArea());
    }
}
