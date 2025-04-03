package nl.tudelft.jpacman.npc.ghost;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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

public class PinkyAITest {

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


    @Test
    void testPinkyNextAiMoveWest() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                "    SP"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Player player = (Player) Navigation.findUnitInBoard(Player.class, level.getBoard());
        player.setDirection(Direction.WEST);

        Ghost Pinky = (Ghost) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertNotNull(Pinky, "Pinky should be present on the board.");

        Optional<Direction> move = Pinky.nextAiMove();
        assertTrue(move.isPresent(), "Pinky should have a move available.");

        assertEquals(Direction.WEST, move.get(), "Pinky should move WEST.");
    }

    @Test
    void testPinkyNextAiMoveEast() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                "PS     "
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Player player = (Player) Navigation.findUnitInBoard(Player.class, level.getBoard());
        player.setDirection(Direction.EAST);

        Ghost Pinky = (Ghost) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertNotNull(Pinky, "Pinky should be present on the board.");

        Optional<Direction> move = Pinky.nextAiMove();
        assertTrue(move.isPresent(), "Pinky should have a move available.");

        assertEquals(Direction.EAST, move.get(), "Pinky should move EAST.");
    }

    @Test
    void testPinkyNextAiMoveNorth() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                " ",
                " ",
                " ",
                " ",
                "S",
                "P"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Player player = (Player) Navigation.findUnitInBoard(Player.class, level.getBoard());
        player.setDirection(Direction.NORTH);

        Ghost Pinky = (Ghost) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertNotNull(Pinky, "Pinky should be present on the board.");

        Optional<Direction> move = Pinky.nextAiMove();
        assertTrue(move.isPresent(), "Pinky should have a move available.");

        assertEquals(Direction.NORTH, move.get(), "Pinky should move NORTH.");
    }

    @Test
    void testPinkyNextAiMoveSouth() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                " ",
                "S",
                "P",
                " ",
                " ",
                " ",
                " "
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Player player = (Player) Navigation.findUnitInBoard(Player.class, level.getBoard());
        player.setDirection(Direction.SOUTH);

        Ghost Pinky = (Ghost) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertNotNull(Pinky, "Pinky should be present on the board.");

        Optional<Direction> move = Pinky.nextAiMove();
        assertTrue(move.isPresent(), "Pinky should have a move available.");

        assertEquals(Direction.SOUTH, move.get(), "Pinky should move SOUTH.");
    }


    /**
     * Test: No player present.
     * Map: "C  " (only Pinky) in one row.
     * Expected: No player, should not have a move.
     */
    @Test
    void testPinkyNoPlayer() {
        GhostMapParser parser = createGhostMapParser();
        // Map with only Pinky.
        List<String> map = Arrays.asList("S  ");
        Level level = parser.parseMap(map);
        // Do not register any player.

        Ghost Pinky = (Ghost) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertNotNull(Pinky, "Pinky should be present on the board.");

        Optional<Direction> move = Pinky.nextAiMove();
        assertFalse(move.isPresent(), "Without any player, Pinky should have no move available.");
    }

    /**
     * Test: Pinky and Player in the same square.
     * Map: "C  " (Pinky and Player together) in one row.
     * Expected: Shortest path is zero, so no move.
     */
    @Test
    void testPinkyAndPlayerSameSquare() {
        GhostMapParser parser = createGhostMapParser();

        List<String> map = Arrays.asList("S ");
        Level level = parser.parseMap(map);
        Ghost Pinky = (Ghost) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertNotNull(Pinky, "Pinky should be present on the board.");

        // Create a player and force it to occupy Pinky's square.
        PacManSprites sprites = new PacManSprites();
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        Player player = playerFactory.createPacMan();
        Square PinkySquare = Pinky.getSquare();
        player.occupy(PinkySquare);

        Optional<Direction> move = Pinky.nextAiMove();
        assertFalse(move.isPresent(), "If Pinky and player share the same square, there should be no move.");
    }

}
