package um.edu.uy.tads;

import um.edu.uy.exceptions.EmptyQueueException;
import um.edu.uy.interfaces.MyBinarySearchTRee;

public class MyBinarySearchTree<K extends Comparable<K>, T> implements MyBinarySearchTRee<K, T> {

    private Node<K, T> raiz;

    public MyBinarySearchTree() {
        this.raiz = null;
    }

    @Override
    public void insertar(K key, T value) {
        this.raiz = insert(key, value, this.raiz);
    }

    @Override
    public void borrar(K key) {
        this.raiz = delete(key, this.raiz);
    }

    @Override
    public boolean contiene(K key) {
        return contains(key, this.raiz);
    }

    @Override
    public int tamanio() {
        return tamanio(this.raiz);
    }

    @Override
    public ListaEnlazada<T> inOrder() {
        ListaEnlazada<T> listaInOrder = new ListaEnlazada<>();
        return inOrder(this.raiz, listaInOrder);
    }

    @Override
    public ListaEnlazada<T> preOrder() {
        ListaEnlazada<T> listaPreOrder = new ListaEnlazada<>();
        return preOrder(this.raiz, listaPreOrder);
    }

    @Override
    public ListaEnlazada<T> postOrder() {
        ListaEnlazada<T> listaPostOrder = new ListaEnlazada<>();
        return postOrder(this.raiz, listaPostOrder);
    }

    @Override
    public ListaEnlazada<T> nivel() {
        ListaEnlazada<T> listaNivel = new ListaEnlazada<>();
        ColaSimple<Node<K, T>> cola = new ColaSimple<>();

        cola.enqueue(this.raiz);

        while (!cola.isEmpty()) {
            try {
                Node<K, T> temp = cola.dequeue();
                listaNivel.agregarOrdenado(temp.getValue());

                if (temp.getIzquierda() != null) {
                    cola.enqueue(temp.getIzquierda());
                }
                if (temp.getDerecha() != null) {
                    cola.enqueue(temp.getDerecha());
                }
            } catch (EmptyQueueException e) {
                e.printStackTrace();
            }
        }

        return listaNivel;
    }

    private Node<K, T> insert(K key, T value, Node<K, T> subarbol) {
        if (subarbol == null) {
            return new Node<>(key, value);
        }

        int cmp = key.compareTo(subarbol.getKey());

        if (cmp > 0) {
            subarbol.setDerecha(insert(key, value, subarbol.getDerecha()));
        } else if (cmp < 0) {
            subarbol.setIzquierda(insert(key, value, subarbol.getIzquierda()));
        }

        return subarbol;
    }

    private Node<K, T> delete(K key, Node<K, T> subarbol) {
        if (subarbol == null) return null;

        int cmp = key.compareTo(subarbol.getKey());

        if (cmp == 0) {
            if (subarbol.getIzquierda() == null && subarbol.getDerecha() == null) {
                return null;
            } else if (subarbol.getDerecha() == null) {
                return subarbol.getIzquierda();
            } else if (subarbol.getIzquierda() == null) {
                return subarbol.getDerecha();
            } else {
                Node<K, T> maxNode = findMax(subarbol.getIzquierda());
                subarbol.setKey(maxNode.getKey());
                subarbol.setValue(maxNode.getValue());
                subarbol.setIzquierda(delete(maxNode.getKey(), subarbol.getIzquierda()));
            }
        } else if (cmp > 0) {
            subarbol.setDerecha(delete(key, subarbol.getDerecha()));
        } else {
            subarbol.setIzquierda(delete(key, subarbol.getIzquierda()));
        }

        return subarbol;
    }

    private boolean contains(K key, Node<K, T> subarbol) {
        if (subarbol == null) return false;

        int cmp = key.compareTo(subarbol.getKey());

        if (cmp == 0) return true;
        if (cmp > 0) return contains(key, subarbol.getDerecha());
        return contains(key, subarbol.getIzquierda());
    }

    private int tamanio(Node<K, T> subarbol) {
        if (subarbol == null) return 0;
        return 1 + tamanio(subarbol.getIzquierda()) + tamanio(subarbol.getDerecha());
    }

    private int cantHojas(Node<K, T> subarbol) {
        if (subarbol == null) return 0;

        if (subarbol.getIzquierda() == null && subarbol.getDerecha() == null) {
            return 1;
        }

        return cantHojas(subarbol.getIzquierda()) + cantHojas(subarbol.getDerecha());
    }

    private ListaEnlazada<T> inOrder(Node<K, T> subarbol, ListaEnlazada<T> lista) {
        if (subarbol != null) {
            inOrder(subarbol.getIzquierda(), lista);
            lista.agregarOrdenado(subarbol.getValue());
            inOrder(subarbol.getDerecha(), lista);
        }
        return lista;
    }

    private ListaEnlazada<T> preOrder(Node<K, T> subarbol, ListaEnlazada<T> lista) {
        if (subarbol != null) {
            lista.agregarOrdenado(subarbol.getValue());
            preOrder(subarbol.getIzquierda(), lista);
            preOrder(subarbol.getDerecha(), lista);
        }
        return lista;
    }

    private ListaEnlazada<T> postOrder(Node<K, T> subarbol, ListaEnlazada<T> lista) {
        if (subarbol != null) {
            postOrder(subarbol.getIzquierda(), lista);
            postOrder(subarbol.getDerecha(), lista);
            lista.agregarOrdenado(subarbol.getValue());
        }
        return lista;
    }

    private Node<K, T> findMax(Node<K, T> subarbol) {
        if (subarbol == null) return null;
        if (subarbol.getDerecha() == null) return subarbol;
        return findMax(subarbol.getDerecha());
    }
}



