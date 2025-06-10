package um.edu.uy.Stack;

import um.edu.uy.Interfaces.MyStack;

import java.util.EmptyStackException;


public class StackArray<T> implements MyStack<T> {

    private T[] arreglo;
    private int top;
    private final int TamMax = 100;

    @SuppressWarnings("unchecked")
    public StackArray() {
        arreglo = (T[]) new Object[TamMax];
        top = -1;
    }

    @Override
    public void push(T elemento) {
        if (top == TamMax - 1) {
            throw new RuntimeException("La pila está llena");
        }
        arreglo[++top] = elemento;
    }

    @Override
    public void pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        arreglo[top--] = null;
    }

    @Override
    public T top() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return arreglo[top];
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public void makeEmpty() {
        for (int i = 0; i <= top; i++) {
            arreglo[i] = null;
        }
        top = -1;
    }
}

