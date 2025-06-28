package um.edu.uy.tads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ListaEnlazadaTest {

    private ListaEnlazada<Integer> lista;

    @BeforeEach
    void setUp() {
        lista = new ListaEnlazada<>();
    }

    @Test
    void getInicio() {
        assertNull(lista.getInicio());
        lista.agregar(1);
        assertNotNull(lista.getInicio());
        assertEquals(1, lista.getInicio().getValor());
    }

    @Test
    void setInicio() {
        Nodo<Integer> nodo = new Nodo<>(5);
        lista.setInicio(nodo);
        assertEquals(5, lista.getInicio().getValor());
    }

    @Test
    void getUltimo() {
        assertNull(lista.getUltimo());
        lista.agregar(1);
        assertEquals(1, lista.getUltimo().getValor());
    }

    @Test
    void setUltimo() {
        Nodo<Integer> nodo = new Nodo<>(10);
        lista.setUltimo(nodo);
        assertEquals(10, lista.getUltimo().getValor());
    }

    @Test
    void tamanio() {
        assertEquals(0, lista.tamanio());
        lista.agregar(3);
        assertEquals(1, lista.tamanio());
    }


    @Test
    void agregarAlFinal() {
        lista.agregarAlFinal(5);
        assertEquals(5, lista.getUltimo().getValor());
    }


    @Test
    void remover() throws FueraDeRango {
        lista.agregar(1);
        lista.agregar(2);
        lista.agregar(3);
        int valor = lista.remover(1);
        assertEquals(2, valor);
        assertArrayEquals(new Integer[]{1, 3}, lista.toArray());
    }

    @Test
    void removerultimo() {
        lista.agregar(1);
        lista.agregar(2);
        int val = lista.removerultimo();
        assertEquals(2, val);
    }

    @Test
    void obtenervalorposicion() throws FueraDeRango {
        lista.agregar(10);
        assertEquals(10, lista.obtenervalorposicion(0));
    }

    @Test
    void obtener() {
        lista.agregar(5);
        assertEquals(5, lista.obtener(5));
        assertNull(lista.obtener(10));
    }

    @Test
    void toArray() {
        lista.agregar(1);
        lista.agregar(2);
        assertArrayEquals(new Integer[]{1, 2}, lista.toArray());
    }

    @Test
    void contiene() {
        lista.agregar(3);
        assertTrue(lista.contiene(3));
        assertFalse(lista.contiene(1));
    }

    @Test
    void imprimirLista() {
        lista.agregar(1);
        lista.imprimirLista(); // solo verifica que no haya excepción
    }

    @Test
    void visualizar() {
        ListaEnlazada<Integer> pos = new ListaEnlazada<>();
        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);
        pos.agregar(2);
        lista.visualizar(pos); // solo visualiza, sin asserts
    }

    @Test
    void intercambiar() {
        lista.agregar(1);
        lista.intercambiar(1, 1); // no lanza excepción, no podemos testear sin saber implementación interna
    }

    @Test
    void iterator() {
        lista.agregar(5);
        assertEquals(1, lista.tamanio(), "El tamaño debería ser 1");

        Iterator<Integer> it = lista.iterator();

        assertTrue(it.hasNext(), "El iterador debería tener un siguiente");
        Integer valor = it.next();
        assertEquals(5, valor, "El valor devuelto por el iterador no es correcto");
        assertFalse(it.hasNext(), "Ya no debería haber más elementos");
    }
    @Test
    void isEmpty() {
        assertTrue(lista.isEmpty());
        lista.agregar(1);
        assertFalse(lista.isEmpty());
    }

    @Test
    void agregar() {
        lista.agregar(7);
        assertEquals(7, lista.getInicio().getValor());
    }
}