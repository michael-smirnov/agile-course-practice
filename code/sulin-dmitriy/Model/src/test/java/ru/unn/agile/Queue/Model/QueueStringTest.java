package ru.unn.agile.Queue.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueStringTest {

    private LabQueue<String> testStrQueue;

    @Before
    public void setUpQueueForTests() {
        testStrQueue = new LabQueue<>();
    }

    @Test
    public void canPushString() {
        final String value1 = "first";
        testStrQueue.push(value1);

        final String value2 = testStrQueue.getHead();

        assertEquals(value1, value2);
    }

    @Test (expected = Exception.class)
    public void cantFindElement() throws Exception {
        testStrQueue.push("first");

        testStrQueue.findElement("second");
    }

    @Test
    public void checkHeadFromEmptyQueue() {
        final String value1 = testStrQueue.getHead();

        assertEquals(null, value1);
    }

    @Test
    public void canConvertToStringArray() {
        String[] testArray = {"First", "Second", "Third"};

        for (int i = 0; i < testArray.length; i++) {
            testStrQueue.push(testArray[i]);
        }
        assertArrayEquals(testArray, testStrQueue.convertToStringArray());
    }
}
