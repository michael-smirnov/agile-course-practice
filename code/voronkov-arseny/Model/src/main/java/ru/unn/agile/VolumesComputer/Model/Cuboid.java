package ru.unn.agile.VolumesComputer.Model;

public class Cuboid implements ICubiform {
    private final double width;
    private final double height;
    private final double length;

    public Cuboid(final double width,
                  final double height,
                  final double length) {
        if (width < 0.0 || height < 0.0 || length < 0.0) {
            throw new NegativeParametersException();
        }
        this.width = width;
        this.height = height;
        this.length = length;
    }
    @Override
    public double getHeight() {
        return height;
    }
    @Override
    public double getLength() {
        return length;
    }
    @Override
    public double getWidth() {
        return width;
    }
}
