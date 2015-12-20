package ru.unn.agile.WeightConverter.Model;

public class WeightConverter {

    public Weight convert(final Weight weight, final WeightUnit unitOutput) {

        Weight outputWeight = new Weight(0.0, WeightUnit.KILOGRAM);
        double valueInputWeight = weight.getValue();

        if (valueInputWeight >= 0
                && valueInputWeight <= Double.MAX_VALUE) {

            double valueOutputWeight = unitOutput.getMultiplier()
                    / weight.getUnit().getMultiplier() * valueInputWeight;
            outputWeight.setValue(valueOutputWeight);
            outputWeight.setUnit(unitOutput);
        } else {
            throw new IllegalArgumentException();
        }
        return outputWeight;
    }

    public String convert(final String value,
                          final WeightUnit unitInput, final WeightUnit unitOutput) {
        Weight weightInput = new Weight(Double.parseDouble(value), unitInput);
        Weight weightOutput = convert(weightInput, unitOutput);
        double val = weightOutput.getValue();
        return Double.toString(val);
    }
}
