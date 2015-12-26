package ru.unn.agile.AreaConverter.viewmodel;

public enum LogMessage {
    SCALE_FROM_CHANGED("Scale 'from' changed to: "),
    SCALE_TO_CHANGED("Scale 'to' changed to: "),
    INPUT_AREA_EDITED("Input area changed to: "),
    CONVERTED("Converted. Input area: "),
    FROM_MEASURE(", from measure: "),
    TO_MEASURE(", to measure: "),
    CONVERT_RESULT(", result: ");

    private final String name;

    LogMessage(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
