package ru.unn.agile.Triangle.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

public class TriangleCorrectnessTests {
    private static final double DELTA = 0.00001;
    private Triangle triangle;
    private double length1;
    private double length2;
    private double length3;

    @Before
    public void setUpTriangle() throws Exception {
        triangle = new Triangle(Arrays.asList(1.9, -2.3, 4.56),
                Arrays.asList(0.0, 3.5, 5.12), Arrays.asList(4.0, 5.0, 10.2), 3);
        length1 = sqrt(pow(1.9 - 0.0, 2) + pow(-2.3 - 3.5, 2) + pow(4.56 - 5.12, 2));
        length2 = sqrt(pow(4.0 - 0.0, 2) + pow(5.0 - 3.5, 2) + pow(10.2 - 5.12, 2));
        length3 = sqrt(pow(1.9 - 4.0, 2) + pow(-2.3 - 5.0, 2) + pow(4.56 - 10.2, 2));
    }

    @Test
    public void canFindCorrectPerimeter() throws Exception {
        double truePerimeter = 22.227365;
        double resultPerimeter = triangle.getPerimeter();
        assertEquals(truePerimeter, resultPerimeter, DELTA);
    }

    @Test
    public void canFindCorrectLength() throws Exception {
        double trueLength = 6.128915;
        List<Double> coordinatesOfPoint1 = triangle.getCoordinatesOfPoint1();
        List<Double> coordinatesOfPoint2 = triangle.getCoordinatesOfPoint2();
        double resultLength = triangle.getLength(coordinatesOfPoint1, coordinatesOfPoint2);
        assertEquals(trueLength, resultLength, DELTA);
    }

    @Test
    public void canFindCorrectSquare() throws Exception {
        double trueSquare = 20.244477;
        double resultSquare = triangle.getSquare();
        assertEquals(trueSquare, resultSquare, DELTA);
    }

    @Test
    public void canFindCorrectMedian() throws Exception {
        double trueMedian = 7.575757;
        List<Double> resultMedians = triangle.getMedians();
        double resultMedian = resultMedians.get(0);
        assertEquals(trueMedian, resultMedian, DELTA);
    }

    @Test
    public void canFindCorrectAltitude() throws Exception {
        double trueAltitude = 7.253279;
        List<Double> resultAltitudes = triangle.getAltitudes();
        double resultAltitude = resultAltitudes.get(0);
        assertEquals(trueAltitude, resultAltitude, DELTA);
    }

    @Test
    public void canFindCorrectBisectrix() throws Exception {
        double trueBisectrix = 4.282380;
        List<Double> resultBisectrices = triangle.getBisectrices();
        double resultBisectrix = resultBisectrices.get(2);
        assertEquals(trueBisectrix, resultBisectrix, DELTA);
    }

    @Test
    public void canFindCorrectAngle() throws Exception {
        double trueAngle = 1.667918;
        List<Double> resultAngles = triangle.getAngles();
        double resultAngle = resultAngles.get(2);
        assertEquals(trueAngle, resultAngle, DELTA);
    }
}
