package ru.unn.agile.Queue.Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Random;

public class QueueIntTest {

    private LabQueue<Integer> testIntQueue;
    private final int size = 10;

    @Before
    public void setUpQueueForTests() {
        testIntQueue = new LabQueue<>();
    }
    @Test
    public void canPushOne() {
        final int one = 1;
        testIntQueue.push(one);

        final int headValue = testIntQueue.getHead();

        assertEquals(one, headValue);
    }

    @Test
    public void canPopElement() {
        final int value1 = 1;
        testIntQueue.push(value1);

        final int value2 = testIntQueue.pop();

        assertEquals(value1, value2);
    }

    @Test
    public void canCheckEmptiness() {
        final boolean isQueueEmpty = testIntQueue.isEmpty();

        assertEquals(Boolean.TRUE, isQueueEmpty);
    }

    @Test
    public void canGetSize() {
        for (int i = 0; i < size; i++) {
            testIntQueue.push(i);
        }

        assertEquals(size, testIntQueue.getSize());
    }

    @Test
    public  void popFromEmptyQueueReturnNull() {
        Integer value1 = testIntQueue.pop();

        assertEquals(null, value1);
    }

    @Test
    public void canFindRandomElement() throws Exception {
        for (int i = size; i > 0; i--) {
            testIntQueue.push(i);
        }

        Random rand = new Random();
        final int value1 = 1 + rand.nextInt(size);
        final int result = testIntQueue.findElement(value1);

        assertEquals(size - value1, result);
    }

    @Test
    public void canFindLastElement() throws Exception {
        for (int i = size; i > 0; i--) {
            testIntQueue.push(i);
        }

        final int lastElement = testIntQueue.findElement(1);

        assertEquals(9, lastElement);
    }

    @Test
    public void checkSizeAfterPop() {
        for (int i = size; i > 0; i--) {
            testIntQueue.push(i);
        }

        testIntQueue.pop();
        final int newSize = testIntQueue.getSize();

        assertEquals(size - 1, newSize);
    }

    @Test
    public void checkSizeAfterGetHead() {
        for (int i = size; i > 0; i--) {
            testIntQueue.push(i);
        }

        testIntQueue.getHead();
        final int newSize = testIntQueue.getSize();

        assertEquals(size, newSize);
    }
}
