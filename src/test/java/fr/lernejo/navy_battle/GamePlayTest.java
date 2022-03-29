package fr.lernejo.navy_battle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import fr.lernejo.navy_battle.assets.FireResult;

public class GamePlayTest {
    @Test
    public void testGame() {
        var game = new Game();

        for (int i = 0; game.localMapShipLeft() && i < 100; i++) {
            var coordinates = game.getNextPlaceToHit();
            game.setFireResult(coordinates, game.hit(coordinates));
        }

        assertFalse(game.localMapShipLeft());
    }

    @Test
    public void testEmptyMap() {
        var game = new Game();

        assertThrows(Exception.class, () -> {
            for (int i = 0; i < 200; i++) {
                var coordinates = game.getNextPlaceToHit();
                game.setFireResult(coordinates, FireResult.MISS);
            }
        });
    }
}