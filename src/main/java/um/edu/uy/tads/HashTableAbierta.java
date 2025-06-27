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
                if (nodo.getClave().equals(clave)) {
                    nodo.setValor(valor);
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
    public NodoHash borrar(K clave) {
        int posicion = funcionHash(clave);
        ListaEnlazada<NodoHash<K, T>> lista = tabla[posicion];

        for (int i = 0; i < lista.tamanio(); i++) {
            try {
                NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                if (nodo.getClave().equals(clave)) {
                    NodoHash<K,T> valor = (NodoHash<K, T>) nodo.getValor();
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
                if (nodo.getClave().equals(clave)) {
                    return nodo.getValor();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public int tamanio() {
        return elementos;
    }

    private int funcionHash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    public ListaEnlazada<NodoHash<K, T>>[] getTabla() {
        return tabla;
    }

    public void setTabla(ListaEnlazada<NodoHash<K, T>>[] tabla) {
        this.tabla = tabla;
    }

    public int getCapacidad() {
        return capacidad;
    }
}
