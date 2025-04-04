package nl.tudelft.jpacman.board;


import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitSquarePropertyTest {

    @Property
    void unitShouldOccupySquareCorrectly(@ForAll("unitInstances") Unit unit, @ForAll("squareInstances") Square square) {
        unit.occupy(square);
        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).contains(unit);
    }

    @Provide
    Arbitrary<Unit> unitInstances() {
        return Arbitraries.of(new BasicUnit());
    }

    @Provide
    Arbitrary<Square> squareInstances() {
        return Arbitraries.of(new BasicSquare());
    }
}
