package fr.lernejo.navy_battle.assets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CoordinatesTest {

    @Test
    void testFromString() {
        var coordinates = new Coordinates("C7");
        assertEquals(6, coordinates.getY());
        assertEquals(2, coordinates.getX());
    }

    @Test
    void testFromStringInvalid() {
        assertThrows(RuntimeException.class, () -> new Coordinates("PP"));
        assertThrows(RuntimeException.class, () -> new Coordinates("P"));
        assertThrows(RuntimeException.class, () -> new Coordinates("PPP"));
    }

    @Test
    void testToString() {
        var coordinates = new Coordinates(2, 6);
        assertEquals("C7", coordinates.toString());
    }

    @Test
    void testHash() {
        var one = new Coordinates(1, 2);
        var two = new Coordinates(1, 2);
        var three = new Coordinates(4, 5);

        assertEquals(one, two);
        assertNotEquals(one, three);

        assertEquals(one.hashCode(), two.hashCode());
        assertNotEquals(one.hashCode(), three.hashCode());
    }

    @Test
    void otherTestEquals() {
        Coordinates one = new Coordinates(1, 2);
        assertNotEquals(one, new Object());

        Object two = null;
        if (one.getX() == 4)
            two = new Object();

        assertNotEquals(two, one);

        assertEquals(one, one);
    }
}