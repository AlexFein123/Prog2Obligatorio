package um.edu.uy.Interfaces;

import um.edu.uy.Exceptions.EmptyQueueException;

public interface MyQueue<T> {
    void enqueue(T element);
    T dequeue() throws EmptyQueueException;
    boolean isEmpty();
}
