package nl.tudelft.jpacman.level;

import net.jqwik.api.*;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PlayerPropertyTest {

    @Provide
    Arbitrary<Player> playerProvider() {
        return Arbitraries.integers().between(1, 5).map(i -> {
            EnumMap<Direction, Sprite> spriteMap = new EnumMap<>(Direction.class);
            for (Direction dir : Direction.values()) {
                spriteMap.put(dir, mock(Sprite.class));
            }
            AnimatedSprite deathSprite = mock(AnimatedSprite.class);
            return new Player(spriteMap, deathSprite);
        });
    }

    @Property
    void scoreNeverDecreases(@ForAll("playerProvider") Player player, @ForAll("positivePoints") int points) {
        int oldScore = player.getScore();
        player.addPoints(points);
        assertThat(player.getScore()).isGreaterThanOrEqualTo(oldScore);
    }

    @Provide
    Arbitrary<Integer> positivePoints() {
        return Arbitraries.integers().between(1, 100);
    }

    @Property
    void reviveResetsDeath(@ForAll("playerProvider") Player player) {
        player.setAlive(false);
        player.setAlive(true);
        assertThat(player.isAlive()).isTrue();
        assertThat(player.getKiller()).isNull();
    }
}
