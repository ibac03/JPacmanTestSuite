package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SuspendResumeAcceptanceTest {

    private Launcher launcher;
    private Game game;

    @BeforeEach
    void setup() {
        launcher = new Launcher();
        launcher.launch();
        game = launcher.getGame();
    }

    @Test
    void testGameSuspendsWhenStopClicked() {
        // GIVEN the game has started
        game.start();
        assertTrue(game.isInProgress(), "Game should be in progress after start.");

        // WHEN the player clicks the Stop button (simulate by calling game.stop())
        game.stop();

        // THEN the game is not in progress
        assertFalse(game.isInProgress(), "Game should not be in progress after stop.");
    }

    @Test
    void testGameStartsWhenStartCalled() {
        // GIVEN the game is initially stopped
        assertFalse(game.isInProgress(), "Game should not be in progress before starting.");

        // WHEN the player clicks the Start button (simulate by calling game.start())
        game.start();

        // THEN the game is in progress
        assertTrue(game.isInProgress(), "Game should be in progress after starting.");
    }
}
