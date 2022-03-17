package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;
public class Launcher {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        HttpServer server = HttpServer.create(new InetSocketAddress(9876), 0);
        server.createContext("/ping", new PingHandler());
        server.setExecutor(null);
        server.start();
    }
}
