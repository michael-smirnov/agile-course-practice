package ru.unn.agile.VolumesComputer.Model;

public final class VolumesComputer {
    public static double solve(final ICubiform cuboid) {
        return cuboid.getWidth() * cuboid.getHeight() * cuboid.getLength();
    }
    public static double solve(final ISpheroid spheroid) {
        final double four = 4.0;
        final double three = 3.0;
        return four / three * Math.PI * spheroid.getSemimajorAxis()
                * spheroid.getSemiminorAxis() * spheroid.getSemiminorAxis();
    }
    public static double solve(final IRightCylindrical rightCylinder) {
        return Math.PI * rightCylinder.getSemimajorAxis()
                * rightCylinder.getSemiminorAxis() * rightCylinder.getHeight();
    }
    public static double solve(final IRightCircularConical rightCircularCone) {
        final double three = 3.0;
        return Math.PI / three * rightCircularCone.getRadius()
                * rightCircularCone.getRadius()
                * rightCircularCone.getHeight();
    }
    private VolumesComputer() {
    }
}
