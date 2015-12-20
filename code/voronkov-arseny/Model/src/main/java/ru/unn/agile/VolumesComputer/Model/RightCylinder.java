package ru.unn.agile.VolumesComputer.Model;

public class RightCylinder implements IRightCylindrical {
    private final double semimajorAxis;
    private final double semiminorAxis;
    private final double height;

    public RightCylinder(final double semimajorAxis,
                         final double semiminorAxis,
                         final double height) {
        if (semimajorAxis < 0.0 || semiminorAxis < 0.0 || height < 0.0) {
            throw new NegativeParametersException();
        }
        this.semimajorAxis = semimajorAxis;
        this.semiminorAxis = semiminorAxis;
        this.height = height;
    }
    @Override
    public double getSemimajorAxis() {
        return semimajorAxis;
    }
    @Override
    public double getSemiminorAxis() {
        return semiminorAxis;
    }
    @Override
    public double getHeight() {
        return height;
    }
}
