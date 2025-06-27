package um.edu.uy.tads;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PairTest {

    @Test
    public void testGetters() {
        Pair<String, Integer> pair = new Pair<>("clave", 10);
        assertEquals("clave", pair.getKey());
        assertEquals(10, pair.getValue());
    }

    @Test
    public void testCompareTo() {
        Pair<String, Integer> pair1 = new Pair<>("a", 5);
        Pair<String, Integer> pair2 = new Pair<>("b", 10);
        Pair<String, Integer> pair3 = new Pair<>("c", 5);

        assertTrue(pair1.compareTo(pair2) < 0);
        assertTrue(pair2.compareTo(pair1) > 0);
        assertEquals(0, pair1.compareTo(pair3));
    }
}