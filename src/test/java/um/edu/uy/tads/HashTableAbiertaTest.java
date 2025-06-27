package um.edu.uy.tads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class HashTableAbiertaTest {

    private HashTableAbierta<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTableAbierta<>(10); // capacidad arbitraria
    }

    @Test
    void testAgregarYObtener() {
        hashTable.agregar("clave1", 100);
        assertEquals(100, hashTable.obtener("clave1"));
    }

    @Test
    void testSobrescribirValor() {
        hashTable.agregar("clave1", 100);
        hashTable.agregar("clave1", 200);
        assertEquals(200, hashTable.obtener("clave1"), "Debe actualizar el valor si la clave ya existe");
        assertEquals(1, hashTable.tamanio(), "No debe aumentar el tamaño al sobrescribir");
    }

    @Test
    void testObtenerClaveInexistenteDevuelveNull() {
        assertNull(hashTable.obtener("inexistente"), "Debe devolver null si la clave no existe");
    }

    @Test
    void testBorrarElementoExistente() {
        hashTable.agregar("clave1", 100);
        NodoHash borrado = hashTable.borrar("clave1");
        assertNotNull(borrado, "Debe devolver el nodo borrado");
        assertEquals(100, borrado.getValor(), "Debe devolver el valor correcto del nodo borrado");
        assertEquals(0, hashTable.tamanio(), "El tamaño debe decrementar después de borrar");
        assertNull(hashTable.obtener("clave1"), "No debe poder obtener una clave ya borrada");
    }

    @Test
    void testBorrarClaveInexistenteDevuelveNull() {
        assertNull(hashTable.borrar("inexistente"), "Debe devolver null si no existe la clave");
    }

    @Test
    void testTamanio() {
        assertEquals(0, hashTable.tamanio());
        hashTable.agregar("a", 1);
        hashTable.agregar("b", 2);
        assertEquals(2, hashTable.tamanio());
    }

    @Test
    void testColisiones() {
        // Estas claves se eligen a mano o con hashCode sabiendo que colisionan, para este test forzamos una colisión artificial
        String clave1 = "a"; // hashCode = 97
        String clave2 = "k"; // hashCode = 107 → ambas dan posición 7 si capacidad = 10

        HashTableAbierta<String, Integer> tablaConColisiones = new HashTableAbierta<>(10);
        tablaConColisiones.agregar(clave1, 1);
        tablaConColisiones.agregar(clave2, 2);

        assertEquals(1, tablaConColisiones.obtener(clave1));
        assertEquals(2, tablaConColisiones.obtener(clave2));
        assertEquals(2, tablaConColisiones.tamanio(), "Ambas claves deben coexistir en la misma lista");
    }
}