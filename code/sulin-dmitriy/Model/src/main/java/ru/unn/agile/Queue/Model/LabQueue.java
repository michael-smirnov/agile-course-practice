package ru.unn.agile.Queue.Model;

import java.util.List;
import java.util.LinkedList;

public class LabQueue<T> {

    private final List<T> queue = new LinkedList<T>();
    public Boolean isEmpty() {
        return queue.isEmpty();
    }

    public int getSize() {
        return  queue.size();
    }

    public Boolean push(final T value) {
        return queue.add(value);
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T result = queue.get(0);
        queue.remove(0);
        return result;
    }

    public T getHead() {
        if (isEmpty()) {
            return null;
        }
        return queue.get(0);
    }

    public int findElement(final T element) throws Exception {
        for (int i = 0; i <= queue.size() - 1; i++) {
            T temp = queue.get(i);
            if (temp.equals(element)) {
                return i;
            }
        }
        throw new Exception("element not found");
    }

    public String[] convertToStringArray() {
        String[] result = new String[getSize()];
        for (int i = 0; i <= queue.size() - 1; i++) {
            result[i] = queue.get(i).toString();
        }
        return result;
    }
}
