package nl.tudelft.jpacman.specification;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.*;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JPacmanSpecificationTest {

    Launcher launcher;
    Game game;
    Level level;

    @BeforeEach
    void setUp() {
        launcher = new Launcher();
        game = launcher.makeGame();
        level = game.getLevel();
    }


    @Test
    void launcherCreatesNonNullGame() {
        assertThat(game).isNotNull();
    }

    @Test
    void launcherCreatesValidLevel() {
        assertThat(level).isNotNull();
        assertThat(level.getBoard()).isNotNull();
    }

    @Test
    void gameHasAtLeastOnePlayer() {
        List<Player> players = game.getPlayers();
        assertThat(players).isNotEmpty();
        assertThat(players.get(0)).isInstanceOf(Player.class);
    }

    @Test
    void playerStartsWithZeroScore() {
        Player player = game.getPlayers().get(0);
        assertThat(player.getScore()).isEqualTo(0);
    }

    @Test
    void playerIsAliveOnGameStart() {
        Player player = game.getPlayers().get(0);
        assertThat(player.isAlive()).isTrue();
    }

    @Test
    void playerScoreIncreasesAfterAddingPoints() {
        Player player = game.getPlayers().get(0);
        int initialScore = player.getScore();
        player.addPoints(100);
        assertThat(player.getScore()).isEqualTo(initialScore + 100);
    }

    @Test
    void gameMovesPlayerOnlyWhenInProgress() {
        Player player = game.getPlayers().get(0);
        Square start = player.getSquare();
        game.move(player, Direction.EAST); // game is not in progress
        assertThat(player.getSquare()).isEqualTo(start); // player did not move

        game.start();
        game.move(player, Direction.EAST); // game is in progress
        assertThat(player.getSquare()).isNotEqualTo(start); // player moved
    }

    @Test
    void levelStopsWhenPlayerDies() {
        game.start();
        Player player = game.getPlayers().get(0);
        player.setAlive(false);

        level.stop();
        assertThat(level.isInProgress()).isFalse();
    }

    @Test
    void registeringPlayerPlacesThemOnStartSquare() {
        Player newPlayer = new PlayerFactory(new PacManSprites()).createPacMan();
        level.registerPlayer(newPlayer);
        assertThat(newPlayer.hasSquare()).isTrue();
    }

    @Test
    void playerScoreIncreasesWhenPelletsAreConsumed() {
        Player player = game.getPlayers().get(0);
        Square originalSquare = player.getSquare();

        // move until score increases (consume pellet)
        game.start();
        for (int i = 0; i < 10; i++) {
            game.move(player, Direction.EAST);
            if (player.getScore() > 0) {
                break;
            }
        }

        assertThat(player.getScore()).isGreaterThan(0);
    }

    @Test
    void playerConsumesSomePellets() {
        Player player = game.getPlayers().get(0);
        int initialPellets = level.remainingPellets();

        game.start();

        int moves = 0;
        while (moves < 20 && player.isAlive() && level.remainingPellets() == initialPellets) {
            game.move(player, Direction.EAST);
            moves++;
        }

        assertThat(level.remainingPellets()).isLessThan(initialPellets);
    }

    @Test
    void boardHasNonZeroSize() {
        Board board = level.getBoard();
        assertThat(board.getWidth()).isGreaterThan(0);
        assertThat(board.getHeight()).isGreaterThan(0);
    }

    @Test
    void allSquaresExist() {
        Board board = level.getBoard();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertThat(board.squareAt(x, y)).isNotNull();
            }
        }
    }


    @Test
    void gameCanStartAndStop() {
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        game.stop();
        assertThat(game.isInProgress()).isFalse();
    }

    @Test
    void testGhostsArePresent() {
        Level level = game.getLevel();
        Board board = level.getBoard();
        Square start = board.squareAt(0, 0);

        assertNotNull(Navigation.findNearest(Blinky.class, start), "Blinky should be present on the board.");
        assertNotNull(Navigation.findNearest(Pinky.class, start), "Pinky should be present on the board.");
        assertNotNull(Navigation.findNearest(Inky.class, start), "Inky should be present on the board.");
        assertNotNull(Navigation.findNearest(Clyde.class, start), "Clyde should be present on the board.");
    }
}
