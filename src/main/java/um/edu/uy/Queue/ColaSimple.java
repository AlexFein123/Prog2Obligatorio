package um.edu.uy.Queue;

import um.edu.uy.Interfaces.MyQueue;

import java.util.EmptyStackException;

public class ColaSimple<T> implements MyQueue<T> {

    private Nodo<T> head;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public ColaSimple() {
        head = null;
    }

    @Override
    public void enqueue(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (head == null) {
            head = nuevo;
        } else {
            Nodo<T> actual = head;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T dato = head.dato;
        head = head.siguiente;
        return dato;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
