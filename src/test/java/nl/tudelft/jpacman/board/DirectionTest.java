package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    void testNorthDelta() {
        Direction north = Direction.NORTH;
        assertEquals(0, north.getDeltaX(), "NORTH should have deltaX of 0");
        assertEquals(-1, north.getDeltaY(), "NORTH should have deltaY of -1");
    }

    @Test
    void testSouthDelta() {
        Direction south = Direction.SOUTH;
        assertEquals(0, south.getDeltaX(), "SOUTH should have deltaX of 0");
        assertEquals(1, south.getDeltaY(), "SOUTH should have deltaY of 1");
    }

    @Test
    void testEastDelta() {
        Direction east = Direction.EAST;
        assertEquals(1, east.getDeltaX(), "EAST should have deltaX of 1");
        assertEquals(0, east.getDeltaY(), "EAST should have deltaY of 0");
    }

    @Test
    void testWestDelta() {
        Direction west = Direction.WEST;
        assertEquals(-1, west.getDeltaX(), "WEST should have deltaX of -1");
        assertEquals(0, west.getDeltaY(), "WEST should have deltaY of 0");
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testDeltaRange(Direction direction) {
        int dx = direction.getDeltaX();
        int dy = direction.getDeltaY();

        assertTrue(dx >= -1 && dx <= 1, "Delta X must be between -1 and 1");
        assertTrue(dy >= -1 && dy <= 1, "Delta Y must be between -1 and 1");
        assertNotEquals(0, dx + dy, "Direction must move in at least one axis");
    }
}
