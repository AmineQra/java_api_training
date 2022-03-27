package fr.lernejo.navy_battle.assets;

import java.util.ArrayList;
import java.util.List;

public class GameMap extends PrintGameMap {
    private final List<Coordinates> positionsToTest = new ArrayList<>();
    private final List<Coordinates> lightHitPositions = new ArrayList<>();

    public GameMap(boolean fill) {
        super(fill);
        for (int i = 0; i < getWidth(); i++) {
            for (int j = i % 2; j < getHeight(); j += 2) {
                lightHitPositions.add(new Coordinates(i, j));
            }
        }
    }

    public void setCell(Coordinates coordinates, MapCells newStatus) {
        super.setCell(coordinates, newStatus);

        if (newStatus == MapCells.HIT) {
            positionsToTest.addAll(List.of(
                coordinates.plus(-1, 0),
                coordinates.plus(0, -1),
                coordinates.plus(1, 0),
                coordinates.plus(0, 1)
            ));
        }
    }


    public Coordinates getNextPlaceToHit() {
        Coordinates coordinates = null;
        if (!positionsToTest.isEmpty())
            coordinates = fireAroundSuccessfulHit();

        if (coordinates == null && !lightHitPositions.isEmpty())
            coordinates = lightHit();

        if (coordinates == null)
            coordinates = bruteForceHit();

        return coordinates;
    }

    private Coordinates fireAroundSuccessfulHit() {
        while (positionsToTest.size() > 0) {
            var c = positionsToTest.remove(0);
            if (getCell(c) == MapCells.WATER) return c;
        }
        return null;
    }

    private Coordinates lightHit() {
        while (lightHitPositions.size() > 0) {
            var c = lightHitPositions.remove(0);
            if (getCell(c) == MapCells.WATER) return c;
        }
        return null;
    }

    private Coordinates bruteForceHit() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (getCell(i, j) == MapCells.WATER)
                    return new Coordinates(i, j);
            }
        }

        throw new RuntimeException("FAILED TO HIT!!");
    }

    public FireResult hit(Coordinates coordinates) {
        if (getCell(coordinates) != MapCells.BOAT)
            return FireResult.MISS;

        var first = getBoats().stream().filter(s -> s.contains(coordinates)).findFirst();
        assert (first.isPresent());
        first.get().remove(coordinates);

        setCell(coordinates, MapCells.HIT);

        return first.get().isEmpty() ? FireResult.SUNK : FireResult.HIT;
    }
}
