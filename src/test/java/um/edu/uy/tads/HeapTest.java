package um.edu.uy.tads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class HeapTest {

    private Heap<Integer> minHeap;
    private Heap<Integer> maxHeap;

    @BeforeEach
    public void setup() {
        minHeap = new Heap<>(10, true);  // heap mínimo
        maxHeap = new Heap<>(10, false); // heap máximo
    }

    @Test
    public void testAgregarYObtenerTamanio() {
        assertEquals(0, minHeap.obtenerTamanio());
        minHeap.agregar(5);
        assertEquals(1, minHeap.obtenerTamanio());
        minHeap.agregar(3);
        assertEquals(2, minHeap.obtenerTamanio());
    }

    @Test
    public void testHeapMinimo() {
        minHeap.agregar(10);
        minHeap.agregar(5);
        minHeap.agregar(20);
        minHeap.agregar(1);

        assertEquals(1, minHeap.obtenerYEliminar());
        assertEquals(3, minHeap.obtenerTamanio());
        assertEquals(5, minHeap.obtenerYEliminar());
        assertEquals(2, minHeap.obtenerTamanio());
    }

    @Test
    public void testHeapMaximo() {
        maxHeap.agregar(10);
        maxHeap.agregar(5);
        maxHeap.agregar(20);
        maxHeap.agregar(1);

        assertEquals(20, maxHeap.obtenerYEliminar());
        assertEquals(3, maxHeap.obtenerTamanio());
        assertEquals(10, maxHeap.obtenerYEliminar());
        assertEquals(2, maxHeap.obtenerTamanio());
    }

    @Test
    public void testObtenerYEliminarCuandoVacio() {
        assertNull(minHeap.obtenerYEliminar());
        assertNull(maxHeap.obtenerYEliminar());
    }

    @Test
    public void testAgregarHastaLlenar() {
        for (int i = 0; i < 10; i++) {
            minHeap.agregar(i);
        }
        assertEquals(10, minHeap.obtenerTamanio());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            minHeap.agregar(11);
        });
        assertEquals("Heap lleno", exception.getMessage());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(minHeap.isEmpty());
        minHeap.agregar(1);
        assertFalse(minHeap.isEmpty());
        minHeap.obtenerYEliminar();
        assertTrue(minHeap.isEmpty());
    }
}