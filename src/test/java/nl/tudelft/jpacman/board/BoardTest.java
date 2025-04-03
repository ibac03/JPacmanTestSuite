package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private Square[][] grid;

    @BeforeEach
    void setUp() {
        grid = new Square[3][2]; // width = 3, height = 2
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 2; y++) {
                grid[x][y] = new BasicSquare();
            }
        }
        board = new Board(grid);
    }

    @Test
    void invariantShouldReturnTrue() {
        assertTrue(board.invariant());
    }

    @Test
    void getWidthShouldReturnCorrectValue() {
        assertEquals(3, board.getWidth());
    }

    @Test
    void getHeightShouldReturnCorrectValue() {
        assertEquals(2, board.getHeight());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "2, 1",
            "1, 0"
    })
    void squareAtShouldReturnCorrectSquare(int x, int y) {
        Square s = board.squareAt(x, y);
        assertNotNull(s);
        assertEquals(grid[x][y], s);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, true",
            "2, 1, true",
            "-1, 0, false",
            "3, 1, false",
            "1, 2, false"
    })
    void withinBordersShouldReturnExpectedResult(int x, int y, boolean expected) {
        assertEquals(expected, board.withinBorders(x, y));
    }
}
