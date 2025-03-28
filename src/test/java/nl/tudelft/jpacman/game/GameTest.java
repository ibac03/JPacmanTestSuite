package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

// STRUCTURAL TESTING

class GameTest {

    private Game game;

    @Mock
    private Level level;

    @Mock
    private PointCalculator pointCalculator;

    @Mock
    private Player player;

    // Stimulate the game
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        game = new Game(pointCalculator) {
            @Override
            public List<Player> getPlayers() {
                return Collections.singletonList(mock(Player.class));
            }
            @Override
            public Level getLevel() {
                return level;
            }
        };
    }

    // Tests for start
    @Test
    void start_GameInProgress() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);

        // Start the game
        game.start();
        assertTrue(game.isInProgress());

        // Start the game again
        game.start();

        // Verify addObserver() and start() were only called once even though start was pressed again
        verify(level, times(1)).addObserver(game);
        verify(level, times(1)).start();
    }

    @Test
    void start_NoPlayersAlive() {
        when(level.isAnyPlayerAlive()).thenReturn(false);
        game.start();
        // Assert game will not start if isAnyPlayerAlive() is false
        assertFalse(game.isInProgress());
    }

    @Test
    void start_NoPelletsRemaining() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(0);
        game.start();
        // Assert game will not start if remainingPellets() is 0
        assertFalse(game.isInProgress());
    }

    @Test
    void start_PlayersAliveAndPelletsRemaining() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();
        assertTrue(game.isInProgress());
        // Verify addObserver() and start() are called
        verify(level).addObserver(game);
        verify(level).start();
    }

    // Tests for stop

    @Test
    void stop_GameInProgress() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();
        game.stop();
        // Ensures the game is stopped
        assertFalse(game.isInProgress());
        verify(level).stop();
    }

    @Test
    void stop_GameNotInProgress() {
        // Ensures getLevel().stop() is never called
        game.stop();
        assertFalse(game.isInProgress());
        verify(level, never()).stop();
    }

    // Tests for move

    @Test
    void move_GameInProgress() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();
        Direction direction = Direction.NORTH;
        game.move(player, direction);
        // Verify if functions in move() are called
        verify(level).move(player, direction);
        verify(pointCalculator).pacmanMoved(player, direction);
    }

    @Test
    void move_GameNotInProgress() {
        Direction direction = Direction.NORTH;
        game.move(player, direction);
        // Verify if functions in move() are NOT called
        verify(level, never()).move(player, direction);
        verify(pointCalculator, never()).pacmanMoved(player, direction);
    }

    // Tests for levelWon
    @Test
    void levelWon() {
        game.levelWon();
        assertFalse(game.isInProgress());
        verify(level, never()).stop();
    }

    // Tests for LevelLost
    @Test
    void levelLost() {
        game.levelLost();
        assertFalse(game.isInProgress());
        verify(level, never()).stop();
    }
}