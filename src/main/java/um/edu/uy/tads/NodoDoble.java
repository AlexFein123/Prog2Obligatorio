package um.edu.uy.tads;

public class NodoDoble {
    private Object valor;
    private NodoDoble siguiente;
    private NodoDoble anterior;

    public NodoDoble(Object valor) {
        this.valor = valor;
        this.siguiente = null;
        this.anterior = null;
    }

    public Object getValor() {
        return valor;
    }

    public NodoDoble getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble siguiente) {
        this.siguiente = siguiente;
    }

    public NodoDoble getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble anterior) {
        this.anterior = anterior;
    }
}