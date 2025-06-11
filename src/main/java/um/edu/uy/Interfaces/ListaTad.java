package um.edu.uy.Interfaces;

public interface ListaTad<K> {

    void agregar(Object valor);

    boolean contiene(Object valor);

    void agregarAlInicio(Object valor);

    void agregarAlFinal(Object valor);

    void agregarOrdenado(Object valor);

    void intercambiar(Object valor, int direccion);

}
