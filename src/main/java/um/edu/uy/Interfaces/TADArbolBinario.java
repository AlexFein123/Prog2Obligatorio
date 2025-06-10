package um.edu.uy.Interfaces;



public interface TADArbolBinario<T extends Comparable<T>> {

    void agregar(T elemento);

    T eliminar();

    String toString();
}
