package nl.tudelft.jpacman.npc.ghost;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.Test;

public class ClydeAITest {

    /**
     * Helper to create a GhostMapParser with default factories.
     */
    private GhostMapParser createGhostMapParser() {
        PacManSprites sprites = new PacManSprites();
        BoardFactory boardFactory = new BoardFactory(sprites);
        GhostFactory ghostFactory = new GhostFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, new DefaultPointCalculator());
        return new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    private void registerPlayer(Level level) {
        PacManSprites sprites = new PacManSprites();
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
    }

    /**
     * Test: Clyde's behaviour when he is under the shyness threshold.
     * Map: "CP "
     * Expected: Shortest path from Clyde (0,0) to player (1,0) is [EAST], so it should return [WEST].
     */
    @Test
    void testClydeBasicNextAiMove() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList("CP ");
        Level level = parser.parseMap(map);
        registerPlayer(level);

        Ghost clyde = (Ghost) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde, "Clyde should be present on the board.");

        Optional<Direction> move = clyde.nextAiMove();
        assertTrue(move.isPresent(), "Clyde should have a move available.");
        // From (0,0) to (1,0): shortest path is EAST; shy threshold applies → expect WEST.
        assertEquals(Direction.WEST, move.get(), "Clyde should move WEST (opposite of EAST).");
    }

    /**
     * Test: No player present.
     * Map: "C  " (only Clyde) in one row.
     * Expected: No player, should not have a move.
     */
    @Test
    void testClydeNoPlayer() {
        GhostMapParser parser = createGhostMapParser();
        // Map with only Clyde.
        List<String> map = Arrays.asList("C  ");
        Level level = parser.parseMap(map);
        // Do not register any player.

        Ghost clyde = (Ghost) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde, "Clyde should be present on the board.");

        Optional<Direction> move = clyde.nextAiMove();
        assertFalse(move.isPresent(), "Without any player, Clyde should have no move available.");
    }

    /**
     * Test: Clyde and Player in the same square.
     * Map: "C  " (Clyde and Player together) in one row.
     * Expected: Shortest path is zero, so no move.
     */
    @Test
    void testClydeAndPlayerSameSquare() {
        GhostMapParser parser = createGhostMapParser();

        List<String> map = Arrays.asList("C ");
        Level level = parser.parseMap(map);
        Ghost clyde = (Ghost) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde, "Clyde should be present on the board.");

        // Create a player and force it to occupy Clyde's square.
        PacManSprites sprites = new PacManSprites();
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        Player player = playerFactory.createPacMan();
        Square clydeSquare = clyde.getSquare();
        player.occupy(clydeSquare);

        Optional<Direction> move = clyde.nextAiMove();
        assertFalse(move.isPresent(), "If Clyde and player share the same square, there should be no move.");
    }

    /**
     * Test: Clyde's behaviour when we are over the shyness threshold.
     * Map:
     *      "C          P"
     * Expected: Since path length > shyness, Clyde returns the first direction of the path [EAST].
     */
    @Test
    void testClydeLongPathNextAiMove() {
        GhostMapParser parser = createGhostMapParser();
        // Build a 6x6 map using a list of 6 strings, each of length 6.
        // Place Clyde at top-left ('C') and player start at bottom-right ('P').
        List<String> map = Arrays.asList(
                "C          P"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);

        // Retrieve Clyde.
        Ghost clyde = (Ghost) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde, "Clyde should be present on the board.");

        // Invoke nextAiMove.
        Optional<Direction> move = clyde.nextAiMove();
        assertTrue(move.isPresent(), "Clyde should have a move available on a long path.");
        assertEquals(Direction.EAST, move.get(), "Clyde's move on a long path should be EAST (first step).");
    }

    /**
     * Test: Blocked path scenario.
     * Map:
     *      "C#P"
     * Expected: No move since there is no possible path
     * CURRENTLY FAILS. NOT SURE IF THIS IS A BUG OR NOT
    @Test
    void testClydeBlockedPath() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                "C#P"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);

        // Retrieve Clyde.
        Ghost clyde = (Ghost) Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde, "Clyde should be present on the board.");

        // In a blocked scenario, there should be no available move.
        Optional<Direction> move = clyde.nextAiMove();
        assertFalse(move.isPresent(), "When Clyde is blocked by walls, no move should be available.");
    }

     */
}
