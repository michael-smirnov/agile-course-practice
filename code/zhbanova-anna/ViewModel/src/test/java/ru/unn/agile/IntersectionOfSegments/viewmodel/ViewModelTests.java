package ru.unn.agile.IntersectionOfSegments.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
        setFirstSegmentValue("1", "5", "5", "5");
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void findOfIntersectionReturnCorrectResultWhenOneCommonPoint() {
        setSecondSegmentValue("0", "2", "6", "8");

        viewModel.calculate();

        assertEquals("Segments intersection in one point (3.0; 5.0).",
                viewModel.resultProperty().get());
    }

    @Test
    public void findOfIntersectionReturnCorrectResultWhenNotIntersection() {
        setSecondSegmentValue("0", "0", "10", "4");

        viewModel.calculate();

        assertEquals("Segments not intersection.", viewModel.resultProperty().get());
    }

    @Test
    public void findOfIntersectionReturnCorrectResultWhenOnePartOfOther() {
        setSecondSegmentValue("2", "5", "5", "5");

        viewModel.calculate();

        assertEquals("Segments have common part [(2.0; 5.0); (5.0; 5.0)].\nLength = 3.0",
                viewModel.resultProperty().get());
    }

    @Test
    public void findOfIntersectionReturnCorrectResultWhenHaveCommonPart() {
        setSecondSegmentValue("2", "5", "10", "5");

        viewModel.calculate();

        assertEquals("Segments have common part [(2.0; 5.0); (5.0; 5.0)].\nLength = 3.0",
                viewModel.resultProperty().get());
    }

    private void setSecondSegmentValue(final String startX, final String startY,
                                       final String finishX, final String finishY) {
        viewModel.seg2Point1XProperty().set(startX);
        viewModel.seg2Point1YProperty().set(startY);
        viewModel.seg2Point2XProperty().set(finishX);
        viewModel.seg2Point2YProperty().set(finishY);
    }

    private void setFirstSegmentValue(final String startX, final String startY,
                                      final String finishX, final String finishY) {
        viewModel.seg1Point1XProperty().set(startX);
        viewModel.seg1Point1YProperty().set(startY);
        viewModel.seg1Point2XProperty().set(finishX);
        viewModel.seg1Point2YProperty().set(finishY);
    }
}
