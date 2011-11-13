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
        Assertions.assertThat(new Move(Position.C1, Position.A3).calculatePositionsInPath()).contains(Position.B2);
    }

    @Test
    public void shouldGiveAllPositionsInUpRightPath() {
        assertThat(new Move(Position.C1, Position.E3).calculatePositionsInPath()).contains(Position.D2);
    }

    @Test
    public void shouldGiveNoPositionsOnDiscontinuousPaths() {
        assertThat(new Move(Position.A1, Position.G3).calculatePositionsInPath()).isEmpty();
    }

    @Test
    public void shouldGiveAllPositionsInDownPath() {
        assertThat(new Move(Position.A5, Position.A3).calculatePositionsInPath()).contains(Position.A4);
    }

    @Test
    public void shouldGiveAllPositionsInUpPath() {
        assertThat(new Move(Position.C3, Position.C5).calculatePositionsInPath()).contains(Position.C4);
    }

    @Test
    public void shouldGiveAllPositionsInRightPath() {
        assertThat(new Move(Position.C3, Position.H3).calculatePositionsInPath()).contains(Position.E3);
    }

    @Test
    public void shouldGiveAllPositionsInLeftPath() {
        assertThat(new Move(Position.G6, Position.A6).calculatePositionsInPath()).contains(Position.B6);
    }

    @Test
    public void pathShouldNotIncludeDestination() {
        assertThat(new Move(Position.D4, Position.D6).calculatePositionsInPath()).excludes(Position.D6);
    }
    
    @Test
    public void shouldIdentifyDiagonalMove() {
        assertThat(Position.A3.isDiagonalTo(Position.E7)).isTrue();
    }

    @Test
    public void shouldBeEmpty() {
        assertThat(new Move(Position.A3, Position.E7).calculatePositionsInPath())
                .contains(Position.B4)
                .contains(Position.C5)
                .contains(Position.D6)
                .excludes(Position.E7);
    }
}
