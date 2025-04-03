package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OccupantTest {

    private Unit unit;

    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    @Test
    void noStartSquare() {
        assertThat(unit.hasSquare()).isFalse();
        // Do NOT call unit.getSquare() here — it asserts false and fails
    }

    @Test
    void unitOccupiesTargetSquare() {
        Square square = new BasicSquare();
        unit.occupy(square); // ✅ use occupy

        assertThat(unit.hasSquare()).isTrue();
        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).contains(unit);
    }

    @Test
    void unitLeavesPreviousSquareOnMove() {
        Square square1 = new BasicSquare();
        Square square2 = new BasicSquare();

        unit.occupy(square1);
        assertThat(unit.getSquare()).isEqualTo(square1);
        assertThat(square1.getOccupants()).contains(unit);

        unit.occupy(square2);
        assertThat(unit.getSquare()).isEqualTo(square2);
        assertThat(square2.getOccupants()).contains(unit);
        assertThat(square1.getOccupants()).doesNotContain(unit);
    }

    @Test
    void reoccupyingSameSquareDoesNotDuplicate() {
        Square square = new BasicSquare();
        unit.occupy(square); // First occupy
        unit.occupy(square); // Re-occupy same square

        assertThat(unit.getSquare()).isEqualTo(square);
        long count = square.getOccupants().stream().filter(u -> u == unit).count();
        assertThat(count).isEqualTo(1); // Only one instance
    }

}
