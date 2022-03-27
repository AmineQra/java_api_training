package fr.lernejo.navy_battle;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import org.json.JSONObject;

import fr.lernejo.navy_battle.assets.Option;

public abstract class Server {
    protected final HttpClient client = HttpClient.newHttpClient();
    protected final Option<HttpServer> server =  new Option<>();

    public void startServer(int port, String connectURL) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.set(server);
        server.setExecutor(Executors.newSingleThreadExecutor());
        createContextes(server);
        server.start();
    }

    public void stopServer() {
        this.server.get().stop(0);
    }
    public abstract void createContextes(HttpServer server);

    //Send POST request
    public JSONObject sendPOSTRequest(String url, JSONObject obj) throws IOException, InterruptedException {
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(obj.toString()))
            .build();

        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }

    //Send GET request
    public JSONObject sendGETRequest(String url) throws IOException, InterruptedException {
        HttpRequest requeteGET = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Accept", "application/json")
            .GET()
            .build();

        var response = client.send(requeteGET, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }
}
