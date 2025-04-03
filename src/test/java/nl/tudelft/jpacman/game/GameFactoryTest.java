package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameFactoryTest {

    private GameFactory gameFactory;
    private PlayerFactory mockPlayerFactory;
    private Level mockLevel;
    private PointCalculator mockPointCalculator;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        mockPlayerFactory = mock(PlayerFactory.class);
        mockLevel = mock(Level.class);
        mockPointCalculator = mock(PointCalculator.class);
        mockPlayer = mock(Player.class);

        when(mockPlayerFactory.createPacMan()).thenReturn(mockPlayer);
        gameFactory = new GameFactory(mockPlayerFactory);
    }

    @Test
    void testCreateSinglePlayerGameReturnsValidGame() {
        Game game = gameFactory.createSinglePlayerGame(mockLevel, mockPointCalculator);

        assertNotNull(game, "GameFactory should return a non-null game.");
        assertTrue(game instanceof SinglePlayerGame, "Game should be instance of SinglePlayerGame.");
        assertEquals(mockPlayer, game.getPlayers().get(0), "Game should contain the player created by PlayerFactory.");
        assertEquals(mockLevel, game.getLevel(), "Game should use the provided level.");
    }

    @Test
    void testGetPlayerFactoryReturnsCorrectFactory() {
        assertEquals(mockPlayerFactory, gameFactory.getPlayerFactory(),
                "getPlayerFactory() should return the original factory.");
    }

    @ParameterizedTest
    @MethodSource("provideGameComponents")
    void testCreateSinglePlayerGameParameterized(Level level, PointCalculator calculator) {
        PlayerFactory playerFactory = mock(PlayerFactory.class);
        Player player = mock(Player.class);
        when(playerFactory.createPacMan()).thenReturn(player);

        GameFactory factory = new GameFactory(playerFactory);
        Game game = factory.createSinglePlayerGame(level, calculator);

        assertNotNull(game, "Game should not be null.");
        assertTrue(game instanceof SinglePlayerGame, "Game should be a SinglePlayerGame.");
        assertEquals(player, game.getPlayers().get(0), "Player should match factory's output.");
        assertEquals(level, game.getLevel(), "Level should match the input level.");
    }

    private static Stream<Arguments> provideGameComponents() {
        return Stream.of(
                Arguments.of(mock(Level.class), mock(PointCalculator.class)),
                Arguments.of(mock(Level.class), mock(PointCalculator.class)),
                Arguments.of(mock(Level.class), mock(PointCalculator.class))
        );
    }
}
