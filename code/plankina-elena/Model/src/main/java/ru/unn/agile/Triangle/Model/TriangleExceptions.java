package ru.unn.agile.Triangle.model;

public enum TriangleExceptions {
    DIFFERENT_DIMENSIONS("Points have different dimensions!"),
    DEGENERATE_TRIANGLE("Triangle is degenerate!"),
    LENGTH_OVERFLOW("Overflow when counting length!"),
    PERIMETER_OVERFLOW("Overflow when counting perimeter!"),
    SQUARE_OVERFLOW("Overflow when counting square!");

    private final String name;

    TriangleExceptions(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
