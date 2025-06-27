package um.edu.uy.tads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.EmptyStackException;

public class StackTest {

    private Stack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new Stack<>();
    }

    @Test
    public void testIsEmpty_InitiallyTrue() {
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testPushAndTop() {
        stack.push(10);
        assertFalse(stack.isEmpty());
        assertEquals(10, stack.top());
    }

    @Test
    public void testPopRemovesTop() {
        stack.push(5);
        stack.push(15);
        assertEquals(15, stack.top());

        stack.pop();
        assertEquals(5, stack.top());
    }

    @Test
    public void testPopOnEmptyThrows() {
        assertThrows(EmptyStackException.class, () -> {
            stack.pop();
        });
    }

    @Test
    public void testTopOnEmptyThrows() {
        assertThrows(EmptyStackException.class, () -> {
            stack.top();
        });
    }

    @Test
    public void testMakeEmpty() {
        stack.push(1);
        stack.push(2);
        stack.makeEmpty();
        assertTrue(stack.isEmpty());
        assertThrows(EmptyStackException.class, () -> stack.top());
    }
}