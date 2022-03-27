package fr.lernejo.navy_battle.assets;

public enum MapCells {
    WATER("."),
    MISSED("-"),
    HIT("X"),
    BOAT("B");

    private final String letter;

    MapCells(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }
}
