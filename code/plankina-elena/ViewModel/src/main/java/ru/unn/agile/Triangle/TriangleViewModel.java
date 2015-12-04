package ru.unn.agile.Triangle;

import ru.unn.agile.Triangle.model.Triangle;

import java.util.ArrayList;
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
    private String valueToCalculate;
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
        isCalculateButtonEnabled = true;
    }

    public void setCoordinate2X(final String point2X) {
        this.point2X = point2X;
        isCalculateButtonEnabled = true;
    }

    public void setCoordinate3X(final String point3X) {
        this.point3X = point3X;
        isCalculateButtonEnabled = true;
    }

    public void setCoordinate1Y(final String point1Y) {
        this.point1Y = point1Y;
        isCalculateButtonEnabled = true;
    }

    public void setCoordinate2Y(final String point2Y) {
        this.point2Y = point2Y;
        isCalculateButtonEnabled = true;
    }

    public void setCoordinate3Y(final String point3Y) {
        this.point3Y = point3Y;
        isCalculateButtonEnabled = true;
    }

    public void setCoordinate1Z(final String point1Z) {
        this.point1Z = point1Z;
        isCalculateButtonEnabled = true;
    }

    public void setCoordinate2Z(final String point2Z) {
        this.point2Z = point2Z;
        isCalculateButtonEnabled = true;
    }

    public void setCoordinate3Z(final String point3Z) {
        this.point3Z = point3Z;
        isCalculateButtonEnabled = true;
    }

    public void setValueToCalculate(final String valueToCalculate) {
        this.valueToCalculate = valueToCalculate;
        isCalculateButtonEnabled = true;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setResult(final String result) {
        this.result = result;
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

    public String getValueToCalculate() {
        return valueToCalculate;
    }

    public String getResult() {
        return result;
    }

    public boolean checkInput() {
        cleanOutput();
        status = Status.READY;
        isCalculateButtonEnabled = false;

        if (!coordinateIsOK(point1X)
                || !coordinateIsOK(point1Y) || !coordinateIsOK(point1Z)) {
            return false;
        }
        if (!coordinateIsOK(point2X)
                || !coordinateIsOK(point2Y) || !coordinateIsOK(point2Z)) {
            return false;
        }
        if (!coordinateIsOK(point3X)
                || !coordinateIsOK(point3Y) || !coordinateIsOK(point3Z)) {
            return false;
        }
        if (!valueToCalculateIsChosen()) {
            return false;
        }

        if (status == Status.READY) {
            isCalculateButtonEnabled = true;
            return true;
        }
        return false;
    }

    private boolean coordinateIsOK(final String coordinate) {
        if (coordinate.isEmpty()) {
            status = Status.WAITING;
        } else {
            try {
                parseDouble(coordinate);
            }  catch (Exception e) {
                status = Status.BAD_FORMAT;
                return false;
            }
        }
        return true;
    }

    private boolean valueToCalculateIsChosen() {
        if (valueToCalculate.isEmpty()) {
            status = Status.WAITING;
            return false;
        }
        return true;
    }

    private void cleanOutput() {
        result = "";
    }

    public void compute() throws Exception {
        List<String> arrayOfMedians = new ArrayList<>();
        List<String> arrayOfAngles = new ArrayList<>();
        if (checkInput()) {
            Triangle triangle = setTriangle();
                if (valueToCalculate.equals(ValuesToCalculate.PERIMETER)) {
                    result = Double.toString(triangle.getPerimeter());
                } else if (valueToCalculate.equals(ValuesToCalculate.SQUARE)) {
                    result = Double.toString(triangle.getSquare());
                } else if (valueToCalculate.equals(ValuesToCalculate.LENGTH_1_2)) {
                    result = Double.toString(triangle.getLength(triangle.getCoordinatesOfPoint1(),
                            triangle.getCoordinatesOfPoint2()));
                } else if (valueToCalculate.equals(ValuesToCalculate.LENGTH_2_3)) {
                    result = Double.toString(triangle.getLength(triangle.getCoordinatesOfPoint2(),
                            triangle.getCoordinatesOfPoint3()));
                } else if (valueToCalculate.equals(ValuesToCalculate.LENGTH_1_3)) {
                    result = Double.toString(triangle.getLength(triangle.getCoordinatesOfPoint1(),
                            triangle.getCoordinatesOfPoint3()));
                } else if (valueToCalculate.equals(ValuesToCalculate.MEDIANS)) {
                    List<Double> medians = triangle.getMedians();
                    for (Double m : medians) {
                        arrayOfMedians.add(m.toString());
                    }
                    result = arrayOfMedians.toString();
                } else if (valueToCalculate.equals(ValuesToCalculate.ANGLES)) {
                    List<Double> angles = triangle.getAngles();
                    for (Double a : angles) {
                        arrayOfAngles.add(a.toString());
                    }
                    result = arrayOfAngles.toString();
                } else {
                    throw new Exception("Incorrect value!");
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
        return new Triangle(coordinatesOfPoint1,
                coordinatesOfPoint2, coordinatesOfPoint3, DIMENSION);
    }
}
