package ru.unn.agile.TriangleViewModel;

public enum ValuesToCalculate {
    PERIMETER("Perimeter"),
    SQUARE("Square"),
    LENGTH_1_2("Length of edge between points 1 and 2"),
    LENGTH_2_3("Length of edge between points 2 and 3"),
    LENGTH_1_3("Length of edge between points 1 and 3"),
    ANGLES("Angles"),
    MEDIANS("Medians"),
    ALTITUDES("Altitudes"),
    BISECTRIECES("Bisectrices");

    private final String name;

    ValuesToCalculate(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ValuesToCalculate getOption(final String valueToCalculate) {
        for (ValuesToCalculate option: ValuesToCalculate.values()) {
            if (option.toString().equals(valueToCalculate)) {
                return option;
            }
        }
        throw new RuntimeException("Unknown option");
    }
}
