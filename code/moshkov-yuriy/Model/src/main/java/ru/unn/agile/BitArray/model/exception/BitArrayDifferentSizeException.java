package ru.unn.agile.BitArray.model.exception;

public class BitArrayDifferentSizeException extends RuntimeException {
    public BitArrayDifferentSizeException(final String message) {
        super(message);
    }
}
