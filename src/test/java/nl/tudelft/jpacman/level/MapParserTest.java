package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MapParserTest {

    private BoardFactory boardCreator;
    private LevelFactory levelCreator;
    private MapParser mapParser;

    @BeforeEach
    public void setUp() {
        // Manually create the mocks
        boardCreator = Mockito.mock(BoardFactory.class);
        levelCreator = Mockito.mock(LevelFactory.class);
        // Instantiate MapParser with the mocks
        mapParser = new MapParser(levelCreator, boardCreator);
    }

    @Test
    public void testParseMapWithSingleWall() {
        // Arrange: Create a dummy wall Square.
        Square dummyWall = mock(Square.class);
        when(boardCreator.createWall()).thenReturn(dummyWall);

        // Stub createBoard to return a dummy Board.
        Board dummyBoard = mock(Board.class);
        when(boardCreator.createBoard(any(Square[][].class))).thenReturn(dummyBoard);

        // Stub createLevel to return a dummy Level.
        Level dummyLevel = mock(Level.class);
        when(levelCreator.createLevel(any(Board.class), anyList(), anyList())).thenReturn(dummyLevel);

        // Create a simple map: a single cell containing '#'
        char[][] map = new char[][]{{'#'}};

        // Act: Parse the map.
        Level result = mapParser.parseMap(map);

        // Assert: The returned level should be our dummy level.
        assertEquals(dummyLevel, result);

        // Verify that createWall was called exactly once.
        verify(boardCreator, times(1)).createWall();

        // Verify that createBoard was called with any grid.
        verify(boardCreator, times(1)).createBoard(any(Square[][].class));

        // Verify that createLevel was called with the dummy board and empty lists for ghosts and start positions.
        verify(levelCreator, times(1))
                .createLevel(eq(dummyBoard),
                        argThat(list -> list.isEmpty()),
                        argThat(list -> list.isEmpty()));
    }
}
