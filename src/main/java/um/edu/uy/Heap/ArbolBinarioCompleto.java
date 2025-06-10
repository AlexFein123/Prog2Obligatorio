package um.edu.uy.Heap;
import um.edu.uy.Interfaces.TADArbolBinario;

import java.util.LinkedList;
import java.util.Queue;

public class ArbolBinarioCompleto<T extends Comparable<T>> implements TADArbolBinario<T> {

    private class Nodo {
        T valor;
        Nodo izquierdo, derecho;

        Nodo(T valor) {
            this.valor = valor;
        }
    }
    private Nodo raiz;

    @Override
    public void agregar(T elemento) {
        Nodo nuevo = new Nodo(elemento);
        if (raiz == null) {
            raiz = nuevo;
            return;
        }
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            if (actual.izquierdo == null) {
                actual.izquierdo = nuevo;
                return;
            } else {
                cola.add(actual.izquierdo);
            }
            if (actual.derecho == null) {
                actual.derecho = nuevo;
                return;
            } else {
                cola.add(actual.derecho);
            }
        }
    }

    @Override
    public T eliminar() {
        if (raiz == null) return null;

        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        Nodo actual = null, ultimo = null, penultimo = null;
        while (!cola.isEmpty()) {
            actual = cola.poll();
            if (actual.izquierdo != null) {
                cola.add(actual.izquierdo);
                penultimo = actual;
            }
            if (actual.derecho != null) {
                cola.add(actual.derecho);
                penultimo = actual;
            }
            ultimo = actual;
        }

        T valorRaiz = raiz.valor;
        raiz.valor = ultimo.valor;

        if (penultimo != null) {
            if (penultimo.derecho == ultimo) {
                penultimo.derecho = null;
            } else {
                penultimo.izquierdo = null;
            }
        } else {
            raiz = null;
        }

        return valorRaiz;
    }

    @Override // Se usa el sb para poder cambiar los valores de los nodos por texto
    public String toString() {
        if (raiz == null) return "(vacío)";
        StringBuilder sb = new StringBuilder();
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        int nivel = 0;
        while (!cola.isEmpty()) {
            int cantidad = cola.size();
            sb.append("Nivel ").append(nivel).append(": ");
            for (int i = 0; i < cantidad; i++) {
                Nodo actual = cola.poll();
                sb.append(actual.valor).append(" ");
                if (actual.izquierdo != null) cola.add(actual.izquierdo);
                if (actual.derecho != null) cola.add(actual.derecho);
            }
            sb.append("\n");
            nivel++;
        }
        return sb.toString();
    }
}