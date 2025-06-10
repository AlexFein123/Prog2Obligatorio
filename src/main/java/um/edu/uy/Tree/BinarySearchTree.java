package um.edu.uy.Tree;

import um.edu.uy.List.ListaEnlazada;
import um.edu.uy.Interfaces.LISTATAD;
import um.edu.uy.Interfaces.MyBinarySearchTree;

public class BinarySearchTree<K extends Comparable<K>, T> implements MyBinarySearchTree<K, T> {

    private NodeBST<K, T> root;

    @Override
    public void insert(K key, T data) {
        root = insertRecursive(root, key, data);
    }

    private NodeBST<K, T> insertRecursive(NodeBST<K, T> node, K key, T data) {
        if (node == null) return new NodeBST<>(key, data);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftChild = insertRecursive(node.leftChild, key, data);
        } else if (cmp > 0) {
            node.rightChild = insertRecursive(node.rightChild, key, data);
        } else {
            System.out.println("Clave duplicada no permitida: " + key);
        }
        return node;
    }

    @Override
    public T find(K key) {
        return findRecursive(root, key);
    }

    private T findRecursive(NodeBST<K, T> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node.data;
        else if (cmp < 0) return findRecursive(node.leftChild, key);
        else return findRecursive(node.rightChild, key);
    }

    @Override
    public void delete(K key) {
        root = deleteRecursive(root, key);
    }

    private NodeBST<K, T> deleteRecursive(NodeBST<K, T> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftChild = deleteRecursive(node.leftChild, key);
        } else if (cmp > 0) {
            node.rightChild = deleteRecursive(node.rightChild, key);
        } else {
            if (node.leftChild == null && node.rightChild == null) return null;
            if (node.leftChild == null) return node.rightChild;
            if (node.rightChild == null) return node.leftChild;
            NodeBST<K, T> successor = getMin(node.rightChild);
            node.key = successor.key;
            node.data = successor.data;
            node.rightChild = deleteRecursive(node.rightChild, successor.key);
        }

        return node;
    }

    private NodeBST<K, T> getMin(NodeBST<K, T> node) {
        while (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }

    public LISTATAD inOrder() {
        LISTATAD lista = new ListaEnlazada();
        inOrderRecursive(root, lista);
        return lista;
    }

    private void inOrderRecursive(NodeBST<K, T> node, LISTATAD lista) {
        if (node != null) {
            inOrderRecursive(node.leftChild, lista);
            lista.agregar(node.key);  // Se agrega como Object
            inOrderRecursive(node.rightChild, lista);
        }
    }

    public LISTATAD preOrder() {
        LISTATAD lista = new ListaEnlazada();
        preOrderRecursive(root, lista);
        return lista;
    }

    private void preOrderRecursive(NodeBST<K, T> node, LISTATAD lista) {
        if (node != null) {
            lista.agregar(node.key);
            preOrderRecursive(node.leftChild, lista);
            preOrderRecursive(node.rightChild, lista);
        }
    }

    public LISTATAD postOrder() {
        LISTATAD lista = new ListaEnlazada();
        postOrderRecursive(root, lista);
        return lista;
    }

    private void postOrderRecursive(NodeBST<K, T> node, LISTATAD lista) {
        if (node != null) {
            postOrderRecursive(node.leftChild, lista);
            postOrderRecursive(node.rightChild, lista);
            lista.agregar(node.key);
        }
    }
}
