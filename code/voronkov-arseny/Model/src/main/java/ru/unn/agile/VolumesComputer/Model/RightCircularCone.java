package ru.unn.agile.VolumesComputer.Model;

public class RightCircularCone implements IRightCircularConical {
    private final double radius;
    private final double height;

    public RightCircularCone(final double radius,
                             final double height) {
        if (radius < 0.0 || height < 0.0) {
            throw new NegativeParametersException();
        }
        this.radius = radius;
        this.height = height;
    }
    @Override
    public double getRadius() {
        return radius;
    }
    @Override
    public double getHeight() {
        return height;
    }
}
