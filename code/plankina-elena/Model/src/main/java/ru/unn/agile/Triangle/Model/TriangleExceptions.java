package ru.unn.agile.Triangle.Model;

public class TriangleExceptions extends Exception {
    public static final String DIFFERENT_DIMENSIONS = "Points have different dimensions!";
    public static final String DEGENERATE_TRIANGLE = "Triangle is degenerate!";
    public static final String LENGTH_OVERFLOW = "Overflow when counting length!";
    public static final String PERIMETER_OVERFLOW = "Overflow when counting perimeter!";
    public static final String SQUARE_OVERFLOW = "Overflow when counting square!";

    private final String name;

    public TriangleExceptions(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
