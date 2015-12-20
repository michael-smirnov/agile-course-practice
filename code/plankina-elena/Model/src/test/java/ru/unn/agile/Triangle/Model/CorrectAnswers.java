package ru.unn.agile.Triangle.Model;

public class CorrectAnswers {
    private final double perimeter;
    private final double length;

    CorrectAnswers(final double perimeter, final double length) {
        this.perimeter = perimeter;
        this.length = length;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public double getLength() {
        return length;
    }
}
