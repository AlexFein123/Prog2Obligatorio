package um.edu.uy.Interfaces;


import um.edu.uy.Exceptions.ElementoYaExistenteException;

public interface HashTable {
    public void insertar(String clave, Object valor) throws ElementoYaExistenteException;
    public boolean pertenece(String clave);
    public void borrar(String clave);
}