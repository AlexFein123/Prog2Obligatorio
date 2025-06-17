package um.edu.uy.tads;

import um.edu.uy.exceptions.ElementoYaExistenteException;
import um.edu.uy.interfaces.HashTable;

import java.util.LinkedList;

public class HashTableAbiertaLista implements HashTable {
    private LinkedList<Entrada>[] tabla;
    private int tamaño;

    private static class Entrada {
        String clave;
        Object valor;

        Entrada(String clave, Object valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    @SuppressWarnings("unchecked")
    public HashTableAbiertaLista(int capacidad) {
        this.tamaño = capacidad;
        tabla = new LinkedList[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    private int hash(String clave) {
        return Math.abs(clave.hashCode()) % tamaño;
    }

    @Override
    public void insertar(String clave, Object valor) throws ElementoYaExistenteException {
        int h = hash(clave);
        for (Entrada e : tabla[h]) {
            if (e.clave.equals(clave)) throw new ElementoYaExistenteException();
        }
        tabla[h].add(new Entrada(clave, valor));
    }

    @Override
    public boolean pertenece(String clave) {
        int h = hash(clave);
        for (Entrada e : tabla[h]) {
            if (e.clave.equals(clave)) return true;
        }
        return false;
    }

    @Override
    public void borrar(String clave) {
        int h = hash(clave);
        tabla[h].removeIf(e -> e.clave.equals(clave));
    }
}
