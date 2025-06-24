package um.edu.uy.tads;

import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.interfaces.ListaTad;

public class ListaEnlazada<T> implements ListaTad<T> {
    private Nodo<T> inicio;
    private Nodo<T> ultimo;
    private int size;

    public ListaEnlazada() {
        this.inicio = null;
        this.size = 0;
    }

    @Override
    public int tamanio() {
        return size;
    }


    @Override
    public void agregarprimero(T valor) {
        Nodo<T> nuevoNodo = new Nodo<T>(valor);

        if (inicio == null) {
            size++;
            ultimo = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(inicio);
            inicio = nuevoNodo;
            size++;
        }
    }

    @Override
    public void agregarAlFinal(T valor) {
        Nodo<T> nuevoNodo = new Nodo<T>(valor);
        if (inicio == null) {
            inicio = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.setSiguiente(nuevoNodo);
            ultimo = nuevoNodo;
        }
        size++;
    }
    @Override
    public void agregarOrdenado(T valor) {
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
        size++;
    }

    @Override
    public void agregarposicion(T valor, int posicion) throws FueraDeRango {
        if (posicion == 0) {
            agregarprimero(valor);
        } else if (posicion == (size - 1)) {
            agregarAlFinal(valor);
        } else {
            Nodo<T> anterior = devolverNodoPosicion(posicion - 1);
            Nodo<T> temp = new Nodo(valor);
            temp.setSiguiente(anterior.getSiguiente());
            anterior.setSiguiente(temp);
            size++;
        }
    }


    @Override
    public T remover(int posicion) throws FueraDeRango {
        T retorno = null;

        if (posicion == size - 1) {
            retorno = removerultimo();
        } else if (posicion == 0) {
            retorno = (T) inicio.getValor();
            Nodo<T> nuevoPri = inicio.getSiguiente();
            inicio.setSiguiente(null);
            inicio = nuevoPri;
            size--;
        } else {
            Nodo<T> temp = devolverNodoPosicion(posicion - 1);
            Nodo<T> eliminar = temp.getSiguiente();
            retorno = (T) eliminar.getValor();
            temp.setSiguiente(eliminar.getSiguiente());
            eliminar.setSiguiente(null);
            size--;
        }

        return retorno;
    }

    public T removerultimo() {
        T devolver = null;

        if (size == 0) {
            return null;
        }

        devolver = (T) ultimo.getValor();

        if (size == 1) {
            inicio = null;
            ultimo = null;
        } else {
            try {
                Nodo<T> temp = devolverNodoPosicion(size - 2);
                temp.setSiguiente(null);
                ultimo = temp;
            } catch (FueraDeRango e) {
                e.printStackTrace();
            }
        }

        size--;
        return devolver;
    }


    @Override
    public T obtenervalorposicion(int posicion) throws FueraDeRango {
        return (T) devolverNodoPosicion(posicion).getValor();
    }

    @Override
    public T obtener(T valueToSearch) {
        Nodo<T> actual = inicio;

        while (actual != null) {
            if (actual.getValor().equals(valueToSearch)) {
                return (T) actual.getValor();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }

    @Override
    public T[] toArray() {
        T[] array = (T[]) new Object[this.size];
        Nodo<T> temp = inicio;

        for (int i = 0; i < this.size; i++) {
            array[i] = (T) temp.getValor();
            temp = temp.getSiguiente();
        }

        return array;
    }


    private Nodo<T> devolverNodoPosicion(int posicion) throws FueraDeRango {
        if (posicion >= size || posicion < 0) {
            throw new FueraDeRango();
        }

        if (posicion == (size - 1)) {
            return ultimo;
        }

        Nodo<T> temp = inicio;
        for (int i = 0; i < posicion; i++) {
            temp = temp.getSiguiente();
        }

        return temp;
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

    public void imprimirLista() {
        Nodo actual = inicio;
        while (actual != null) {
            System.out.print(actual.getValor() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }

    public void visualizar(ListaEnlazada posiciones) {
        Nodo<T> actual = this.inicio;
        int indice = 1;
        Nodo<T> nodoPos = posiciones.inicio;

        while (actual != null && nodoPos != null) {
            Integer posBuscada = (Integer) nodoPos.getValor();

            if (indice == posBuscada) {
                System.out.print(actual.getValor() + " ");
                nodoPos = nodoPos.getSiguiente();
            }

            actual = actual.getSiguiente();
            indice++;
        }
    }

    public void intercambiar(T valor, int direccion) {
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

    public boolean isEmpty(){
        return size == 0;
    }


}
