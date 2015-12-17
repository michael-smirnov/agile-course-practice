package ru.unn.agile.arabicroman.viewmodel;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ViewModelWithLoggerTests {
    private ArabicRomanConverterViewModel viewModelWithLogger;

    @Before
    public void setUp() {
        viewModelWithLogger = new ArabicRomanConverterViewModel(new FakeLogger());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModelWithLogger);
    }

    @Test (expected = IllegalArgumentException.class)
    public void throwsOnNullLoggerAsArgumentInViewModelConstructor() {
        viewModelWithLogger = new ArabicRomanConverterViewModel(null);
    }
}
