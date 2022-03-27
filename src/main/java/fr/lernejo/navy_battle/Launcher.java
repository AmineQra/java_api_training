package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URISyntaxException;
public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException, NumberFormatException, URISyntaxException {
        try {
            if (args.length == 0) {
                System.err.println("Bad arguments, try giving [port] [url].");
                System.exit(-1);
            }

            int serverPort = Integer.parseInt(args[0]);
            System.out.println("Waiting for opponent... (port: " + serverPort+")");

            new ServerHandler().startServer(serverPort, (args.length > 1 ? args[1] : null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
