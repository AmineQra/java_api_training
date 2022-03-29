package fr.lernejo.navy_battle.assets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OptionTest {
    @Test
    public void testEmpty() {
        var opt = new Option<String>();
        assertTrue(opt.isEmpty());
        assertFalse(opt.isNotEmpty());

        assertThrows(Exception.class, opt::get);
    }

    @Test
    public void testSome(){
        var opt = new Option<String>();
        opt.set("Hello");
        assertTrue(opt.isNotEmpty());
        assertEquals("Hello", opt.get());
    }


    @Test
    public void testClear(){
        var opt = new Option<String>();
        opt.set("Hello");
        assertTrue(opt.isNotEmpty());
        opt.unset();
        assertFalse(opt.isNotEmpty());
    }
}