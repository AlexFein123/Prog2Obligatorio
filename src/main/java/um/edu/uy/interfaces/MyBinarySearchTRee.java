package um.edu.uy.interfaces;

import um.edu.uy.tads.ListaEnlazada;

public interface MyBinarySearchTRee<K extends Comparable<K>, T> {

    void insertar (K key, T value);

    void borrar (K key);

    boolean contiene(K key);

    int tamanio();

    ListaEnlazada<T> inOrder();

    ListaEnlazada<T> preOrder();

    ListaEnlazada<T> postOrder();

    ListaEnlazada<T> nivel();

}
