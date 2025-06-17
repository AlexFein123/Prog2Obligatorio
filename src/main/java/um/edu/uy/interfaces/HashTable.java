package um.edu.uy.interfaces;


import um.edu.uy.exceptions.ElementoYaExistenteException;

public interface HashTable {
    public void insertar(String clave, Object valor) throws ElementoYaExistenteException;
    public boolean pertenece(String clave);
    public void borrar(String clave);
}