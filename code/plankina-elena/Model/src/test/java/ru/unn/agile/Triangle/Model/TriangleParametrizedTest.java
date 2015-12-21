package ru.unn.agile.Triangle.Model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TriangleParametrizedTest {

    private Triangle triangle;
    private CorrectAnswers correctAnswers;
    public TriangleParametrizedTest(final Triangle triangle,
                                    final CorrectAnswers correctAnswers) {
        this.triangle = triangle;
        this.correctAnswers = correctAnswers;
    }
    private static final double DELTA = 0.001;

    @Parameters
    public static Collection<Object[]> setOfTriangles() throws Exception {
        return Arrays.asList(new Object[][]{
                {new Triangle(Arrays.asList(1.9, -2.3, 4.56), Arrays.asList(0.0, 3.5, 5.12),
                                Arrays.asList(4.0, 5.0, 10.2), 3),
                        new CorrectAnswers(22.2273, 6.1289)},
                {new Triangle(Arrays.asList(-134.9, -211.32, -400.56),
                                Arrays.asList(2.0, 3.578293, -9955.12),
                                Arrays.asList(4.0, 5.0, 105.2), 3),
                        new CorrectAnswers(20185.6225, 9557.9568)},
                {new Triangle(Arrays.asList(1.9, -24.3, 4.56), Arrays.asList(0.0, 3.5, 5.12),
                                Arrays.asList(42.0, 54.0, 10.2), 3),
                        new CorrectAnswers(181.9012, 27.8704)},
                {new Triangle(Arrays.asList(1.9, -2.3, 4.56), Arrays.asList(0.0, 3.5, 5.12),
                                Arrays.asList(34.0, 35.0, 36.2), 3),
                        new CorrectAnswers(120.4386, 6.1289)},
                {new Triangle(Arrays.asList(-14.9, -11.32, -700.56),
                                Arrays.asList(2.0, 323.8293, -55.12),
                                Arrays.asList(-4.0, -5.0, 5.2), 3),
                        new CorrectAnswers(1767.7060, 727.4637)}});
    }

    @Test
    public void canFindPerimeterCorrectly() throws Exception {
        double perimeterResult = triangle.getPerimeter();
        double perimeterCorrect = correctAnswers.getPerimeter();
        assertEquals(perimeterResult, perimeterCorrect, DELTA);
    }

    @Test
    public void canFindLengthCorrectly() throws Exception {
        List<Double> lengths = triangle.getLengthsOfEdges();
        double lengthResult = lengths.get(0);
        double lengthCorrect = correctAnswers.getLength();
        assertEquals(lengthResult, lengthCorrect, DELTA);
    }
}

