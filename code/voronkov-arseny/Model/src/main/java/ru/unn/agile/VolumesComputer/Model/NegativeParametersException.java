package ru.unn.agile.VolumesComputer.Model;

public class NegativeParametersException extends IllegalArgumentException {
    public NegativeParametersException() {
        super("Parameters must not be negative.");
    }
}
