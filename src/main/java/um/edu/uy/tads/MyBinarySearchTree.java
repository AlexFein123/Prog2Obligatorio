package um.edu.uy.tads;

import um.edu.uy.exceptions.EmptyQueueException;
import um.edu.uy.interfaces.MyBinarySearchTRee;

public class MyBinarySearchTree<K extends Comparable<K>, T> implements MyBinarySearchTRee<K,T> {



    private Node<K, T> raiz;

    public MyBinarySearchTree() {
        this.raiz = null;
    }


    @Override
    public void insertar(K key, T value) {

        this.raiz = insert(key, value, this.raiz);

    }

    @Override
    public void borrar (K key){ this.raiz = delete(key, this.raiz); }

    @Override
    public boolean contiene(K key) {
        return contains(key, this.raiz);
    }

    @Override
    public int tamanio(){ return size(this.raiz); }



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
        ColaSimple<Node<K, T>> listaSubTree = new ColaSimple<>();
        listaSubTree.enqueue(this.raiz);

        while (!listaSubTree.isEmpty()) {
            Node<K, T> temp = null;
            try {
                temp = listaSubTree.dequeue();
            } catch (EmptyQueueException error) {
                error.printStackTrace();
            }

            listaNivel.agregarOrdenado(temp.getValue());

            if (temp.getLeft() != null) {
                listaSubTree.enqueue(temp.getLeft());
            }

            if (temp.getRight() != null) {
                listaSubTree.enqueue(temp.getRight());
            }
        }

        return listaNivel;
    }

    

    

    private Node<K, T> insert(K key, T value, Node<K, T> subTree){


        if (subTree == null){
            Node<K, T> newNode = new Node(key, value);
            subTree = newNode;

        } else {
            if (key.compareTo(subTree.getKey()) > 0){
                subTree.setRight(insert(key, value, subTree.getRight()));
            } else if (key.compareTo(subTree.getKey()) < 0){
                subTree.setLeft(insert(key, value, subTree.getLeft()));
            } else{
                // El elementro ya esta insertado, no se hace nada
            }
        }
        return subTree;
    }

    private Node<K,T> delete (K elemento, Node<K,T> subtree){
        Node<K,T> subtreeToReturn = subtree;

        if (elemento.compareTo(subtree.getKey()) == 0){
            if (subtree.getLeft() == null && subtree.getRight() == null){
                subtreeToReturn = null;
            }else if(subtree.getRight() == null){
                subtreeToReturn = subtree.getLeft();
            } else if(subtree.getLeft() == null){
                subtreeToReturn = subtree.getRight();
            }else {
                Node<K,T> min = findMax(subtree.getLeft());
                subtree.setKey(min.getKey());
                subtree.setValue(min.getValue());
                subtree.setLeft(delete(min.getKey(), subtree.getLeft()));


            }
        } else if (elemento.compareTo(subtree.getKey()) > 0){
            subtree.setRight(delete(elemento, subtree.getRight()));


        } else{
            subtree.setLeft(delete(elemento, subtree.getLeft()));


        }

        return subtreeToReturn;

    }

    private boolean contains(K key, Node<K, T> subTree){

        boolean result = false;

        if (subTree != null){
            if(key.compareTo(subTree.getKey()) == 0){
                result = true;
            } else if (key.compareTo(subTree.getKey()) > 0){
                result = contains(key, subTree.getRight());
            } else {
                result = contains(key, subTree.getLeft());
            }
        }

        return result;
    }

    private int size(Node<K,T> subtree) {
        int size = 0;

        if (subtree != null){
            size++;
            if (subtree.getLeft() != null){
                size = size + size(subtree.getLeft());
            }
            if(subtree.getRight() != null){
                size = size + size(subtree.getRight());
            }
        }
        return size;
    }

    private int countLeaf(Node<K, T> subtree){
        int leafs = 0;

        if (subtree != null){
            if (subtree.getRight() != null || subtree.getLeft() != null){
                if (subtree.getRight() != null){
                    leafs = leafs + countLeaf(subtree.getRight());
                }
                if (subtree.getLeft() != null){
                    leafs = leafs + countLeaf(subtree.getLeft());
                }
            } else{
                leafs++;
            }
        }
        return leafs;
    }


    private ListaEnlazada<T>
 postOrder(Node<K, T> subtree, ListaEnlazada<T>
 listaPostOrder){

        if (subtree != null){
            if (subtree.getLeft() != null){
                postOrder(subtree.getLeft(), listaPostOrder);
            }
            if (subtree.getRight() != null){
                postOrder(subtree.getRight(), listaPostOrder);
            }
            listaPostOrder.agregarOrdenado
(subtree.getValue());
        }
        return listaPostOrder;

    }

    private ListaEnlazada<T>
 preOrder(Node<K, T> subtree, ListaEnlazada<T>
 listaPreOrder){

        if (subtree != null){
            listaPreOrder.agregarOrdenado
(subtree.getValue());
            if (subtree.getLeft() != null){
                preOrder(subtree.getLeft(), listaPreOrder);
            }
            if (subtree.getRight() != null){
                preOrder(subtree.getRight(), listaPreOrder);
            }
        }
        return listaPreOrder;
    }

    private ListaEnlazada<T>
 inOrder(Node<K,T> subtree, ListaEnlazada<T>
 listaInOrder){


        if (subtree != null){
            if (subtree.getLeft() != null){
                inOrder(subtree.getLeft(), listaInOrder);
            }
            listaInOrder.agregarOrdenado(subtree.getValue());
            if (subtree.getRight() != null){
                inOrder(subtree.getRight(), listaInOrder);
            }
        }
        return listaInOrder;
    }

    private Node<K,T> findMax (Node<K,T> subtree){
        Node<K,T> treeToReturn = null;

        if (subtree != null){
            if(subtree.getRight() == null){
                treeToReturn = subtree;
            } else{
                treeToReturn = findMax(subtree.getRight());
            }
        }

        return treeToReturn;

    }



}


