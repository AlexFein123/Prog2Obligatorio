package um.edu.uy.interfaces;

import um.edu.uy.exceptions.EmptyQueueException;

public interface MyQueue<T> {
    void enqueue(T element);
    T dequeue() throws EmptyQueueException;
    boolean isEmpty();
}
