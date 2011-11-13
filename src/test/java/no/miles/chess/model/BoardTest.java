package no.miles.chess.model;

import org.junit.Before;
import org.junit.Test;

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
        assertPieceOn(A3, Player.WHITE, PawnPiece.class);
    }

    @Test
    public void createInitialBoardConfiguration() {

        assertPieceOn(Position.A1, Player.WHITE, RookPiece.class);
        assertPieceOn(Position.B1, Player.WHITE, KnightPiece.class);
        assertPieceOn(Position.C1, Player.WHITE, BishopPiece.class);
        assertPieceOn(Position.D1, Player.WHITE, QueenPiece.class);
        assertPieceOn(Position.E1, Player.WHITE, KingPiece.class);
        assertPieceOn(Position.F1, Player.WHITE, BishopPiece.class);
        assertPieceOn(Position.G1, Player.WHITE, KnightPiece.class);
        assertPieceOn(Position.H1, Player.WHITE, RookPiece.class);

        assertPawns(Player.WHITE, 2);
        assertPawns(Player.BLACK, 7);

        assertPieceOn(Position.A8, Player.BLACK, RookPiece.class);
        assertPieceOn(Position.B8, Player.BLACK, KnightPiece.class);
        assertPieceOn(Position.C8, Player.BLACK, BishopPiece.class);
        assertPieceOn(Position.D8, Player.BLACK, QueenPiece.class);
        assertPieceOn(Position.E8, Player.BLACK, KingPiece.class);
        assertPieceOn(Position.F8, Player.BLACK, BishopPiece.class);
        assertPieceOn(Position.G8, Player.BLACK, KnightPiece.class);
        assertPieceOn(Position.H8, Player.BLACK, RookPiece.class);

        for (Position p : Position.values()) {
            if (p.row > 2 && p.row < 7) {
                assertThat(board.hasNoPieceOn(p)).isTrue();
            }
        }
    }

    @Test
    public void moveShouldPutPieceOnNewPositionAndSetOldPositionToEmpty() {
        board.makeMove(new Move(Position.A2, Position.A3));
        assertPieceOn(Position.A3, Player.WHITE, PawnPiece.class);
        assertThat(board.hasNoPieceOn(Position.A2)).isTrue();
    }

    @Test
    public void moveToOccupiedPositionShouldReplaceCurrentlyOccupyingPieceWithMovingPiece() {
        board = new BoardBuilder()
                .withBishopOn(Player.WHITE, A3)
                .withPawnOn(Player.BLACK, E7)
                .build();

        board.makeMove(new Move(Position.A3, Position.E7));
        assertThat(board.getPieceOn(Position.E7).belongsTo(Player.WHITE)).isTrue();
        assertThat(board.getPieceOn(Position.E7)).isInstanceOf(BishopPiece.class);
    }

    private void assertPawns(Player player, int row) {
        for (Position p : Position.values()) {
            if (p.row == row) {
                assertPieceOn(p, player, PawnPiece.class);
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

    private void assertPieceOn(Position position, Player player, Class<? extends Piece> type) {
        Piece piece = board.getPieceOn(position);
        assertThat(piece.belongsTo(player)).isTrue();
        assertThat(piece).isInstanceOf(type);
    }

}
