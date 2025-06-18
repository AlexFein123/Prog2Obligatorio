package um.edu.uy.interfaces;


import um.edu.uy.exceptions.ElementoYaExistenteException;

public interface HashTable<K,T> {

    void agregar(K key, T value);

    T obtener(K key);

    T remover(K key);

    int size();

}