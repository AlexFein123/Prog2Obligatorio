package um.edu.uy.tads;

import um.edu.uy.interfaces.HashTable;

public class HashTableAbierta<K, T> implements HashTable<K, T> {
    private ListaEnlazada<NodoHash<K, T>>[] tabla;
    private int capacidad;
    private int elementos;

    public HashTableAbierta(int capacidad) {
        this.capacidad = capacidad;
        this.elementos = 0;
        this.tabla = new ListaEnlazada[capacidad];

        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada<>();
        }
    }

    @Override
    public void agregar(K clave, T valor) {
        int posicion = funcionHash(clave);
        ListaEnlazada<NodoHash<K, T>> lista = tabla[posicion];

        for (int i = 0; i < lista.tamanio(); i++) {
            try {
                NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                if (nodo.getKey().equals(clave)) {
                    nodo.setValue(valor);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        NodoHash<K, T> nuevo = new NodoHash<>(clave, valor);
        lista.agregarAlFinal(nuevo);
        elementos++;
    }

    @Override
    public T remover(K clave) {
        int posicion = funcionHash(clave);
        ListaEnlazada<NodoHash<K, T>> lista = tabla[posicion];

        for (int i = 0; i < lista.tamanio(); i++) {
            try {
                NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                if (nodo.getKey().equals(clave)) {
                    T valor = nodo.getValue();
                    lista.remover(i);
                    elementos--;
                    return valor;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public T obtener(K clave) {
        int posicion = funcionHash(clave);
        ListaEnlazada<NodoHash<K, T>> lista = tabla[posicion];

        for (int i = 0; i < lista.tamanio(); i++) {
            try {
                NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                if (nodo.getKey().equals(clave)) {
                    return nodo.getValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public int size() {
        return elementos;
    }

    private int funcionHash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }
}
