package um.edu.uy.interfaces;

public interface TADHeap<T extends Comparable<T>> {

    void agregar(T elemento);

    T obtenerYEliminar();

    int obtenerTamanio();

    void imprimir();  // salida visual

}