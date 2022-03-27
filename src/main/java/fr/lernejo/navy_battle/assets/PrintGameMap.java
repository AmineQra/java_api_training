package fr.lernejo.navy_battle.assets;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PrintGameMap extends BaseGameMap {
    public PrintGameMap(boolean fill) {
        super(fill);

        if (fill)
            printMap();
    }

    public void printMap() {
        System.out.println(" --------------- ");
        for (MapCells[] row : getMap()) {
            System.out.println(Arrays.stream(row).map(MapCells::getLetter).collect(Collectors.joining(" ")));
        }
        System.out.println(" --------------- ");
    }

    public boolean hasShipLeft() {
        for (var row : getMap()) {
            if (Arrays.stream(row).anyMatch(s -> s == MapCells.BOAT))
                return true;
        }
        return false;
    }

    public void setCell(Coordinates coordinates, MapCells newStatus) {
        getMap()[coordinates.getX()][coordinates.getY()] = newStatus;
    }
}
