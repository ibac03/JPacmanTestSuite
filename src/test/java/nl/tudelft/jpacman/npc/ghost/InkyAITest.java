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

public class InkyAITest {

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
    void testInkyNextAiMoveEast() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                "I P   B"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Player player = (Player) Navigation.findUnitInBoard(Player.class, level.getBoard());
        player.setDirection(Direction.EAST);

        Ghost inky = (Ghost) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the board.");

        Optional<Direction> move = inky.nextAiMove();
        assertTrue(move.isPresent(), "Inky should have a move available.");
        assertEquals(Direction.EAST, move.get(), "Inky should move EAST.");
    }

    @Test
    void testInkyNextAiMoveWest() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                "  B   P I"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Player player = (Player) Navigation.findUnitInBoard(Player.class, level.getBoard());
        player.setDirection(Direction.WEST);

        Ghost inky = (Ghost) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the board.");

        Optional<Direction> move = inky.nextAiMove();
        assertTrue(move.isPresent(), "Inky should have a move available.");
        assertEquals(Direction.WEST, move.get(), "Inky should move WEST.");
    }

    @Test
    void testInkyNextAiMoveNorth() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                "  ",
                " B",
                "  ",
                "  ",
                "  ",
                " P",
                "  ",
                "  ",
                " I"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Player player = (Player) Navigation.findUnitInBoard(Player.class, level.getBoard());
        player.setDirection(Direction.NORTH);

        Ghost inky = (Ghost) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the board.");

        Optional<Direction> move = inky.nextAiMove();
        assertTrue(move.isPresent(), "Inky should have a move available.");
        assertEquals(Direction.NORTH, move.get(), "Inky should move NORTH.");
    }

    @Test
    void testInkyNextAiMoveSouth() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList(
                " I",
                "  ",
                "  ",
                "  ",
                "  ",
                " P",
                "  ",
                "  ",
                "  ",
                " B",
                "  "
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Player player = (Player) Navigation.findUnitInBoard(Player.class, level.getBoard());
        player.setDirection(Direction.SOUTH);

        Ghost inky = (Ghost) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the board.");

        Optional<Direction> move = inky.nextAiMove();
        assertTrue(move.isPresent(), "Inky should have a move available.");
        assertEquals(Direction.SOUTH, move.get(), "Inky should move SOUTH.");
    }

    @Test
    void testInkyNoPlayer() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList("I  ");
        Level level = parser.parseMap(map);

        Ghost inky = (Ghost) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the board.");

        Optional<Direction> move = inky.nextAiMove();
        assertFalse(move.isPresent(), "Without a player, Inky should not move.");
    }

    @Test
    void testInkyAndPlayerSameSquare() {
        GhostMapParser parser = createGhostMapParser();
        List<String> map = Arrays.asList("I ");
        Level level = parser.parseMap(map);

        Ghost inky = (Ghost) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the board.");

        PacManSprites sprites = new PacManSprites();
        Player player = new PlayerFactory(sprites).createPacMan();
        Square sameSquare = inky.getSquare();
        player.occupy(sameSquare);

        Optional<Direction> move = inky.nextAiMove();
        assertFalse(move.isPresent(), "Inky shouldn't move if he's on the same square as the player.");
    }


}
