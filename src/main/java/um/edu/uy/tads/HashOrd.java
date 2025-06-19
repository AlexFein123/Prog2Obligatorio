package um.edu.uy.tads;


import um.edu.uy.exceptions.ElementoYaExistenteException;
import um.edu.uy.interfaces.HashTable;

public class HashOrd<K,T> implements HashTable<K,T> {

    private NodoHash<K,T>[] tabla;
    private int size;
    private int elementos;


    public HashOrd() {
        this.size = 11;
        this.tabla = new NodoHash[size];
        this.elementos = 0;
    }


    private int posicion(K clave) {
        int lugar = clave.hashCode() % size;
        return lugar;
    }



    @Override
    public void insertar(K clave, T valor) throws ElementoYaExistenteException {
        if (pertenece(clave)){
            throw new ElementoYaExistenteException();
        }

        int indice=posicion(clave);
        NodoHash<K,T> actual =tabla[indice];
        NodoHash<K,T> anterior=null;

        while (actual != null && compareKeys(actual.getClave(), clave) < 0 ) {
            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (pertenece(clave)){
            throw new ElementoYaExistenteException();
        }

        NodoHash<K,T> nuevoNodo = new NodoHash(clave, valor);
        nuevoNodo.setSiguiente(actual);

        if (anterior == null) {
            tabla[indice] = nuevoNodo;
        } else {
            anterior.setSiguiente(nuevoNodo);
        }
        elementos++;
    }

    @Override
    public boolean pertenece(K clave) {
        int indice= posicion(clave) ;
        NodoHash<K,T> actual= tabla[indice];
        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                return true;
            }
            if (compareKeys(actual.getClave(), clave) > 0) {
                break;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    @Override
    public void borrar(K clave) {
        int indice = posicion(clave);
        NodoHash<K,T> actual = tabla[indice];
        NodoHash<K,T> anterior = null;


        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                if (anterior == null) {
                    tabla[indice] = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                elementos--;
                return;
            }
            // Si está ordenado y la clave actual es mayor, no está
            if (compareKeys(actual.getClave(), clave) > 0) {
                break;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
    }

    public T obtener (K clave) {
        int indice = posicion(clave);
        NodoHash<K,T> actual = tabla[indice];

        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                return actual.getValor();
            }
            if (compareKeys(actual.getClave(), clave) > 0) {
                break;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    private int compareKeys(K key1, K key2) {
        if (key1 instanceof Comparable) {
            return ((Comparable<K>) key1).compareTo(key2);
        }
        // Si no es comparable, usar hashCode para ordenar
        return Integer.compare(key1.hashCode(), key2.hashCode());
    }
    public T[] toArray() {
        Object[] array = new Object[elementos];
        int index = 0;
        for (int i = 0; i < tabla.length; i++) {
            NodoHash<K,T> actual = tabla[i];
            while (actual != null) {
                array[index++] = actual.getValor();
                actual = actual.getSiguiente();
            }
        }
        return (T[]) array;
    }
    }
}

