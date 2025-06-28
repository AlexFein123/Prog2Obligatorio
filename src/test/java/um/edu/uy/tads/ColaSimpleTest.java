package um.edu.uy.tads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ColaSimpleTest {

    private Stack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new Stack<>();
    }

    @Test
    void testIsEmptyInitially() {
        assertTrue(stack.isEmpty(), "La pila debe estar vacía inicialmente");
    }

    @Test
    void testPushAndTop() {
        stack.push(10);
        assertFalse(stack.isEmpty(), "La pila no debe estar vacía después de hacer push");
        assertEquals(10, stack.top(), "El elemento en el tope debe ser el último insertado");
    }

    @Test
    void testPushMultipleAndTop() {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.top(), "El tope debe ser el último insertado (20)");
    }

    @Test
    void testPop() {
        stack.push(10);
        stack.push(20);
        stack.pop(); // elimina 20
        assertEquals(10, stack.top(), "Después de hacer pop, el tope debe ser el valor anterior (10)");
    }

    @Test
    void testPopUntilEmpty() {
        stack.push(1);
        stack.pop();
        assertTrue(stack.isEmpty(), "La pila debe estar vacía después de hacer pop del único elemento");
    }

    @Test
    void testTopThrowsExceptionWhenEmpty() {
        assertThrows(EmptyStackException.class, () -> {
            stack.top();
        }, "Debe lanzar EmptyStackException si se llama a top sobre una pila vacía");
    }

    @Test
    void testPopThrowsExceptionWhenEmpty() {
        assertThrows(EmptyStackException.class, () -> {
            stack.pop();
        }, "Debe lanzar EmptyStackException si se llama a pop sobre una pila vacía");
    }

    @Test
    void testMakeEmpty() {
        stack.push(1);
        stack.push(2);
        stack.makeEmpty();
        assertTrue(stack.isEmpty(), "makeEmpty debe vaciar la pila");
        assertThrows(EmptyStackException.class, stack::top, "top debe lanzar excepción después de makeEmpty");
    }
}