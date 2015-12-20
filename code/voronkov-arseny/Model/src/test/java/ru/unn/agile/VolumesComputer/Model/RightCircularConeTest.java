package ru.unn.agile.VolumesComputer.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RightCircularConeTest {
    @Test
    public void isConstructorCorrectIfParametersAreGood() {
        RightCircularCone rightCircularCone = new RightCircularCone(5.0, 4.0);
        assertEquals(rightCircularCone.getRadius(), 5.0, 0.0);
        assertEquals(rightCircularCone.getHeight(), 4.0, 0.0);
    }
    @Test (expected = NegativeParametersException.class)
    public void constructorThrowExceptionWhenParametersAreNegative() {
        new RightCircularCone(5.0, -1.0);
    }
}
