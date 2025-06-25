package um.edu.uy.tads;

import um.edu.uy.tads.Nodo;
import um.edu.uy.tads.ListaEnlazada;

public class ListaEnlazadaUtils {

    public static <T> void intercambiar(ListaEnlazada<T> lista, T valor, int direccion) {
        if (lista.isEmpty() || direccion == 0) return;

        Nodo<T> anterior = null;
        Nodo<T> actual = lista.getInicio();

        while (actual != null && !actual.getValor().equals(valor)) {
            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (actual == null) return;

        if (direccion == 1 && actual.getSiguiente() != null) {
            Nodo<T> siguiente = actual.getSiguiente();
            Nodo<T> siguienteDeSiguiente = siguiente.getSiguiente();

            if (anterior != null) {
                anterior.setSiguiente(siguiente);
            } else {
                lista.setInicio(siguiente);
            }

            siguiente.setSiguiente(actual);
            actual.setSiguiente(siguienteDeSiguiente);
        } else if (direccion == -1 && anterior != null) {
            Nodo<T> anteAnterior = null;
            Nodo<T> cursor = lista.getInicio();

            while (cursor != null && cursor.getSiguiente() != anterior) {
                cursor = cursor.getSiguiente();
            }

            anteAnterior = cursor;

            if (anteAnterior != null) {
                anteAnterior.setSiguiente(actual);
            } else {
                lista.setInicio(actual);
            }

            anterior.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(anterior);
        }
    }

    public static <T> void imprimirLista(ListaEnlazada<T> lista) {
        Nodo<T> actual = lista.getInicio();
        while (actual != null) {
            System.out.print(actual.getValor() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }
}
