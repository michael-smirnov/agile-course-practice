package ru.unn.agile.VolumesComputer.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CuboidTest {
    @Test
    public void isConstructorCorrectIfParametersAreGood() {
        Cuboid cuboid = new Cuboid(5.0, 4.0, 3.0);
        assertEquals(cuboid.getWidth(), 5.0, 0.0);
        assertEquals(cuboid.getHeight(), 4.0, 0.0);
        assertEquals(cuboid.getLength(), 3.0, 0.0);
    }
    @Test (expected = NegativeParametersException.class)
    public void constructorThrowExceptionWhenParametersAreNegative() {
        new Cuboid(5.0, 4.0, -1.0);
    }
}
