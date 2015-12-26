package ru.unn.agile.Triangle.Model;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.acos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Triangle {
    private final List<Double> coordinatesOfPoint1;
    private final List<Double> coordinatesOfPoint2;
    private final List<Double> coordinatesOfPoint3;
    private static final double THE_HALF = 0.5;

    public Triangle(final List<Double> coordinatesOfPoint1,
                    final List<Double> coordinatesOfPoint2,
                    final List<Double> coordinatesOfPoint3,
                    final int inputDimension) throws TriangleExceptions {
        if (!hasEqualDimensions(coordinatesOfPoint1, coordinatesOfPoint2,
                coordinatesOfPoint3, inputDimension)) {
            throw new TriangleExceptions(TriangleExceptions.DIFFERENT_DIMENSIONS);
        }
        if (!isPossibleToBuildNondegenerateTriangle(coordinatesOfPoint1,
                coordinatesOfPoint2, coordinatesOfPoint3)) {
            throw new TriangleExceptions(TriangleExceptions.DEGENERATE_TRIANGLE);
        }
        this.coordinatesOfPoint1 = coordinatesOfPoint1;
        this.coordinatesOfPoint2 = coordinatesOfPoint2;
        this.coordinatesOfPoint3 = coordinatesOfPoint3;
    }

    public List<Double> getCoordinatesOfPoint1() {
        return coordinatesOfPoint1;
    }

    public List<Double> getCoordinatesOfPoint2() {
        return coordinatesOfPoint2;
    }

    public List<Double> getCoordinatesOfPoint3() {
        return coordinatesOfPoint3;
    }

    private boolean hasEqualDimensions(final List<Double> coordinatesOfPoint1,
                                             final List<Double> coordinatesOfPoint2,
                                             final List<Double> coordinatesOfPoint3,
                                             final int dimension) {
        return coordinatesOfPoint1.size() == dimension
                && coordinatesOfPoint2.size() == dimension
                && coordinatesOfPoint3.size() == dimension;
    }

    private boolean isPossibleToBuildNondegenerateTriangle(
            final List<Double> coordinatesOfPoint1, final List<Double> coordinatesOfPoint2,
            final List<Double> coordinatesOfPoint3)
            throws TriangleExceptions {
        double lengthOfEdge1 = getLength(coordinatesOfPoint1, coordinatesOfPoint2);
        double lengthOfEdge2 = getLength(coordinatesOfPoint2, coordinatesOfPoint3);
        double lengthOfEdge3 = getLength(coordinatesOfPoint1, coordinatesOfPoint3);
        return lengthOfEdge1 < lengthOfEdge2 + lengthOfEdge3
                && lengthOfEdge2 < lengthOfEdge1 + lengthOfEdge3
                && lengthOfEdge3 < lengthOfEdge1 + lengthOfEdge2;
    }

    public static double getLength(final List<Double> coordinatesOfPoint1,
                                   final List<Double> coordinatesOfPoint2)
            throws TriangleExceptions {
        double sum = 0.0;
        for (int i = 0; i < coordinatesOfPoint1.size(); i++) {
            if (sum > Double.MAX_VALUE) {
                throw new TriangleExceptions(TriangleExceptions.LENGTH_OVERFLOW);
            }
            sum += pow(coordinatesOfPoint1.get(i) - coordinatesOfPoint2.get(i), 2);
        }
        return sqrt(sum);
    }

    public List<Double> getLengthsOfEdges() throws TriangleExceptions {
        double length1 = getLength(coordinatesOfPoint1, coordinatesOfPoint2);
        double length2 = getLength(coordinatesOfPoint2, coordinatesOfPoint3);
        double length3 = getLength(coordinatesOfPoint3, coordinatesOfPoint1);
        return Arrays.asList(length1, length2, length3);
    }

    public double getPerimeter() throws TriangleExceptions {
        List<Double> lengths = getLengthsOfEdges();
        if (lengths.get(0) + lengths.get(1) > Double.MAX_VALUE - lengths.get(2)) {
            throw new TriangleExceptions(TriangleExceptions.PERIMETER_OVERFLOW);
        }
            return lengths.get(0) + lengths.get(1) + lengths.get(2);
    }

    public double getSquare() throws TriangleExceptions {
        List<Double> lengths = getLengthsOfEdges();
        double halfPerimeter = getPerimeter() * THE_HALF;
        double interimResult = getInterimSquareValues(halfPerimeter, lengths.get(0),
                lengths.get(1), lengths.get(2));
        if (interimResult > Double.MAX_VALUE) {
            throw new TriangleExceptions(TriangleExceptions.SQUARE_OVERFLOW);
        }
        return sqrt(interimResult);
    }

    private double getInterimSquareValues(final double halfPerimeter, final double length1,
                                          final double length2, final double length3) {
        return halfPerimeter * (halfPerimeter - length1)
                * (halfPerimeter - length2) * (halfPerimeter - length3);
    }

    public List<Double> getMedians() throws TriangleExceptions {
        List<Double> lengths = getLengthsOfEdges();
        double median1 = getInterimMediansValues(lengths.get(0),
                lengths.get(1), lengths.get(2));
        double median2 = getInterimMediansValues(lengths.get(1),
                lengths.get(0), lengths.get(2));
        double median3 = getInterimMediansValues(lengths.get(2),
                lengths.get(0), lengths.get(1));
        return Arrays.asList(median1, median2, median3);
    }

    private double getInterimMediansValues(final double contraryEdge,
                                           final double length1, final double length2) {
        return Math.round(THE_HALF * sqrt(2 * pow(length1, 2)
                + 2 * pow(length2, 2) - pow(contraryEdge, 2)));
    }

    public List<Double> getAltitudes() throws TriangleExceptions {
        List<Double> lengths = getLengthsOfEdges();
        double altitude1 = getInterimAltitudesValues(lengths.get(0));
        double altitude2 = getInterimAltitudesValues(lengths.get(1));
        double altitude3 = getInterimAltitudesValues(lengths.get(2));
        return Arrays.asList(altitude1, altitude2, altitude3);
    }

    private double getInterimAltitudesValues(final double length) throws TriangleExceptions {
        return Math.round(2 * getPerimeter() / length);
    }

    public List<Double> getBisectrices() throws TriangleExceptions {
        List<Double> lengths = getLengthsOfEdges();
        double bisectrix1 = getInterimBisectricesValues(lengths.get(0),
                lengths.get(1), lengths.get(2));
        double bisectrix2 = getInterimBisectricesValues(lengths.get(1),
                lengths.get(0), lengths.get(2));
        double bisectrix3 = getInterimBisectricesValues(lengths.get(2),
                lengths.get(0), lengths.get(1));
        return Arrays.asList(bisectrix1, bisectrix2, bisectrix3);
    }

    private double getInterimBisectricesValues(final double contraryEdge, final double length1,
                                               final double length2) throws TriangleExceptions {
        return Math.round(sqrt(length1 * length2 * getPerimeter()
                * (length1 + length2 - contraryEdge))
                / (length1 + length2));
    }

    public List<Double> getAngles() throws TriangleExceptions {
        List<Double> lengths = getLengthsOfEdges();
        double angle1 = getInterimAnglesValues(lengths.get(0), lengths.get(1), lengths.get(2));
        double angle2 = getInterimAnglesValues(lengths.get(1), lengths.get(0), lengths.get(2));
        double angle3 = getInterimAnglesValues(lengths.get(2), lengths.get(0), lengths.get(1));
        return Arrays.asList(angle1, angle2, angle3);
    }

    private double getInterimAnglesValues(final double contraryEdge, final double length1,
                                               final double length2) throws TriangleExceptions {
        return Math.round(acos((pow(length1, 2) + pow(length2, 2) - pow(contraryEdge, 2))
                / (2 * length1 * length2)));
    }
}
