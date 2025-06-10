package um.edu.uy.Interfaces;

public interface MyBinarySearchTree<K extends Comparable<K>, T> {
    T find(K key);
    void insert(K key, T data);
    void delete(K key);
}