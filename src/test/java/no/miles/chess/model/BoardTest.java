package no.miles.chess.model;

import org.junit.Before;
import org.junit.Test;

import static no.miles.chess.model.PieceType.*;
import static no.miles.chess.model.PieceType.KNIGHT;
import static no.miles.chess.model.PieceType.ROOK;
import static no.miles.chess.model.Position.*;
import static org.fest.assertions.Assertions.assertThat;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = Board.createInitial();
    }

    @Test
    public void sixteenPiecesOnEachSideWhenGameStarts() {
        assertThat(board.getAllPiecesFor(Player.WHITE).size()).isEqualTo(16);
        assertThat(board.getAllPiecesFor(Player.BLACK).size()).isEqualTo(16);
    }

    @Test
    public void moveWhitePawnOneForward() {
        board.makeMove(new Move(A2, A3));

        assertThat(board.hasNoPieceOn(A2)).isTrue();
        assertPieceOn(A3, Player.WHITE, PieceType.PAWN);
    }

    @Test
    public void createInitialBoardConfiguration() {

        assertPieceOn(Position.A1, Player.WHITE, ROOK);
        assertPieceOn(Position.B1, Player.WHITE, KNIGHT);
        assertPieceOn(Position.C1, Player.WHITE, BISHOP);
        assertPieceOn(Position.D1, Player.WHITE, QUEEN);
        assertPieceOn(Position.E1, Player.WHITE, KING);
        assertPieceOn(Position.F1, Player.WHITE, BISHOP);
        assertPieceOn(Position.G1, Player.WHITE, KNIGHT);
        assertPieceOn(Position.H1, Player.WHITE, ROOK);

        assertPawns(Player.WHITE, 2);
        assertPawns(Player.BLACK, 7);

        assertPieceOn(Position.A8, Player.BLACK, ROOK);
        assertPieceOn(Position.B8, Player.BLACK, KNIGHT);
        assertPieceOn(Position.C8, Player.BLACK, BISHOP);
        assertPieceOn(Position.D8, Player.BLACK, QUEEN);
        assertPieceOn(Position.E8, Player.BLACK, KING);
        assertPieceOn(Position.F8, Player.BLACK, BISHOP);
        assertPieceOn(Position.G8, Player.BLACK, KNIGHT);
        assertPieceOn(Position.H8, Player.BLACK, ROOK);

        for (Position p : Position.values()) {
            if (p.row > 2 && p.row < 7) {
                assertThat(board.hasNoPieceOn(p)).isTrue();
            }
        }
    }

    @Test
    public void moveShouldPutPieceOnNewPositionAndSetOldPositionToEmpty() {
        board.makeMove(new Move(Position.A2, Position.A3));
        assertPieceOn(Position.A3, Player.WHITE, PieceType.PAWN);
        assertThat(board.hasNoPieceOn(Position.A2)).isTrue();
    }

    @Test
    public void moveToOccupiedPositionShouldReplaceCurrentlyOccupyingPieceWithMovingPiece() {
        board = new BoardBuilder()
                .withPieceOn(Player.WHITE, PieceType.BISHOP, A3)
                .withPieceOn(Player.BLACK, PieceType.PAWN, E7)
                .build();

        board.makeMove(new Move(Position.A3, Position.E7));
        assertThat(board.getPieceOn(Position.E7).belongsTo(Player.WHITE)).isTrue();
        assertThat(board.getPieceOn(Position.E7).getType()).isEqualTo(PieceType.BISHOP);
    }

    private void assertPawns(Player player, int row) {
        for (Position p : Position.values()) {
            if (p.row == row) {
                assertPieceOn(p, player, PieceType.PAWN);
            }
        }
    }

    @Test
    public void toStringShouldReturnRepresentationOfBoard() throws Exception {
        Board board = Board.createInitial();

        String expected = "\n" +
                "    A   B   C   D   E   F   G   H\n" +
                "  --------------------------------\n" +
                "8 |bR |bN |bB |bQ |bK |bB |bN |bR |\n" +
                "  --------------------------------\n" +
                "7 |bx |bx |bx |bx |bx |bx |bx |bx |\n" +
                "  --------------------------------\n" +
                "6 |   |   |   |   |   |   |   |   |\n" +
                "  --------------------------------\n" +
                "5 |   |   |   |   |   |   |   |   |\n" +
                "  --------------------------------\n" +
                "4 |   |   |   |   |   |   |   |   |\n" +
                "  --------------------------------\n" +
                "3 |   |   |   |   |   |   |   |   |\n" +
                "  --------------------------------\n" +
                "2 |wx |wx |wx |wx |wx |wx |wx |wx |\n" +
                "  --------------------------------\n" +
                "1 |wR |wN |wB |wQ |wK |wB |wN |wR |\n" +
                "  --------------------------------\n";
        assertThat(board.toString()).isEqualTo(expected);
    }

    private void assertPieceOn(Position position, Player player, PieceType type) {
        Piece piece = board.getPieceOn(position);
        assertThat(piece.belongsTo(player)).isTrue();
        assertThat(piece.getType() == type).isTrue();
    }

}
