package um.edu.uy.tads;

import um.edu.uy.interfaces.LISTATAD;

public class ListaCircularDoble implements LISTATAD {
    private NodoDoble inicio;

    public ListaCircularDoble() {
        this.inicio = null;
    }

    @Override
    public void agregar(Object valor) {
        if (!(valor instanceof Comparable)) {
            throw new IllegalArgumentException("El objeto debe ser Comparable");
        }

        NodoDoble nuevo = new NodoDoble(valor);
        Comparable nuevoValor = (Comparable) valor;

        if (inicio == null) {
            inicio = nuevo;
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
            return;
        }

        NodoDoble actual = inicio;

        do {
            Comparable actualValor = (Comparable) actual.getValor();
            if (nuevoValor.compareTo(actualValor) < 0) {
                break;
            }
            actual = actual.getSiguiente();
        } while (actual != inicio);

        NodoDoble anterior = actual.getAnterior();
        nuevo.setSiguiente(actual);
        nuevo.setAnterior(anterior);
        anterior.setSiguiente(nuevo);
        actual.setAnterior(nuevo);

        if (nuevoValor.compareTo((Comparable) inicio.getValor()) < 0) {
            inicio = nuevo;
        }
    }

    @Override
    public boolean contiene(Object valor) {
        if (inicio == null) return false;

        NodoDoble actual = inicio;
        do {
            if (actual.getValor().equals(valor)) {
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != inicio);

        return false;
    }

    @Override
    public void agregarAlInicio(Object valor) {
        this.agregar(valor);
    }

    @Override
    public void agregarAlFinal(Object valor) {
        this.agregar(valor);
    }

    @Override
    public void agregarOrdenado(Object valor) {

    }

    @Override
    public void intercambiar(Object valor, int direccion) {

    }

    public void imprimirLista() {
        if (inicio == null) {
            System.out.println("Lista vacía");
            return;
        }

        NodoDoble actual = inicio;
        do {
            System.out.print(actual.getValor());
            System.out.print(" ");
            actual = actual.getSiguiente();

        } while (actual != inicio);
        System.out.println(" ");

    }
    public Object removeFirst() {
        if (inicio == null) return null;

        Object valor = inicio.getValor();

        if (inicio.getSiguiente() == inicio) {
            inicio = null;
        } else {
            NodoDoble siguiente = inicio.getSiguiente();
            NodoDoble anterior = inicio.getAnterior();
            siguiente.setAnterior(anterior);
            anterior.setSiguiente(siguiente);
            inicio = siguiente;
        }

        return valor;
    }
    public Object removeLast() {
        if (inicio == null) return null;

        NodoDoble ultimo = inicio.getAnterior();
        Object valor = ultimo.getValor();

        if (ultimo == inicio) {
            inicio = null;
        } else {
            NodoDoble anterior = ultimo.getAnterior();
            anterior.setSiguiente(inicio);
            inicio.setAnterior(anterior);
        }

        return valor;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public void addLast(Object valor) {
        NodoDoble nuevo = new NodoDoble(valor);

        if (inicio == null) {
            inicio = nuevo;
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
        } else {
            NodoDoble ultimo = inicio.getAnterior();
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);
        }
    }


}
