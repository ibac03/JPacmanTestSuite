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

public class BlinkyAITest {

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
     * Test: Blinky's behaviour when he is under the shyness threshold.
     * Map: "CP "
     * Expected: Shortest path from Blinky (0,0) to player (1,0) is [EAST], so it should return [EAST].
     */
    @Test
    void testBlinkyBasicNextAiMove() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList("BP ");
        Level level = parser.parseMap(map);
        registerPlayer(level);

        Ghost Blinky = (Ghost) Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertNotNull(Blinky, "Blinky should be present on the board.");

        Optional<Direction> move = Blinky.nextAiMove();
        assertTrue(move.isPresent(), "Blinky should have a move available.");
        // From (0,0) to (1,0): shortest path is EAST
        assertEquals(Direction.EAST, move.get(), "Blinky should move EAST.");
    }

    /**
     * Test: No player present.
     * Map: "C  " (only Blinky) in one row.
     * Expected: No player, should not have a move.
     */
    @Test
    void testBlinkyNoPlayer() {
        GhostMapParser parser = createGhostMapParser();
        // Map with only Blinky.
        List<String> map = Arrays.asList("B  ");
        Level level = parser.parseMap(map);
        // Do not register any player.

        Ghost Blinky = (Ghost) Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertNotNull(Blinky, "Blinky should be present on the board.");

        Optional<Direction> move = Blinky.nextAiMove();
        assertFalse(move.isPresent(), "Without any player, Blinky should have no move available.");
    }

    /**
     * Test: Blinky and Player in the same square.
     * Map: "C  " (Blinky and Player together) in one row.
     * Expected: Shortest path is zero, so no move.
     */
    @Test
    void testBlinkyAndPlayerSameSquare() {
        GhostMapParser parser = createGhostMapParser();

        List<String> map = Arrays.asList("B ");
        Level level = parser.parseMap(map);
        Ghost Blinky = (Ghost) Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertNotNull(Blinky, "Blinky should be present on the board.");

        // Create a player and force it to occupy Blinky's square.
        PacManSprites sprites = new PacManSprites();
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        Player player = playerFactory.createPacMan();
        Square BlinkySquare = Blinky.getSquare();
        player.occupy(BlinkySquare);

        Optional<Direction> move = Blinky.nextAiMove();
        assertFalse(move.isPresent(), "If Blinky and player share the same square, there should be no move.");
    }

}
