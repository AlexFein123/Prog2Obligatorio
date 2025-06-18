package um.edu.uy.interfaces;
import um.edu.uy.exceptions.FueraDeRango;
public interface ListaTad<T> {

    void agregarposicion (T valor, int posicion) throws FueraDeRango;
    void agregarprimero (T valor);
    void agregarAlFinal(T valor);
    void agregarOrdenado(T valor);

    boolean contiene(T valor);

    T remover (int posicion) throws FueraDeRango;
    T removerultimo ();

    T obtenervalorposicion (int posicion) throws FueraDeRango;

    T obtener (T valueToSearch);


    int tamanio();

    T[] toArray();



}
