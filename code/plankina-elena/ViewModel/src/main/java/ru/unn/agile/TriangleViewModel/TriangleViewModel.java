package ru.unn.agile.TriangleViewModel;

import ru.unn.agile.Triangle.model.Triangle;
import ru.unn.agile.Triangle.model.TriangleExceptions;

import java.util.Arrays;
import java.util.List;
import static java.lang.Double.parseDouble;

public class TriangleViewModel {
    private boolean isCalculateButtonEnabled;
    private String status;
    private String point1X;
    private String point1Y;
    private String point1Z;
    private String point2X;
    private String point2Y;
    private String point2Z;
    private String point3X;
    private String point3Y;
    private String point3Z;
    private ValuesToCalculate valueToCalculate;
    private String result;
    private static final int DIMENSION = 3;

    public TriangleViewModel() {
        status = Status.WAITING;
        isCalculateButtonEnabled = false;
        point1X = "";
        point1Y = "";
        point1Z = "";
        point2X = "";
        point2Y = "";
        point2Z = "";
        point3X = "";
        point3Y = "";
        point3Z = "";
        valueToCalculate = ValuesToCalculate.MEDIANS;
        result = "0.0";
    }

    public boolean isCalculateButtonEnabled() {
        return isCalculateButtonEnabled;
    }

    public void setCoordinate1X(final String point1X) {
        this.point1X = point1X;
    }

    public void setCoordinate2X(final String point2X) {
        this.point2X = point2X;
    }

    public void setCoordinate3X(final String point3X) {
        this.point3X = point3X;
    }

    public void setCoordinate1Y(final String point1Y) {
        this.point1Y = point1Y;
    }

    public void setCoordinate2Y(final String point2Y) {
        this.point2Y = point2Y;
    }

    public void setCoordinate3Y(final String point3Y) {
        this.point3Y = point3Y;
    }

    public void setCoordinate1Z(final String point1Z) {
        this.point1Z = point1Z;
    }

    public void setCoordinate2Z(final String point2Z) {
        this.point2Z = point2Z;
    }

    public void setCoordinate3Z(final String point3Z) {
        this.point3Z = point3Z;
    }

    public void setValueToCalculate(final ValuesToCalculate valueToCalculate) {
        this.valueToCalculate = valueToCalculate;
    }

    public String getStatus() {
        return status;
    }

    public String getCoordinate1X() {
        return point1X;
    }

    public String getCoordinate2X() {
        return point2X;
    }

    public String getCoordinate3X() {
        return point3X;
    }

    public String getCoordinate1Y() {
        return point1Y;
    }

    public String getCoordinate2Y() {
        return point2Y;
    }

    public String getCoordinate3Y() {
        return point3Y;
    }

    public String getCoordinate1Z() {
        return point1Z;
    }

    public String getCoordinate2Z() {
        return point2Z;
    }

    public String getCoordinate3Z() {
        return point3Z;
    }

    public ValuesToCalculate getValueToCalculate() {
        return valueToCalculate;
    }

    public String getResult() {
        return result;
    }

    public boolean checkInput() {
        if (areAllFieldsOK()) {
            status = Status.READY;
            isCalculateButtonEnabled = true;
            return  true;
        }
        return false;
    }

    private boolean areAllFieldsOK() {
        if (!isCoordinateOK(point1X)
                || !isCoordinateOK(point1Y) || !isCoordinateOK(point1Z)) {
            return false;
        }
        if (!isCoordinateOK(point2X)
                || !isCoordinateOK(point2Y) || !isCoordinateOK(point2Z)) {
            return false;
        }
        if (!isCoordinateOK(point3X)
                || !isCoordinateOK(point3Y) || !isCoordinateOK(point3Z)) {
            return false;
        }
        if (!valueToCalculateIsChosen()) {
            return false;
        }
        return true;
    }

    private boolean isCoordinateOK(final String coordinate) {
        if (coordinate.isEmpty()) {
            status = Status.WAITING;
            isCalculateButtonEnabled = false;
            return false;
        } else {
            try {
                parseDouble(coordinate);
                return true;
            }  catch (Exception e) {
                status = Status.BAD_FORMAT;
                return false;
            }
        }
    }

