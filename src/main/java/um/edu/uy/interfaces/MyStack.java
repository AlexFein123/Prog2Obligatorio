package um.edu.uy.interfaces;

import java.util.EmptyStackException;

public interface MyStack<T> {
    void pop() throws EmptyStackException;
    T top() throws EmptyStackException;
    void push(T element);
    boolean isEmpty();
    void makeEmpty();
}