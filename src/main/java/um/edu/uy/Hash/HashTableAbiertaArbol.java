package um.edu.uy.Hash;

import um.edu.uy.Exceptions.ElementoYaExistenteException;
import um.edu.uy.Interfaces.HashTable;

import java.util.TreeMap;

public class HashTableAbiertaArbol implements HashTable {
    private TreeMap<String, Object>[] tabla;
    private int tamaño;

    @SuppressWarnings("unchecked")
    public HashTableAbiertaArbol(int capacidad) {
        this.tamaño = capacidad;
        tabla = new TreeMap[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new TreeMap<>();
        }
    }

    private int hash(String clave) {
        return Math.abs(clave.hashCode()) % tamaño;
    }

    @Override
    public void insertar(String clave, Object valor) throws ElementoYaExistenteException {
        int h = hash(clave);
        if (tabla[h].containsKey(clave)) {
            throw new ElementoYaExistenteException();
        }
        tabla[h].put(clave, valor);
    }

    @Override
    public boolean pertenece(String clave) {
        int h = hash(clave);
        return tabla[h].containsKey(clave);
    }

    @Override
    public void borrar(String clave) {
        int h = hash(clave);
        tabla[h].remove(clave);
    }
}
