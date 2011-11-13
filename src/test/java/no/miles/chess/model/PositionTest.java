package no.miles.chess.model;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PositionTest {

    private Game game;

    @Before
    public void setup() {
        game = new Game(Board.createEmpty());
    }

    @Test
    public void shouldGiveAllPositionsInUpLeftPath() {
        Assertions.assertThat(game.calculatePositionsInPath(Position.C1, Position.A3)).contains(Position.B2);
    }

    @Test
    public void shouldGiveAllPositionsInUpRightPath() {
        assertThat(game.calculatePositionsInPath(Position.C1, Position.E3)).contains(Position.D2);
    }

    @Test
    public void shouldGiveNoPositionsOnDiscontinuousPaths() {
        assertThat(game.calculatePositionsInPath(Position.A1, Position.G3)).isEmpty();
    }

    @Test
    public void shouldGiveAllPositionsInDownPath() {
        assertThat(game.calculatePositionsInPath(Position.A5, Position.A3)).contains(Position.A4);
    }

    @Test
    public void shouldGiveAllPositionsInUpPath() {
        assertThat(game.calculatePositionsInPath(Position.C3, Position.C5)).contains(Position.C4);
    }

    @Test
    public void shouldGiveAllPositionsInRightPath() {
        assertThat(game.calculatePositionsInPath(Position.C3, Position.H3)).contains(Position.E3);
    }

    @Test
    public void shouldGiveAllPositionsInLeftPath() {
        assertThat(game.calculatePositionsInPath(Position.G6, Position.A6)).contains(Position.B6);
    }

    @Test
    public void pathShouldNotIncludeDestination() {
        assertThat(game.calculatePositionsInPath(Position.D4, Position.D6)).excludes(Position.D6);
    }
    
    @Test
    public void shouldIdentifyDiagonalMove() {
        assertThat(Position.A3.isDiagonalTo(Position.E7)).isTrue();
    }

    @Test
    public void shouldBeEmpty() {
        assertThat(game.calculatePositionsInPath(Position.A3, Position.E7))
                .contains(Position.B4)
                .contains(Position.C5)
                .contains(Position.D6)
                .excludes(Position.E7);
    }
}
