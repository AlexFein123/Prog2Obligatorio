package um.edu.uy.List;

public class Nodo<T> {
    private Object valor;
    private Nodo<T> siguiente;

    public Nodo(Object valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    public Object getValor() {
        return valor;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
