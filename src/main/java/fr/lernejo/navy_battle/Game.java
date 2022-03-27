package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.assets.*;

public class Game {
    private final GameMap localMap;
    private final GameMap remoteMap;
    private final Option<GameStatus> status = new Option<>(GameStatus.ONGOING);

    public Game() {
        localMap = new GameMap(true);
        remoteMap = new GameMap(false);
    }

    public void wonGame() {
        status.set(GameStatus.WON);

        System.out.println("I told you i would win it!");
        System.out.println("Adversary map:");
        remoteMap.printMap();

        System.out.println("Our map:");
        localMap.printMap();
        System.out.println("Good Job! --Game Finished");
    }

    public Coordinates getNextPlaceToHit() {
        return remoteMap.getNextPlaceToHit();
    }

    public void setFireResult(Coordinates coordinates, FireResult result) {
        if (result == FireResult.MISS)
            remoteMap.setCell(coordinates, MapCells.MISSED);
        else
            remoteMap.setCell(coordinates, MapCells.HIT);
    }

    public boolean localMapShipLeft() {
        return localMap.hasShipLeft();
    }

    public FireResult hit(Coordinates coordinates) {
        return localMap.hit(coordinates);
    }

    public GameStatus getStatus() {
        if (!localMap.hasShipLeft())
            status.set(GameStatus.LOST);
        return status.get();
    }
}
