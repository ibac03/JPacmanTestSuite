package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class GhostCollisionAITest {

    private GhostMapParser parser;

    @BeforeEach
    void setup() {
        PacManSprites sprites = new PacManSprites();
        BoardFactory boardFactory = new BoardFactory(sprites);
        GhostFactory ghostFactory = new GhostFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, new DefaultPointCalculator());
        parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    private void registerPlayer(Level level) {
        PacManSprites sprites = new PacManSprites();
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
    }

    @Test
    void ghostDoesNotWalkIntoWall_Blinky() {
        List<String> map = Arrays.asList(
                "#B#",
                "#P#"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Ghost blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        Optional<Direction> move = blinky.nextAiMove();

        assertThat(move).isPresent();
        assertThat(move.get()).isNotEqualTo(Direction.WEST);
        assertThat(move.get()).isNotEqualTo(Direction.EAST);
    }

    @Test
    void ghostDoesNotWalkIntoWall_Clyde() {
        List<String> map = Arrays.asList(
                "#C#",
                "#P#"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);
        Ghost clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> move = clyde.nextAiMove();

        assertThat(move).isPresent();
        assertThat(move.get()).isNotEqualTo(Direction.WEST);
        assertThat(move.get()).isNotEqualTo(Direction.EAST);
    }

    @Test
    void ghostMovesWhenPossible_Pinky() {
        List<String> map = Arrays.asList(
                "SP ",
                "###"
        );
        Level level = parser.parseMap(map);
        registerPlayer(level);

        Ghost pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        Optional<Direction> move = pinky.nextAiMove();
        assertThat(move).isPresent();
    }
}
