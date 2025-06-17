package um.edu.uy.tads;

import um.edu.uy.interfaces.MyStack;

import java.util.EmptyStackException;


public class Stack<T> implements MyStack<T> {

    private Nodo<T> top;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato, Nodo<T> siguiente) {
            this.dato = dato;
            this.siguiente = siguiente;
        }
    }

    public Stack() {
        top = null;
    }

    @Override
    public void pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        top = top.siguiente;
    }

    @Override
    public T top() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.dato;
    }

    @Override
    public void push(T elemento) {
        top = new Nodo<>(elemento, top);
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public void makeEmpty() {
        top = null;
    }
}

