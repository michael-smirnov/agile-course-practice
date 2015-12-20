package ru.unn.agile.VolumesComputer.Model;

public class Spheroid implements ISpheroid {
    private final double semimajorAxis;
    private final double semiminorAxis;

    public Spheroid(final double semimajorAxis,
                    final double semiminorAxis) {
        if (semimajorAxis < 0.0 || semiminorAxis < 0.0) {
            throw new NegativeParametersException();
        }
        this.semimajorAxis = semimajorAxis;
        this.semiminorAxis = semiminorAxis;
    }
    @Override
    public double getSemimajorAxis() {
        return semimajorAxis;
    }
    @Override
    public double getSemiminorAxis() {
        return semiminorAxis;
    }
}
