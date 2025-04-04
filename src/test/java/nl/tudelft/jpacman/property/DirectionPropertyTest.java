package nl.tudelft.jpacman.property;

import net.jqwik.api.*;
import nl.tudelft.jpacman.board.Direction;
import static org.assertj.core.api.Assertions.assertThat;

public class DirectionPropertyTest {

    @Property
    void deltaShouldNotBothBeZero(@ForAll Direction direction) {
        boolean bothZero = direction.getDeltaX() == 0 && direction.getDeltaY() == 0;
        assertThat(bothZero).isFalse();
    }

    @Property
    void directionEnumHasValidName(@ForAll Direction direction) {
        assertThat(direction.name()).isNotEmpty();
    }
}
