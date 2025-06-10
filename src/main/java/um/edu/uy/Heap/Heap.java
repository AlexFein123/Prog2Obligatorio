package um.edu.uy.Heap;
import um.edu.uy.Interfaces.TADHeap;

public class Heap<T extends Comparable<T>> implements TADHeap<T> {
    private T[] datos;
    private int tamaño;
    private boolean esMinimo;

    @SuppressWarnings("unchecked")
    public Heap(int capacidad, boolean esMinimo) {
        datos = (T[]) new Comparable[capacidad];
        this.tamaño = 0;
        this.esMinimo = esMinimo;
    }

    @Override
    public void agregar(T elemento) {
        if (tamaño >= datos.length) throw new RuntimeException("Heap lleno");
        datos[tamaño] = elemento;
        flotar(tamaño);
        tamaño++;
    }

    @Override
    public T obtenerYEliminar() {
        if (tamaño == 0) return null;
        T raiz = datos[0];
        datos[0] = datos[--tamaño];
        hundir(0);
        return raiz;
    }

    @Override
    public int obtenerTamanio() {
        return tamaño;
    }

    @Override
    public void imprimir() {
        int nivel = 0;
        int elementosEnNivel = 1;
        int i = 0;
        while (i < tamaño) {
            System.out.print("Nivel " + nivel + ": ");
            for (int j = 0; j < elementosEnNivel && i < tamaño; j++, i++) {
                System.out.print(datos[i] + " ");
            }
            System.out.println();
            nivel++;
            elementosEnNivel *= 2;
        }
    }

    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (comparar(datos[i], datos[padre])) {
                intercambiar(i, padre);
                i = padre;
            } else break;
        }
    }

    private void hundir(int i) {
        while (2 * i + 1 < tamaño) {
            int hijo = 2 * i + 1;
            if (hijo + 1 < tamaño && comparar(datos[hijo + 1], datos[hijo])) {
                hijo++;
            }
            if (comparar(datos[hijo], datos[i])) {
                intercambiar(i, hijo);
                i = hijo;
            } else break;
        }
    }

    private boolean comparar(T a, T b) {
        return esMinimo ? a.compareTo(b) < 0 : a.compareTo(b) > 0;
    }

    private void intercambiar(int i, int j) {
        T tmp = datos[i];
        datos[i] = datos[j];
        datos[j] = tmp;
    }
}
