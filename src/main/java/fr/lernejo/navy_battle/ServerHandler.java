package fr.lernejo.navy_battle;

import java.io.IOException;
import java.util.UUID;

import com.sun.net.httpserver.HttpServer;

import org.json.JSONObject;

import fr.lernejo.navy_battle.assets.Coordinates;
import fr.lernejo.navy_battle.assets.FireResult;
import fr.lernejo.navy_battle.assets.Option;
import fr.lernejo.navy_battle.assets.ServerInfo;

public class ServerHandler extends Server {
    private final Option<ServerInfo> localServer = new Option<>();
    private final Option<ServerInfo> remoteServer = new Option<>();
    protected final Option<Game> gamePlay = new Option<>();

    @Override
    public void startServer(int port, String connectURL) throws IOException {
        localServer.set(new ServerInfo(
            UUID.randomUUID().toString(),
            "http://localhost:" + port,
            "I will Win, you are so too easy!"
        ));

        if (connectURL != null)
            new Thread(() -> this.requestStart(connectURL)).start();

        super.startServer(port, connectURL);
    }

    @Override
    public void createContextes(HttpServer server) {
        server.createContext("/ping", new PingHandler());
        server.createContext("/api/game/start", s -> startGame(new RequestHandler(s)));
        server.createContext("/api/game/fire", s -> handleFire(new RequestHandler(s)));
    }


    public void startGame(RequestHandler handler) throws IOException {
        try {
            remoteServer.set(ServerInfo.fromJSON(handler.getJSONObject()));
            gamePlay.set(new Game());
            System.out.println("Will fight against " + remoteServer.get().getUrl());

            handler.sendJSON(202, localServer.get().toJSON());

            fire();

        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }

    public void requestStart(String server) {
        try {
            gamePlay.set(new Game());
            this.remoteServer.set(new ServerInfo("first", server, "good luck in the game"));
            var response = sendPOSTRequest(server + "/api/game/start", this.localServer.get().toJSON());

            this.remoteServer.set(ServerInfo.fromJSON(response).withURL(server));
            System.out.println("Will fight against " + remoteServer.get().getUrl());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start game!");
        }
    }

    public void fire() throws IOException, InterruptedException {
        Coordinates coordinates = gamePlay.get().getNextPlaceToHit();
        var response =
            sendGETRequest(remoteServer.get().getUrl() + "/api/game/fire?cell=" + coordinates.toString());

        if (!response.getBoolean("shipLeft")) {
            gamePlay.get().wonGame();
            return;
        }

        gamePlay.get().setFireResult(coordinates, FireResult.fromAPI(response.getString("consequence")));
    }

    public void handleFire(RequestHandler handler) throws IOException {
        try {
            var pos = new Coordinates(handler.getQueryParameter("cell"));
            handler.sendJSON(200, new JSONObject().put("consequence", gamePlay.get().hit(pos).toAPI())
                .put("shipLeft", gamePlay.get().localMapShipLeft()));

            if (!gamePlay.get().localMapShipLeft()) {
                System.out.println("You lost!");
                return;
            }

            fire();
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
}
