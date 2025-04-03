package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SinglePlayerGameTest {

    private Player mockPlayer;
    private Level mockLevel;
    private PointCalculator mockCalculator;
    private SinglePlayerGame game;

    @BeforeEach
    void setUp() {
        mockPlayer = mock(Player.class);
        mockLevel = mock(Level.class);
        mockCalculator = mock(PointCalculator.class);
        game = new SinglePlayerGame(mockPlayer, mockLevel, mockCalculator);
    }

    @Test
    void testGetPlayersReturnsSinglePlayer() {
        List<Player> players = game.getPlayers();
        assertEquals(1, players.size(), "There should be exactly one player.");
        assertEquals(mockPlayer, players.get(0), "The player returned should be the one passed in.");
    }

    @Test
    void testGetLevelReturnsCorrectLevel() {
        assertEquals(mockLevel, game.getLevel(), "Level returned should match the one passed in.");
    }

    @Test
    void testPlayerIsRegisteredToLevel() {
        verify(mockLevel).registerPlayer(mockPlayer);
    }

    @ParameterizedTest
    @MethodSource("provideGameComponents")
    void testConstructorWithDifferentInputs(Player player, Level level, PointCalculator calculator) {
        SinglePlayerGame spg = new SinglePlayerGame(player, level, calculator);
        assertEquals(player, spg.getPlayers().get(0));
        assertEquals(level, spg.getLevel());
        verify(level).registerPlayer(player);
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> provideGameComponents() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(mock(Player.class), mock(Level.class), mock(PointCalculator.class)),
                org.junit.jupiter.params.provider.Arguments.of(mock(Player.class), mock(Level.class), mock(PointCalculator.class))
        );
    }
}
