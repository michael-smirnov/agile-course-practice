package test.java.ru.unn.agile.LongArithmetic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.ru.unn.agile.LongArithmetic.Matrix;

public class WhenWorkWithMatrix {

    @Test
    public void Multiply_Matrix_2x2_And_Matrix_2x2() {
        Matrix firstMatrix = new Matrix(2, 2);
        firstMatrix.setElement(0, 0, 1);
        firstMatrix.setElement(0, 1, 1);
        firstMatrix.setElement(1, 0, 2);
        firstMatrix.setElement(1, 1, 1);
        Matrix secondMatrix = new Matrix(2, 2);
        secondMatrix.setElement(0, 0, 2);
        secondMatrix.setElement(0, 1, 2);
        secondMatrix.setElement(1, 0, 1);
        secondMatrix.setElement(1, 1, 2);
        Matrix gageMatrix = new Matrix(2, 2);
        gageMatrix.setElement(0, 0, 3);
        gageMatrix.setElement(0, 1, 4);
        gageMatrix.setElement(1, 0, 5);
        gageMatrix.setElement(1, 1, 6);

        Matrix resultMatrix = firstMatrix.multiply(secondMatrix);

        boolean resultCompare = gageMatrix.isEqual(resultMatrix);
        assertEquals(true, resultCompare);
    }

    @Test
    public void Multiply_Some_Matrix_5x3_And_E_Matrix_3x3() {
        Matrix someMatrix = generateMatrix(5, 3);
        Matrix eMatrix = new Matrix(3, 3);
        eMatrix.setElement(0, 0, 1);
        eMatrix.setElement(1, 1, 1);
        eMatrix.setElement(2, 2, 1);

        Matrix resultMatrix = someMatrix.multiply(eMatrix);

        boolean resultCompare = someMatrix.isEqual(resultMatrix);
        assertEquals(true, resultCompare);
    }

    @Test
    public void Multiply_Some_Matrix_5x3_And_Some_Matrix_3x5() {
        Matrix firstMatrix = generateMatrix(5, 3);
        Matrix secondMatrix = generateMatrix(3, 5);

        Matrix resultMatrix = firstMatrix.multiply(secondMatrix);

        boolean success = true;
        if (resultMatrix.getHeight() != 5 || resultMatrix.getWidth() != 5) {
            success = false;
        }
        assertEquals(true, success);
    }

    @Test
    public void Compare_Equal_Matrixs_4x3() {
        Matrix firstMatrix = generateMatrix(4, 3);
        Matrix secondMatrix = new Matrix(firstMatrix);

        boolean isEqual = firstMatrix.isEqual(secondMatrix);
        assertEquals(true, isEqual);
    }

    @Test
    public void Compare_Some_Matrix_4x3_And_Some_Matrix_3x3() {
        Matrix firstMatrix = generateMatrix(4, 3);
        Matrix secondMatrix = generateMatrix(3, 3);

        boolean isEqual = firstMatrix.isEqual(secondMatrix);
        assertEquals(false, isEqual);
    }

    @Test
    public void Compare_Matrix_2x2_And_E_Matrix_2x2() {
        Matrix matrix = new Matrix(2, 2);
        matrix.setElement(0, 0, 1);
        matrix.setElement(0, 1, 1);
        matrix.setElement(1, 0, 2);
        matrix.setElement(1, 1, 1);
        Matrix eMatrix = new Matrix(2, 2);
        eMatrix.setElement(0, 0, 1);
        eMatrix.setElement(1, 1, 1);

        boolean isEqual = matrix.isEqual(eMatrix);
        assertEquals(false, isEqual);
    }

    private Matrix generateMatrix(final int height, final int width) {
        Matrix resultMatrix = new Matrix(height, width);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int value = (int) (Math.random() * 10000);
                resultMatrix.setElement(i, j, value);
            }
        }

        return resultMatrix;
    }
}
