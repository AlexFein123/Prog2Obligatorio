package um.edu.uy.Hash;

import um.edu.uy.Exceptions.ElementoYaExistenteException;
import um.edu.uy.Interfaces.HashTable;

import java.util.LinkedList;

public class HashTableAbiertaListaOrdenada implements HashTable {
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
    public HashTableAbiertaListaOrdenada(int capacidad) {
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
        LinkedList<Entrada> lista = tabla[h];

        for (Entrada e : lista) {
            if (e.clave.equals(clave)) throw new ElementoYaExistenteException();
        }

        int i = 0;
        while (i < lista.size() && lista.get(i).clave.compareTo(clave) < 0) {
            i++;
        }
        lista.add(i, new Entrada(clave, valor));
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
