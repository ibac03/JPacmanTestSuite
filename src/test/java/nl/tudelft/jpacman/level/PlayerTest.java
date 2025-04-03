package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private Player player;
    private AnimatedSprite mockDeathSprite;
    private EnumMap<Direction, Sprite> spriteMap;

    @BeforeEach
    void setUp() {
        mockDeathSprite = mock(AnimatedSprite.class);
        spriteMap = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) {
            spriteMap.put(dir, mock(Sprite.class));
        }
        player = new Player(spriteMap, mockDeathSprite);
    }

    @Test
    void testIsAliveInitiallyTrue() {
        assertTrue(player.isAlive(), "Player should be alive by default after creation.");
    }

    @Test
    void testSetAliveFalse() {
        player.setAlive(false);
        assertFalse(player.isAlive(), "Player should not be alive after setAlive(false).");
    }

    @Test
    void testSetAliveTrueAfterDeath() {
        player.setAlive(false);  // kill the player
        player.setAlive(true);   // bring them back to life
        assertTrue(player.isAlive(), "Player should be alive again after setAlive(true).");
    }

    @Test
    void testGetKillerInitiallyNull() {
        assertNull(player.getKiller(), "Killer should be null by default.");
    }

    @Test
    void testSetAndGetKiller() {
        Unit mockGhost = mock(Unit.class);
        player.setKiller(mockGhost);
        assertEquals(mockGhost, player.getKiller(), "Killer should be set correctly.");
    }

    @Test
    void testScoreInitiallyZero() {
        assertEquals(0, player.getScore(), "Initial score should be 0.");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 100})
    void testAddPoints(int points) {
        player.addPoints(points);
        assertEquals(points, player.getScore(), "Score should reflect points added.");
    }

    @Test
    void testAddPointsMultipleTimes() {
        player.addPoints(10);
        player.addPoints(20);
        assertEquals(30, player.getScore(), "Score should accumulate correctly.");
    }
}
