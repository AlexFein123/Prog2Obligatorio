package um.edu.uy.Interfaces;

public interface TADHeap<T extends Comparable<T>> {

    void agregar(T elemento);

    T obtenerYEliminar();

    int obtenerTamanio();

    void imprimir();  // salida visual

}