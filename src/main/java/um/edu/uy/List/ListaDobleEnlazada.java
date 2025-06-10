package um.edu.uy.List;

import um.edu.uy.Interfaces.LISTATAD;

public class ListaDobleEnlazada implements LISTATAD {
    private NodoDoble inicio;

    public ListaDobleEnlazada() {
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
        } else if (nuevoValor.compareTo(inicio.getValor()) < 0) {

            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);
            inicio = nuevo;
        } else {
            NodoDoble actual = inicio;
            while (actual.getSiguiente() != null &&
                    ((Comparable) actual.getSiguiente().getValor()).compareTo(nuevoValor) < 0) {
                actual = actual.getSiguiente();
            }


            nuevo.setSiguiente(actual.getSiguiente());
            nuevo.setAnterior(actual);

            if (actual.getSiguiente() != null) {
                actual.getSiguiente().setAnterior(nuevo);
            }

            actual.setSiguiente(nuevo);
        }
    }


    @Override
    public boolean contiene(Object valor) {
        NodoDoble actual = inicio;
        while (actual != null) {
            if (actual.getValor().equals(valor)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    @Override
    public void agregarAlInicio(Object valor) {
        NodoDoble nuevo = new NodoDoble(valor);
        if (inicio != null) {
            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);
        }
        inicio = nuevo;
    }

    @Override
    public void agregarAlFinal(Object valor) {
        NodoDoble nuevo = new NodoDoble(valor);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoDoble actual = inicio;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
            nuevo.setAnterior(actual);
        }
    }

    public void imprimirLista() {
        NodoDoble actual = inicio;
        while (actual != null) {
            System.out.print(actual.getValor() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }

    @Override
    public void agregarOrdenado(Object valor) {
        this.agregar(valor);
    }

    @Override
    public void intercambiar(Object valor, int direccion) {
        if (inicio == null || direccion == 0) return;

        NodoDoble actual = inicio;


        while (actual != null && !actual.getValor().equals(valor)) {
            actual = actual.getSiguiente();
            if (actual == inicio) break;
        }

        if (actual == null) return;

        if (direccion == 1 && actual.getSiguiente() != null && actual.getSiguiente() != inicio) {
            NodoDoble siguiente = actual.getSiguiente();
            NodoDoble anterior = actual.getAnterior();


            NodoDoble despues = siguiente.getSiguiente();

            if (anterior != null) anterior.setSiguiente(siguiente);
            siguiente.setAnterior(anterior);

            siguiente.setSiguiente(actual);
            actual.setAnterior(siguiente);

            actual.setSiguiente(despues);
            if (despues != null) despues.setAnterior(actual);

            if (actual == inicio) inicio = siguiente;

        } else if (direccion == -1 && actual.getAnterior() != null && actual.getAnterior() != inicio.getAnterior()) {
            NodoDoble anterior = actual.getAnterior();
            NodoDoble antesDeAnterior = anterior.getAnterior();

            if (antesDeAnterior != null) antesDeAnterior.setSiguiente(actual);
            actual.setAnterior(antesDeAnterior);

            actual.setSiguiente(anterior);
            anterior.setAnterior(actual);

            anterior.setSiguiente(actual.getSiguiente());
            if (actual.getSiguiente() != null) actual.getSiguiente().setAnterior(anterior);

            if (anterior == inicio) inicio = actual;
        }
    }


}
