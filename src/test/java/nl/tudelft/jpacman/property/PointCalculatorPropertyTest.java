package nl.tudelft.jpacman.property;

import net.jqwik.api.*;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import nl.tudelft.jpacman.points.PointCalculator;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PointCalculatorPropertyTest {

    @Property
    void noPointsAwardedOnGhostCollisionByDefault(@ForAll int baseScore) {
        PointCalculator calculator = mock(PointCalculator.class);
        Player player = mock(Player.class);
        Blinky ghost = mock(Blinky.class);

        doAnswer(invocation -> {
            when(player.getScore()).thenReturn(baseScore);
            return null;
        }).when(calculator).collidedWithAGhost(player, ghost);

        calculator.collidedWithAGhost(player, ghost);
        assertThat(player.getScore()).isEqualTo(baseScore);
    }
}
