package ru.unn.agile.arabicroman.viewmodel;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ViewModelTests {
    private ArabicRomanConverterViewModel viewModel;


    @Before
    public void setUp() {
        viewModel = new ArabicRomanConverterViewModel(new FakeLogger());
    }

    @Test
    public void byDefaultConvertButtonDisabled() {
        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void byDefaultConvertedNumberIsArabic() {
        assertTrue(viewModel.isConvertedNumberArabic());
    }

    @Test
    public void byDefaultErrorMessageStringIsEmpty() {
        assertEquals("", viewModel.getErrorMessage());
    }

    @Test
    public void byDefaultInputNumberIsArabic() {
        assertEquals("Arabic Number", viewModel.getInputNumberFormat());
    }

    @Test
    public void byDefaultOutputNumberIsRoman() {
        assertEquals("Roman Number", viewModel.getOutputNumberFormat());
    }

    @Test
    public void whenNumberEnteredConvertButtonEnabled() {
        viewModel.setInputNumber("1");

        assertTrue(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void whenArabicNumberEmptyConvertButtonDisabled() {
        viewModel.setInputNumber("1");
        viewModel.setInputNumber("");

        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canConvertValidArabicNumber() {
        viewModel.setInputNumber("1");
        viewModel.convert();

        assertEquals("I", viewModel.getOutputNumber());
    }

    @Test
    public void canChangeConvertingDirection() {
        viewModel.reverseConvertingDirection();

        assertFalse(viewModel.isConvertedNumberArabic());
    }

    @Test
    public void canChangeConvertingDirectionTwice() {
        viewModel.reverseConvertingDirection();
        viewModel.reverseConvertingDirection();

        assertTrue(viewModel.isConvertedNumberArabic());
    }

    @Test
    public void inputNumberFormatChangedAfterReverse() {
        String oldOutputNumberFormat = viewModel.getOutputNumberFormat();
        viewModel.reverseConvertingDirection();

        assertEquals(oldOutputNumberFormat, viewModel.getInputNumberFormat());
    }

    @Test
    public void outputNumberFormatChangedAfterReverse() {
        String oldInputNumberFormat = viewModel.getInputNumberFormat();
        viewModel.reverseConvertingDirection();

        assertEquals(oldInputNumberFormat, viewModel.getOutputNumberFormat());
    }

    @Test
    public void outputNumberFormatIsNotChangedAfterDoubleReverse() {
        String oldOutputNumberFormat = viewModel.getOutputNumberFormat();
        viewModel.reverseConvertingDirection();
        viewModel.reverseConvertingDirection();

        assertEquals(oldOutputNumberFormat, viewModel.getOutputNumberFormat());
    }

    @Test
    public void inputNumberFormatIsNotChangedAfterDoubleReverse() {
        String oldInputNumberFormat = viewModel.getInputNumberFormat();
        viewModel.reverseConvertingDirection();
        viewModel.reverseConvertingDirection();

        assertEquals(oldInputNumberFormat, viewModel.getInputNumberFormat());
    }

    @Test
    public void convertButtonEnabledForRomanNumber() {
        viewModel.reverseConvertingDirection();
        viewModel.setInputNumber("X");

        assertTrue(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canConvertValidRomanNumber() {
        viewModel.reverseConvertingDirection();
        viewModel.setInputNumber("X");
        viewModel.convert();

        assertEquals("10", viewModel.getOutputNumber());
    }

    @Test
    public void whenEnteredInvalidArabicNumberConvertButtonDisabled() {
        viewModel.setInputNumber("12s");

        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canDetectInvalidRomanNumber() {
        viewModel.reverseConvertingDirection();
        viewModel.setInputNumber("sd");
        viewModel.convert();

        assertEquals("Illegal input number", viewModel.getErrorMessage());
    }

    @Test
    public void canDetectInvalidArabicNumber() {
        viewModel.setInputNumber("4000");
        viewModel.convert();

        assertEquals("Illegal input number", viewModel.getErrorMessage());
    }

    @Test
    public void errorMessageDisapearsAfterAnotherNumberEntered() {
        viewModel.setInputNumber("4000");
        viewModel.convert();
        viewModel.setInputNumber("400");

        assertEquals("", viewModel.getErrorMessage());
    }
}
