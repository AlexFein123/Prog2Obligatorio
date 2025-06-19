package um.edu.uy.interfaces;

import um.edu.uy.exceptions.ElementoYaExistenteException;

public interface HashTable<K,T> {
    public void insertar (K clave,T valor) throws ElementoYaExistenteException, ElementoYaExistenteException;
    public boolean pertenece (K clave);
    public void borrar (K clave);
}
