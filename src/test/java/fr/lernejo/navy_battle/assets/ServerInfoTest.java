package fr.lernejo.navy_battle.assets;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerInfoTest {
    @Test
    void testIt() {
        var one = new ServerInfo("id", "url", "message");
        assertEquals("id", one.getId());
        assertEquals("url", one.getUrl());
        assertEquals("message", one.getMessage());
    }

    @Test
    void testWithURL() {
        var one = new ServerInfo("id", "url", "message");
        assertEquals("id", one.getId());
        assertEquals("url", one.getUrl());
        assertEquals("message", one.getMessage());

        var two = one.withURL("http://other.fr/");
        assertEquals("id", two.getId());
        assertEquals("http://other.fr/", two.getUrl());
        assertEquals("message", two.getMessage());
    }

    @Test
    void testJSON() {
        JSONObject in = new JSONObject("{\"id\": \"my_id\", \"url\":\"my_url\",\"message\":\"my_message\"}");
        var srv = ServerInfo.fromJSON(in);
        assertEquals("my_id", srv.getId());
        assertEquals("my_url", srv.getUrl());
        assertEquals("my_message", srv.getMessage());

        assertEquals(srv.toJSON().toString(), in.toString());
    }
}