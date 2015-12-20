package ru.unn.agile.WeightConverter.Model;

import java.util.Objects;

public class Weight {
    private double value;
    private WeightUnit unit;

    Weight(final double value, final WeightUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }

    @Override
    public boolean equals(final Object object) {
        Weight length = (Weight) object;
        return length.value == value
                && length.unit == unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    public void setValue(final double value) {
        this.value = value;
    }

    public void setUnit(final WeightUnit unit) {
        this.unit = unit;
    }

}
