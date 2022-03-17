package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;
import java.net.http.HttpClient;
public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 1){
            int port = Integer.parseInt(args[0]);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/ping", new PingHandler());
            server.createContext("/api/start/game", new PostHandler());
            server.setExecutor(executor);
            server.start();
        }
        else if (args.length == 2){
            int clientport = Integer.parseInt(args[0]);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(URI.create(args[1] + "/api/start/game"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + clientport + "\", \"message\":\"hello\"}"))
                .build();
            HttpResponse<String> response = client.send(requetePost, BodyHandlers.ofString());
            System.out.println(response.body());
        }
        else{
            System.out.println("Bad argument");
            System.exit(-1);
        }
        
    }
}