    private boolean valueToCalculateIsChosen() {
        if (valueToCalculate.toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private void cleanOutput() {
        result = "0.0";
    }

    public void compute() throws Exception {
        double resultDouble;
        if (checkInput()) {
            Triangle triangle = setTriangle();
            cleanOutput();
            switch (valueToCalculate) {
                case PERIMETER:
                    try {
                        resultDouble = triangle.getPerimeter();
                        result = String.format("%.3f", resultDouble);
                    } catch (Exception e) {
                        status = TriangleExceptions.PERIMETER_OVERFLOW.toString();
                    }
                    break;
                case SQUARE:
                    try {
                        resultDouble = triangle.getSquare();
                        result = String.format("%.3f", resultDouble);
                    } catch (Exception e) {
                        status = TriangleExceptions.SQUARE_OVERFLOW.toString();
                    }
                    break;
                case LENGTH_1_2:
                    try {
                        resultDouble = triangle.getLength(triangle.getCoordinatesOfPoint1(),
                                triangle.getCoordinatesOfPoint2());
                        result = String.format("%.3f", resultDouble);
                    } catch (Exception e) {
                        status = TriangleExceptions.LENGTH_OVERFLOW.toString();
                    }
                    break;
                case LENGTH_2_3:
                    try {
                        resultDouble = triangle.getLength(triangle.getCoordinatesOfPoint2(),
                                triangle.getCoordinatesOfPoint3());
                        result = String.format("%.3f", resultDouble);
                    } catch (Exception e) {
                        status = TriangleExceptions.LENGTH_OVERFLOW.toString();
                    }
                    break;
                case LENGTH_1_3:
                    try {
                        resultDouble = triangle.getLength(triangle.getCoordinatesOfPoint1(),
                                triangle.getCoordinatesOfPoint3());
                        result = String.format("%.3f", resultDouble);
                    } catch (Exception e) {
                        status = TriangleExceptions.LENGTH_OVERFLOW.toString();
                    }
                    break;
                case MEDIANS:
                    List<Double> medians = triangle.getMedians();
                    result = medians.toString();
                    break;
                case ALTITUDES:
                    List<Double> altitudes = triangle.getAltitudes();
                    result = altitudes.toString();
                    break;
                case BISECTRIECES:
                    List<Double> bisectrieces = triangle.getBisectrices();
                    result = bisectrieces.toString();
                    break;
                case ANGLES:
                    List<Double> angles = triangle.getAngles();
                    result = angles.toString();
                    break;
                default:
                        break;
            }
            status = Status.SUCCESS;
        }
    }

    public Triangle setTriangle() throws Exception {
        List<Double> coordinatesOfPoint1 = Arrays.asList(Double.parseDouble(point1X),
                Double.parseDouble(point1Y), Double.parseDouble(point1Z));
        List<Double> coordinatesOfPoint2 = Arrays.asList(Double.parseDouble(point2X),
                Double.parseDouble(point2Y), Double.parseDouble(point2Z));
        List<Double> coordinatesOfPoint3 = Arrays.asList(Double.parseDouble(point3X),
                Double.parseDouble(point3Y), Double.parseDouble(point3Z));
        try {
            return new Triangle(coordinatesOfPoint1,
                    coordinatesOfPoint2, coordinatesOfPoint3, DIMENSION);
        } catch (Exception e) {
            if (!Triangle.hasEqualDimensions(coordinatesOfPoint1, coordinatesOfPoint2,
                    coordinatesOfPoint3, DIMENSION)) {
                status = TriangleExceptions.DIFFERENT_DIMENSIONS.toString();
            }
            if (!Triangle.isPossibleToBuildNondegenerateTriangle(coordinatesOfPoint1,
                    coordinatesOfPoint2, coordinatesOfPoint3)) {
                status = TriangleExceptions.DEGENERATE_TRIANGLE.toString();
            }
            return null;
        }
    }
}
