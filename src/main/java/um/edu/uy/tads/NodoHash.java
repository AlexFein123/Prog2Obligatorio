package um.edu.uy.tads;


public class NodoHash<K,T> {
    private K clave;
    private T valor;
    private NodoHash<K, T> siguiente;


    public NodoHash(K clave, T valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }

    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public NodoHash<K, T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoHash<K, T> siguiente) {
        this.siguiente = siguiente;

    }
}

