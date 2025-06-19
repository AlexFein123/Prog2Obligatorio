package um.edu.uy.tads;

import um.edu.uy.interfaces.HashTable;
import um.edu.uy.exceptions.FueraDeRango;

public class HashTableCerrada<K, T> implements HashTable<K, T> {

    private ListaEnlazada<NodoHash<K, T>>[] entryArray;
    private int size;

    public HashTableCerrada(int capacidadInicial) {
        int primeCapacity = getNextPrimeNumber(capacidadInicial - 1);
        this.entryArray = new ListaEnlazada[primeCapacity];
        this.size = 0;
    }

    @Override
    public void agregar(K key, T value) {
        if (((float) (this.size + 1)) / entryArray.length > 0.75f) {
            reHashing();
        }

        int posicion = Math.abs(key.hashCode()) % entryArray.length;
        ListaEnlazada<NodoHash<K, T>> lista = entryArray[posicion];

        if (lista == null) {
            lista = new ListaEnlazada<>();
            entryArray[posicion] = lista;
        }

        try {
            for (int i = 0; i < lista.tamanio(); i++) {
                NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                if (nodo.getKey().equals(key)) {
                    nodo.setValue(value);
                    return;
                }
            }
        } catch (FueraDeRango e) {
            e.printStackTrace();
        }

        NodoHash<K, T> nuevo = new NodoHash<>(key, value);
        lista.agregarOrdenado(nuevo);
        size++;
    }

    @Override
    public T obtener(K key) {
        int posicion = Math.abs(key.hashCode()) % entryArray.length;
        ListaEnlazada<NodoHash<K, T>> lista = entryArray[posicion];

        if (lista != null) {
            try {
                for (int i = 0; i < lista.tamanio(); i++) {
                    NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                    if (nodo.getKey().equals(key)) {
                        return nodo.getValue();
                    }
                }
            } catch (FueraDeRango e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public T remover(K key) {
        int posicion = Math.abs(key.hashCode()) % entryArray.length;
        ListaEnlazada<NodoHash<K, T>> lista = entryArray[posicion];

        if (lista != null) {
            try {
                for (int i = 0; i < lista.tamanio(); i++) {
                    NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                    if (nodo.getKey().equals(key)) {
                        lista.remover(i);
                        size--;
                        return nodo.getValue();
                    }
                }
            } catch (FueraDeRango e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    public ListaEnlazada<T> getValues() {
        ListaEnlazada<T> valores = new ListaEnlazada<>();

        for (ListaEnlazada<NodoHash<K, T>> lista : entryArray) {
            if (lista != null) {
                try {
                    for (int i = 0; i < lista.tamanio(); i++) {
                        NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                        valores.agregarOrdenado(nodo.getValue());
                    }
                } catch (FueraDeRango e) {
                    e.printStackTrace();
                }
            }
        }

        return valores;
    }


    private void reHashing() {
        int nuevaCapacidad = getNextPrimeNumber(entryArray.length * 2);
        ListaEnlazada<NodoHash<K, T>>[] viejaTabla = entryArray;
        entryArray = new ListaEnlazada[nuevaCapacidad];
        size = 0;

        for (ListaEnlazada<NodoHash<K, T>> lista : viejaTabla) {
            if (lista != null) {
                try {
                    for (int i = 0; i < lista.tamanio(); i++) {
                        NodoHash<K, T> nodo = lista.obtenervalorposicion(i);
                        agregar(nodo.getKey(), nodo.getValue());
                    }
                } catch (FueraDeRango e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getNextPrimeNumber(int num) {
        int siguiente = num + 1;
        while (!esPrimo(siguiente)) {
            siguiente++;
        }
        return siguiente;
    }

    private boolean esPrimo(int numero) {
        if (numero < 2) return false;
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }
}
