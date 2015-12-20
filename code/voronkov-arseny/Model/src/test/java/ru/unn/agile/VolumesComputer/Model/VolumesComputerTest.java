package ru.unn.agile.VolumesComputer.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class VolumesComputerTest {
    private static final double EPSILON = 0.0000001;

    @Test
    public void cuboidGoodParams() {
        final double w = 5.0, h = 4.0, l = 3.0;
        final double goodVolume = 60.0;
        final double volume = VolumesComputer.solve(new Cuboid(w, h, l));
        assertEquals(volume, goodVolume, EPSILON);
    }
    @Test
    public void cuboidZeroParams() {
        final double w = 5.0, h = 4.0, l = 0.0;
        final double goodVolume = 0.0;
        final double volume = VolumesComputer.solve(new Cuboid(w, h, l));
        assertEquals(volume, goodVolume, EPSILON);
    }
    @Test
    public void cuboidLargeParams() {
        final double w = Double.MAX_VALUE, h = Double.MAX_VALUE, l = 1.0;
        final double goodVolume = Double.POSITIVE_INFINITY;
        final double volume = VolumesComputer.solve(new Cuboid(w, h, l));
        assertEquals(volume, goodVolume, 0.0);
    }

    @Test
    public void spheroidGoodParams() {
        final double a = 5.0, b = 4.0;
        final double goodVolume = 335.10321638291127876934862754981;
        final double volume = VolumesComputer.solve(new Spheroid(a, b));
        assertEquals(volume, goodVolume, EPSILON);
    }
    @Test
    public void spheroidZeroParams() {
        final double a = 5.0, b = 0.0;
        final double goodVolume = 0.0;
        final double volume = VolumesComputer.solve(new Spheroid(a, b));
        assertEquals(volume, goodVolume, EPSILON);
    }
    @Test
    public void spheroidLargeParams() {
        final double a = Double.MAX_VALUE, b = Double.MAX_VALUE;
        final double goodVolume = Double.POSITIVE_INFINITY;
        final double volume = VolumesComputer.solve(new Spheroid(a, b));
        assertEquals(volume, goodVolume, 0.0);
    }

    @Test
    public void rightCylinderGoodParams() {
        final double a = 5.0, b = 4.0, h = 3.0;
        final double goodVolume = 188.49555921538759430775860299677;
        final double volume = VolumesComputer.solve(
                new RightCylinder(a, b, h));
        assertEquals(volume, goodVolume, EPSILON);
    }
    @Test
    public void rightCylinderZeroParams() {
        final double a = 5.0, b = 4.0, h = 0.0;
        final double goodVolume = 0.0;
        final double volume = VolumesComputer.solve(
                new RightCylinder(a, b, h));
        assertEquals(volume, goodVolume, EPSILON);
    }
    @Test
    public void rightCylinderLargeParams() {
        final double a = Double.MAX_VALUE, b = Double.MAX_VALUE, h = 1.0;
        final double goodVolume = Double.POSITIVE_INFINITY;
        final double volume = VolumesComputer.solve(
                new RightCylinder(a, b, h));
        assertEquals(volume, goodVolume, 0.0);
    }

    @Test
    public void rightCircularConeGoodParams() {
        final double r = 5.0, h = 4.0;
        final double goodVolume = 104.71975511965977461542144610932;
        final double volume = VolumesComputer.solve(
                new RightCircularCone(r, h));
        assertEquals(volume, goodVolume, EPSILON);
    }
    @Test
    public void rightCircularConeZeroParams() {
        final double r = 5.0, h = 0.0;
        final double goodVolume = 0.0;
        final double volume = VolumesComputer.solve(
                new RightCircularCone(r, h));
        assertEquals(volume, goodVolume, EPSILON);
    }
    @Test
    public void rightCircularConeLargeParams() {
        final double r = Double.MAX_VALUE, h = Double.MAX_VALUE;
        final double goodVolume = Double.POSITIVE_INFINITY;
        final double volume = VolumesComputer.solve(
                new RightCircularCone(r, h));
        assertEquals(volume, goodVolume, 0.0);
    }

    @Test (expected = NullPointerException.class)
    public void cuboidNullParameter() {
        VolumesComputer.solve((ICubiform) null);
    }
    @Test (expected = NullPointerException.class)
    public void spheroidNullParameter() {
        VolumesComputer.solve((ISpheroid) null);
    }
    @Test (expected = NullPointerException.class)
    public void rightCylinderNullParameter() {
        VolumesComputer.solve((IRightCylindrical) null);
    }
    @Test (expected = NullPointerException.class)
    public void rightCircularConeNullParameter() {
        VolumesComputer.solve((IRightCircularConical) null);
    }
}
