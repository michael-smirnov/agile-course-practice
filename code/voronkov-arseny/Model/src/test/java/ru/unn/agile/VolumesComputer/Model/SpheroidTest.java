package ru.unn.agile.VolumesComputer.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SpheroidTest {
    @Test
    public void isConstructorCorrectIfParametersAreGood() {
        Spheroid spheroid = new Spheroid(5.0, 4.0);
        assertEquals(spheroid.getSemimajorAxis(), 5.0, 0.0);
        assertEquals(spheroid.getSemiminorAxis(), 4.0, 0.0);
    }
    @Test (expected = NegativeParametersException.class)
    public void constructorThrowExceptionWhenParametersAreNegative() {
        new Spheroid(5.0, -1.0);
    }
}
