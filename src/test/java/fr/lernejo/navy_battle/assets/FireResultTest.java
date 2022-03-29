package fr.lernejo.navy_battle.assets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FireResultTest {
    @Test
    public void toAPI() {
        var res = FireResult.HIT;
        assertEquals("hit", res.toAPI());
    }

    @Test
    public void fromAPI() {
        assertEquals(FireResult.HIT, FireResult.fromAPI("hit"));
    }

    @Test
    public void fromAPIBad() {
        assertThrows(Exception.class, () -> FireResult.fromAPI("badvalue"));
    }
}