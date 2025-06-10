package um.edu.uy.List;

import um.edu.uy.Interfaces.LISTATAD;

public class ListaEnlazada implements LISTATAD {
    private Nodo inicio;

    public ListaEnlazada() {
        this.inicio = null;
    }

    @Override
    public void agregar(Object valor) {
        Nodo nuevoNodo = new Nodo(valor);
        if (inicio == null) {
            inicio = nuevoNodo;
        } else {
            Nodo actual = inicio;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
    }

    public void imprimirLista() {
        Nodo actual = inicio;
        while (actual != null) {
            System.out.print(actual.getValor() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }
    @Override
    public boolean contiene(Object valor) {
        Nodo actual = inicio;
        while (actual != null) {
            if (actual.getValor().equals(valor)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    @Override
    public void agregarAlInicio(Object valor) {
        Nodo nuevoNodo = new Nodo(valor);
        nuevoNodo.setSiguiente(inicio);
        inicio = nuevoNodo;
    }

    @Override
    public void agregarAlFinal(Object valor) {
        this.agregar(valor);
    }

    @Override
    public void agregarOrdenado(Object valor) {
        if (!(valor instanceof Comparable)) {
            throw new IllegalArgumentException("El objeto debe implementar Comparable.");
        }

        Nodo nuevo = new Nodo(valor);
        Comparable nuevoValor = (Comparable) valor;


        if (inicio == null || nuevoValor.compareTo(inicio.getValor()) < 0) {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
            return;
        }

        Nodo actual = inicio;
        while (actual.getSiguiente() != null &&
                ((Comparable) actual.getSiguiente().getValor()).compareTo(nuevoValor) < 0) {
            actual = actual.getSiguiente();
        }

        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
    }

    public void visualizar(ListaEnlazada posiciones) {
        Nodo actual = this.inicio;
        int indice = 1;

        Nodo nodoPos = posiciones.inicio;

        while (actual != null && nodoPos != null) {
            Integer posBuscada = (Integer) nodoPos.getValor();

            if (indice == posBuscada) {
                System.out.print(actual.getValor());
                System.out.print(" ");
                nodoPos = nodoPos.getSiguiente();
            }

            actual = actual.getSiguiente();
            indice++;
        }
    }

    public void intercambiar(Object valor, int direccion) {
        if (inicio == null || direccion == 0) return;

        Nodo anterior = null;
        Nodo actual = inicio;

        while (actual != null && !actual.getValor().equals(valor)) {
            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (actual == null) return;

        if (direccion == 1 && actual.getSiguiente() != null) {
            Nodo siguiente = actual.getSiguiente();
            Nodo siguienteDeSiguiente = siguiente.getSiguiente();

            if (anterior != null) {
                anterior.setSiguiente(siguiente);
            } else {
                inicio = siguiente;
            }

            siguiente.setSiguiente(actual);
            actual.setSiguiente(siguienteDeSiguiente);

        } else if (direccion == -1 && anterior != null) {
            Nodo anteAnterior = null;
            Nodo cursor = inicio;

            while (cursor != null && cursor.getSiguiente() != anterior) {
                cursor = cursor.getSiguiente();
            }
            anteAnterior = cursor;

            if (anteAnterior != null) {
                anteAnterior.setSiguiente(actual);
            } else {
                inicio = actual;
            }

            anterior.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(anterior);
        }
    }

    public ListaEnlazada interseccion(ListaEnlazada otraLista) {
        ListaEnlazada resultado = new ListaEnlazada();
        Nodo actual = this.inicio;

        while (actual != null) {
            if (otraLista.contiene(actual.getValor())) {
                resultado.agregar(actual.getValor());
            }
            actual = actual.getSiguiente();
        }

        return resultado;
    }

    public ListaEnlazada diferenciaSimetrica(ListaEnlazada otraLista) {
        ListaEnlazada resultado = new ListaEnlazada();

        Nodo actual = this.inicio;
        while (actual != null) {
            if (!otraLista.contiene(actual.getValor())) {
                resultado.agregar(actual.getValor());
            }
            actual = actual.getSiguiente();
        }
        Nodo otro = otraLista.inicio;
        while (otro != null) {
            if (!this.contiene(otro.getValor())) {
                resultado.agregar(otro.getValor());
            }
            otro = otro.getSiguiente();
        }

        return resultado;
    }
}

