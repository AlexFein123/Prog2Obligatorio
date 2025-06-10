package um.edu.uy.Hash;

import um.edu.uy.Exceptions.ElementoYaExistenteException;
import um.edu.uy.Interfaces.HashTable;

public class HashTableLineal implements HashTable {
    private static class Entrada {
        String clave;
        Object valor;
        boolean borrado;

        Entrada(String clave, Object valor) {
            this.clave = clave;
            this.valor = valor;
            this.borrado = false;
        }
    }

    private Entrada[] tabla;
    private int tamaño;
    private int usados;

    public HashTableLineal(int capacidadInicial) {
        this.tamaño = capacidadInicial;
        this.tabla = new Entrada[tamaño];
        this.usados = 0;
    }

    private int hash(String clave) {
        return Math.abs(clave.hashCode()) % tamaño;
    }

    @Override
    public void insertar(String clave, Object valor) throws ElementoYaExistenteException {
        if (usados > tamaño / 2) reestructurar();

        int h = hash(clave);
        for (int i = 0; i < tamaño; i++) {
            int pos = (h + i) % tamaño;
            if (tabla[pos] == null || tabla[pos].borrado) {
                tabla[pos] = new Entrada(clave, valor);
                usados++;
                return;
            } else if (tabla[pos].clave.equals(clave)) {
                throw new ElementoYaExistenteException();
            }
        }
    }

    @Override
    public boolean pertenece(String clave) {
        int h = hash(clave);
        for (int i = 0; i < tamaño; i++) {
            int pos = (h + i) % tamaño;
            if (tabla[pos] == null) return false;
            if (!tabla[pos].borrado && tabla[pos].clave.equals(clave)) return true;
        }
        return false;
    }

    @Override
    public void borrar(String clave) {
        int h = hash(clave);
        for (int i = 0; i < tamaño; i++) {
            int pos = (h + i) % tamaño;
            if (tabla[pos] == null) return;
            if (!tabla[pos].borrado && tabla[pos].clave.equals(clave)) {
                tabla[pos].borrado = true;
                usados--;
                return;
            }
        }
    }

    private void reestructurar() {
        int nuevoTamaño = siguientePrimo(tamaño * 2);
        Entrada[] vieja = tabla;
        tabla = new Entrada[nuevoTamaño];
        tamaño = nuevoTamaño;
        usados = 0;

        for (Entrada e : vieja) {
            if (e != null && !e.borrado) {
                try {
                    insertar(e.clave, e.valor);
                } catch (ElementoYaExistenteException ex) {

                }
            }
        }
    }

    private int siguientePrimo(int n) {
        while (true) {
            if (esPrimo(n)) return n;
            n++;
        }
    }

    private boolean esPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i*i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
