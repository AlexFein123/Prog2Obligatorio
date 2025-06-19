package um.edu.uy.interfaces;

import um.edu.uy.exceptions.ElementoYaExistenteException;
import um.edu.uy.tads.NodoHash;

public interface HashTable<K,T> {
    public void agregar (K clave,T valor) throws ElementoYaExistenteException, ElementoYaExistenteException;
    public NodoHash borrar (K clave);
    public T obtener (K clave);
    public int tamanio();
}
