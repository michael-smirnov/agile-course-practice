package ru.unn.agile.VolumesComputer.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class NegativeParametersExceptionTest {
    @Test
    public void construct() {
        NegativeParametersException e = new NegativeParametersException();
        assertEquals(e.getMessage(), "Parameters must not be negative.");
    }
}
