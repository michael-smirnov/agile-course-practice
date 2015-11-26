package ru.unn.agile.Triangle.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TriangleExceptionsTests {
    private Triangle triangle;
    @Before
    public void setUpTriangles() throws Exception {
        triangle = new Triangle(Arrays.asList(1.9, -2.3, 4.56),
                Arrays.asList(0.0, 3.5, 5.12), Arrays.asList(4.0, 5.0, 10.2), 3);
    }

    @Test
    public void canCreateNewTriangle() {
        assertNotNull(triangle);
    }

    @Test
    public void canBuildNonDegenerateTriangle() throws Exception {
        List<Double> coordinates1 = triangle.getCoordinatesOfPoint1();
        List<Double> coordinates2 = triangle.getCoordinatesOfPoint2();
        List<Double> coordinates3 = triangle.getCoordinatesOfPoint3();
        assertEquals(triangle.isPossibleToBuildNondegenerateTriangle(coordinates1,
                coordinates2, coordinates3), true);
    }

    @Test
    public void canFindLength() throws Exception {
        List<Double> coordinates1 = triangle.getCoordinatesOfPoint1();
        List<Double> coordinates3 = triangle.getCoordinatesOfPoint3();
        double length = triangle.getLength(coordinates1, coordinates3);
        assertNotNull(length);
    }

    @Test
    public void canFindLengths() throws Exception {
        List<Double> lengths = triangle.getLengthsOfEdges();
        assertNotNull(lengths);
    }

    @Test
    public void canFindPerimeter() throws Exception {
        double perimeter = triangle.getPerimeter();
        assertNotNull(perimeter);
    }

    @Test
    public void canFindSquare() throws Exception {
        double square = triangle.getSquare();
        assertNotNull(square);
    }

    @Test
    public void canFindAltitudes() throws Exception {
        List<Double> altitudes = triangle.getAltitudes();
        assertNotNull(altitudes);
    }

    @Test
    public void canFindAngles() throws Exception {
        List<Double> angles = triangle.getAngles();
        assertNotNull(angles);
    }

    @Test(expected = Exception.class)
    public void isOverflowWhenGetLengthWithLargeNumber() throws Exception {
        Triangle triangleException = new Triangle(Arrays.asList(0.0, 0.0, 0.0),
                Arrays.asList(Double.MAX_VALUE, 0.0, Double.MAX_VALUE),
                Arrays.asList(0.0, 0.0, 0.0), 3);
        List<Double> lengths = triangleException.getLengthsOfEdges();
        double length1 = lengths.get(0);
    }

    @Test(expected = Exception.class)
    public void isOverflowWhenFindPerimeterWithLargeNumber() throws Exception {
        Triangle triangleException = new Triangle(Arrays.asList(0.0, 0.0, 0.0),
                Arrays.asList(Double.MAX_VALUE, 0.0, 0.0), Arrays.asList(0.0, 0.0, 0.0), 3);
        double perimeter = triangleException.getPerimeter();
    }

    @Test(expected = Exception.class)
    public void isOverflowWhenFindSquareWithLargeNumber() throws Exception {
        Triangle triangleException =
                new Triangle(Arrays.asList(2.4 * Math.sqrt(Double.MAX_VALUE), 0.0, 0.0),
                Arrays.asList(1.4 * Math.sqrt(Double.MAX_VALUE), 0.0, 0.0),
                Arrays.asList(1 * Math.sqrt(Double.MAX_VALUE), 0.0, 0.0), 3);
        triangleException.getSquare();
    }
}
