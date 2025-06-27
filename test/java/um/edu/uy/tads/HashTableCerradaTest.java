package um.edu.uy.tads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class HashTableCerradaTest {

    private HashTableCerrada<String, Integer> tabla;

    @BeforeEach
    void setUp() {
        tabla = new HashTableCerrada<>(5); // capacidad chica para forzar rehash rápido
    }

    @Test
    void testAgregarYObtener() {
        tabla.agregar("uno", 1);
        tabla.agregar("dos", 2);
        assertEquals(1, tabla.obtener("uno"));
        assertEquals(2, tabla.obtener("dos"));
    }

    @Test
    void testSobrescribirValor() {
        tabla.agregar("clave", 100);
        tabla.agregar("clave", 200);
        assertEquals(200, tabla.obtener("clave"));
        assertEquals(1, tabla.tamanio());
    }

    @Test
    void testObtenerClaveInexistente() {
        assertNull(tabla.obtener("inexistente"));
    }

    @Test
    void testBorrarClaveExistente() {
        tabla.agregar("clave", 123);
        NodoHash<String, Integer> borrado = tabla.borrar("clave");
        assertNotNull(borrado);
        assertEquals("clave", borrado.getClave());
        assertEquals(123, borrado.getValor());
        assertEquals(0, tabla.tamanio());
        assertNull(tabla.obtener("clave"));
    }

    @Test
    void testBorrarClaveInexistente() {
        assertNull(tabla.borrar("clave_inexistente"));
    }

    @Test
    void testTamanio() {
        tabla.agregar("a", 1);
        tabla.agregar("b", 2);
        assertEquals(2, tabla.tamanio());
    }
}