package ru.unn.agile.BitArray.model;

import ru.unn.agile.BitArray.model.exception.BitArrayDifferentSizeException;

import org.junit.Test;
import org.junit.Before;
import java.util.Arrays;
import static org.junit.Assert.*;

public class BitArrayTest {

    private BitArray bitArray;

    @Before
    public void initializeBitArray() {
        bitArray = new BitArray(5);
    }

    @Test
    public void canCreateBitArrayWithSize() {
        assertNotNull(bitArray);
    }

    @Test(expected = NegativeArraySizeException.class)
    public void failWhenCreateBitArrayWithNegativeSize() {
        bitArray = new BitArray(-1);
    }

    @Test
    public void canGetValueBit() {
        assertNotNull(bitArray.getBit(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void failWhenGetValueBitWithIndexOutOfRangeSize() {
        bitArray.getBit(6);
    }

    @Test()
    public void canSetValueBit() {
        bitArray.setBit(1, true);
        assertTrue(bitArray.getBit(1));
    }

    @Test()
    public void canNotSetNullValueBit() {
        bitArray.setBit(1, null);
        assertNotNull(bitArray.getBit(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void failWhenSetValueBitWithIndexOutOfRangeSize() {
        bitArray.setBit(-1, true);
    }

    @Test
    public void canSetAllValuesInArray() {
        Boolean[] allFalseArray = new Boolean[bitArray.getSize()];
        Arrays.fill(allFalseArray, false);
        bitArray.setAll(false);
        assertTrue(Arrays.equals(bitArray.getBitArray(), allFalseArray));
    }

    @Test
    public void canAndArraysWithSameSize() {
        BitArray array2 = new BitArray(5);
        BitArray resArray;
        Boolean[] allFalseArray = new Boolean[bitArray.getSize()];
        Arrays.fill(allFalseArray, false);
        bitArray.setAll(false);
        array2.setAll(true);
        resArray = bitArray.and(array2);
        assertTrue(Arrays.equals(resArray.getBitArray(), allFalseArray));
    }

    @Test(expected = BitArrayDifferentSizeException.class)
    public void failWhenAndArraysWithDifferentSize() {
        BitArray array2 = new BitArray(6);
        BitArray resArray;
        resArray = bitArray.and(array2);
    }

    @Test
    public void canOrArraysWithSameSize() {
        BitArray array2 = new BitArray(5);
        BitArray resArray;
        Boolean[] allTrueArray = new Boolean[bitArray.getSize()];
        Arrays.fill(allTrueArray, true);
        bitArray.setAll(false);
        array2.setAll(true);
        resArray = bitArray.or(array2);
        assertTrue(Arrays.equals(resArray.getBitArray(), allTrueArray));
    }

    @Test(expected = BitArrayDifferentSizeException.class)
    public void failWhenOrArraysWithDifferentSize() {
        BitArray array2 = new BitArray(6);
        BitArray resArray;
        resArray = bitArray.or(array2);
    }

    @Test
    public void canXorArraysWithSameSize() {
        BitArray array2 = new BitArray(5);
        BitArray resArray;
        Boolean[] allFalseArray = new Boolean[bitArray.getSize()];
        Arrays.fill(allFalseArray, false);
        bitArray.setAll(true);
        array2.setAll(true);
        resArray = bitArray.xor(array2);
        assertTrue(Arrays.equals(resArray.getBitArray(), allFalseArray));
    }

    @Test(expected = BitArrayDifferentSizeException.class)
    public void failWhenXorArraysWithDifferentSize() {
        BitArray array2 = new BitArray(6);
        BitArray resArray;
        resArray = bitArray.xor(array2);
    }

    @Test
    public void canNotArray() {
        BitArray resArray;
        Boolean[] allTrueArray = new Boolean[bitArray.getSize()];
        Arrays.fill(allTrueArray, true);
        bitArray.setAll(false);
        resArray = bitArray.not();
        assertTrue(Arrays.equals(resArray.getBitArray(), allTrueArray));
    }
}
