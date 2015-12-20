package ru.unn.agile.PercentAccretion.viewmodel;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.PercentAccretion.Model.PercentAccretionFactory;

import static org.junit.Assert.*;

public class PercentAccretionViewModelTest {
    private PercentAccretionViewModel viewModel;

    @Before
    public void initialize() {
        viewModel = new PercentAccretionViewModel();
    }

    @Test
    public void byDefaultCalculateBtnIsDisabled() {
        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void whenFieldsAreEmptyShowErrorMessage() {
        assertEquals(PercentAccretionViewModel.
                        PercentAccretionErrors.FIELD_IS_EMPTY.getMessage(),
                viewModel.getErrorMessage());
    }

    @Test
    public void whenFillAllFieldsCalculateBtnIsEnabled() {
        viewModel.setPercentRate("1");
        viewModel.setCountOfYears("1");
        viewModel.setInitialSum("1");

        assertTrue(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void whenFillAllFieldsClearErrorMessage() {
        viewModel.setPercentRate("1");
        viewModel.setCountOfYears("1");
        viewModel.setInitialSum("1");

        assertEquals(PercentAccretionViewModel.
                        PercentAccretionErrors.SUCCESS.getMessage(),
                viewModel.getErrorMessage());
    }

    @Test
    public void whenOneOfFieldsIsFilledIncorrectShowErrorMessage() {
        viewModel.setInitialSum("1");
        viewModel.setPercentRate("1");
        viewModel.setCountOfYears("a");

        assertEquals(PercentAccretionViewModel.
                        PercentAccretionErrors.INCORRECT_VALUES.getMessage(),
                viewModel.getErrorMessage());
    }

    @Test
    public void whenCorrectWrongValueOfFieldClearErrorMessage() {
        viewModel.setInitialSum("1");
        viewModel.setPercentRate("1");
        viewModel.setCountOfYears("a");
        viewModel.setCountOfYears("1");

        assertEquals(PercentAccretionViewModel.
                        PercentAccretionErrors.SUCCESS.getMessage(),
                viewModel.getErrorMessage());
    }

    @Test
    public void whenOneOfFieldsIsClearedShowErrorMessage() {
        viewModel.setInitialSum("1");
        viewModel.setPercentRate("1");
        viewModel.setCountOfYears("1");
        viewModel.setCountOfYears("");

        assertEquals(PercentAccretionViewModel.
                        PercentAccretionErrors.FIELD_IS_EMPTY.getMessage(),
                viewModel.getErrorMessage());
    }

    @Test
    public void whenCalculateSimplePercentSumShowResult() {
        String expectedValue = "3.0";

        viewModel.setInitialSum("1");
        viewModel.setPercentRate("100");
        viewModel.setCountOfYears("2");
        viewModel.setPercentType(PercentAccretionFactory.
                AccretionType.SIMPLE_PERCENT_SUM.toString());
        viewModel.calculateResultSum();

        assertEquals(expectedValue, viewModel.getResultSum());
    }

    @Test
    public void whenCalculateComplexPercentSumShowResult() {
        String expectedValue = "4.0";

        viewModel.setInitialSum("1");
        viewModel.setPercentRate("100");
        viewModel.setCountOfYears("2");
        viewModel.setPercentType(PercentAccretionFactory.
                AccretionType.COMPLEX_PERCENT_SUM.toString());
        viewModel.calculateResultSum();

        assertEquals(expectedValue, viewModel.getResultSum());
    }

    @Test
    public void whenSumIsCalculatedAndOneOfFieldsIsChangedClearSum() {
        String expectedValue = "";

        viewModel.setInitialSum("1");
        viewModel.setPercentRate("100");
        viewModel.setCountOfYears("2");
        viewModel.setPercentType(PercentAccretionFactory.
                AccretionType.SIMPLE_PERCENT_SUM.toString());
        viewModel.calculateResultSum();
        viewModel.setInitialSum("2");

        assertEquals(expectedValue, viewModel.getResultSum());
    }

    @Test
    public void whenSumIsCalculatedAndPercentTypeIsChangedClearSum() {
        String expectedValue = "";

        viewModel.setInitialSum("1");
        viewModel.setPercentRate("100");
        viewModel.setCountOfYears("2");
        viewModel.setPercentType(PercentAccretionFactory.
                AccretionType.SIMPLE_PERCENT_SUM.toString());
        viewModel.calculateResultSum();
        viewModel.setPercentType(PercentAccretionFactory.
                AccretionType.COMPLEX_PERCENT_SUM.toString());

        assertEquals(expectedValue, viewModel.getResultSum());
    }
}
