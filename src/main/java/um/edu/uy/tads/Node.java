package um.edu.uy.tads;

public class Node<K, T> {


    private T valor;

    private K key;

    private Node<K, T> derecha;

    private Node<K, T> izquierda;

    public Node(K key, T value) {
        this.valor = value;
        this.key = key;
    }

    public T getValue() {
        return valor;
    }

    public void setValue(T valor) {
        this.valor = valor;
    }

    public Node<K, T> getDerecha() {
        return derecha;
    }

    public void setDerecha(Node<K, T> right) {
        this.derecha = derecha;
    }

    public Node<K, T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Node<K,T> left) {
        this.izquierda = izquierda;
    }


    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
}