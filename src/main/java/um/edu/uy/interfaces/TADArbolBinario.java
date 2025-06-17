package um.edu.uy.interfaces;



public interface TADArbolBinario<T extends Comparable<T>> {

    void agregar(T elemento);

    T eliminar();

    String toString();
}
