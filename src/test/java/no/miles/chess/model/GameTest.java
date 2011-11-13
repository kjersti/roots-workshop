package no.miles.chess.model;

import org.junit.Before;
import org.junit.Test;

import static no.miles.chess.model.Position.*;
import static org.fest.assertions.Assertions.assertThat;

public class GameTest {

    private Game game;
    private Board board;

    @Before
    public void setUp() throws Exception {
        board = Board.createInitial();
        game = new Game(board);
    }

    @Test
    public void whiteMakesFirstMove() {
        assertThat(game.currentPlayer()).isEqualTo(Player.WHITE);
    }

    @Test
    public void blackMakesSecondMove() {
        game.move(new Move(Position.E2, Position.E3));
        assertThat(game.currentPlayer()).isEqualTo(Player.BLACK);
    }

    /***********************
     * MOVEMENT TESTS      *
     ***********************/
    @Test
    public void cannotMoveToOccupiedPosition() {
        assertThat(game.canMove(new Move(Position.E8, Position.D8))).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void canOnlyMoveOwnPieces() {
        game = new Game(Board.createInitial());
        game.move(new Move(Position.A7, Position.A5));
    }

    @Test
    public void cannotMoveFromEmptySquare() {
        assertThat(game.canMove(new Move(A4, A5))).isFalse();
    }

    /***********************
     * PAWN TESTS          *
     ***********************/
    @Test
    public void whitePawnCanMoveOneSquareForwards() {
        assertThat(game.canMove(new Move(A2, A3))).isTrue();
    }

    @Test
    public void blackPawnCanMoveOneSquareForwards() {
        game.setCurrentPlayer(Player.BLACK);
        assertThat(game.canMove(new Move(A7, A6))).isTrue();
    }

    @Test
    public void pawnCanMoveTwoSquaresForwardsInFirstMove() {
        assertThat(game.canMove(new Move(A2, A4))).isTrue();
    }

    @Test
    public void pawnCanOnlyMoveOneSquareAtTheTimeAfterItHasMovedOnce() {
        Game game = new Game(new BoardBuilder()
                .withBothKings()
                .withPawnOn(Player.WHITE, A4)
                .build()
        );

        game.move(new Move(A4, A5));
        game.setCurrentPlayer(Player.WHITE);

        assertThat(game.canMove(new Move(A5, A6))).isTrue();
        assertThat(game.canMove(new Move(A5, A7))).isFalse();
    }

    @Test
    public void pawnCannotMoveBackwards() {
        board = new BoardBuilder()
                .withBothKings()
                .withPawnOn(Player.WHITE, A3)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(A3, A2))).isFalse();
    }

    @Test
    public void pawnCannotMoveDiagonally() {
        assertThat(game.canMove(new Move(A2, B3))).isFalse();
    }

    @Test
    public void pawnCannotMoveSideways() {
        board = new BoardBuilder()
                .withBothKings()
                .withPawnOn(Player.WHITE, A3)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(A3, B3))).isFalse();
    }

    @Test
    public void pawnCannotJumpOtherPieces() {
        board = new BoardBuilder()
                .withBothKings()
                .withPawnOn(Player.WHITE, C2)
                .withPawnOn(Player.BLACK, C3)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(C2, C4))).isFalse();
    }

    @Test
    public void pawnCannotMoveMoreThanTwoSquares() {
        assertThat(game.canMove(new Move(A2, A5))).isFalse();
    }

    @Test
    public void cannotMoveToOccupiedSquare() throws Exception {
        Game game = new Game(new BoardBuilder()
                            .withBothKings()
                            .withRookOn(Player.BLACK, C5)
                            .withRookOn(Player.WHITE, C4)
                            .build()
        );

        assertThat(game.canMake(new Move(C4, C5))).isFalse();
    }

    /***********************
     * ROOK TESTS          *
     ***********************/
    @Test
    public void rookCanMoveVerticallyAndHorisontally() {
        board = new BoardBuilder()
                .withBothKings()
                .withRookOn(Player.WHITE, A3)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(A3, Position.H3))).isTrue();
        assertThat(game.canMove(new Move(A3, Position.A6))).isTrue();
    }

    @Test
    public void rookCannotMoveDiagonally() {
        board = new BoardBuilder()
                .withBothKings()
                .withRookOn(Player.WHITE, A1)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(A1, C3))).isFalse();
    }

    @Test
    public void rookCannotJumpOverOtherPieces() {
        assertThat(game.canMove(new Move(A1, A3))).isFalse();
    }

    /***********************
     * KNIGHT TESTS        *
     ***********************/
    @Test
    public void knightCanMoveInLShape() {

        board = new BoardBuilder()
                .withBothKings()
                .withKnightOn(Player.WHITE, E4)
                .build();
        game = new Game(board);

        assertThat(game.canMove(new Move(E4, D2))).isTrue();
        assertThat(game.canMove(new Move(E4, D6))).isTrue();
        assertThat(game.canMove(new Move(E4, C3))).isTrue();
        assertThat(game.canMove(new Move(E4, C5))).isTrue();
        assertThat(game.canMove(new Move(E4, F2))).isTrue();
        assertThat(game.canMove(new Move(E4, F6))).isTrue();
        assertThat(game.canMove(new Move(E4, G3))).isTrue();
        assertThat(game.canMove(new Move(E4, G5))).isTrue();
    }

    @Test
    public void knightCannotMoveForwards() {
        board = new BoardBuilder()
                .withBothKings()
                .withKnightOn(Player.WHITE, A1)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(A1, A2))).isFalse();
    }

    @Test
    public void knightCannotMoveDiagonally() {
        board = new BoardBuilder()
                .withBothKings()
                .withKnightOn(Player.WHITE, B1)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(B1, D3))).isFalse();
    }

    /***********************
     * BISHOP TESTS        *
     ***********************/
    @Test
    public void bishopCanMoveDiagonally() {
        board = new BoardBuilder()
                .withBothKings()
                .withBishopOn(Player.WHITE, D6)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(D6, G3))).isTrue();
        assertThat(game.canMove(new Move(D6, A3))).isTrue();
        assertThat(game.canMove(new Move(D6, H2))).isTrue();
        assertThat(game.canMove(new Move(D6, E7))).isTrue();
    }
    
    @Test
    public void bishopCannotMoveVertically() {
        board = new BoardBuilder()
                .withBothKings()
                .withBishopOn(Player.WHITE, E3)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(E3, E5))).isFalse();
    }

    @Test
    public void bishopCannotMoveHorizontally() {
        board = new BoardBuilder()
                .withBothKings()
                .withBishopOn(Player.WHITE, A3)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(A3, H3))).isFalse();
    }

    @Test
    public void bishopsCannotJumpOverOtherPieces() {
        assertThat(game.canMove(new Move(C1, A3))).isFalse();
    }

    /***********************
     * QUEEN TESTS         *
     ***********************/
    @Test
    public void queenCanMoveHorizontally() {
        board = new BoardBuilder().withBothKings()
                .withQueenOn(Player.WHITE, E4)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(E4, A4))).isTrue();
        assertThat(game.canMove(new Move(E4, H4))).isTrue();
    }

    @Test
    public void queenCanMoveVertically() {
        board = new BoardBuilder().withBothKings()
                .withQueenOn(Player.WHITE, E4)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(E4, E7))).isTrue();
        assertThat(game.canMove(new Move(E4, E2))).isTrue();
    }

    @Test
    public void queenCanMoveDiagonally() {
        board = new BoardBuilder().withBothKings()
                .withQueenOn(Player.WHITE, E4)
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(E4, H1))).isTrue();
        assertThat(game.canMove(new Move(E4, H7))).isTrue();
        assertThat(game.canMove(new Move(E4, C2))).isTrue();
        assertThat(game.canMove(new Move(E4, C6))).isTrue();
    }

    @Test
    public void queenCannotJumpOverOtherPieces() {
        assertThat(game.canMove(new Move(D1, D3))).isFalse();
    }

    /***********************
     * KING TESTS          *
     ***********************/
    @Test
    public void kingCanMoveOneSquareInAnyDirection() {
        game = new Game(new BoardBuilder()
                .withKingOn(Player.WHITE, E4).build());

        assertThat(game.canMove(new Move(E4, E5))).isTrue();
        assertThat(game.canMove(new Move(E4, E3))).isTrue();
        assertThat(game.canMove(new Move(E4, D5))).isTrue();
        assertThat(game.canMove(new Move(E4, D4))).isTrue();
        assertThat(game.canMove(new Move(E4, D3))).isTrue();
        assertThat(game.canMove(new Move(E4, F5))).isTrue();
        assertThat(game.canMove(new Move(E4, F4))).isTrue();
        assertThat(game.canMove(new Move(E4, F3))).isTrue();
    }

    @Test
    public void kingCannotMoveMoreThanOneSquare() {
        board = new BoardBuilder()
                .withBothKings()
                .build();
        game = new Game(board);
        assertThat(game.canMove(new Move(E1, E3))).isFalse();
    }

    /***********************
     * CAPTURE TESTS       *
     ***********************/
    @Test
    public void canCaptureEnemyPiece() {
        board = new BoardBuilder()
                .withBothKings()
                .withBishopOn(Player.WHITE, A3)
                .withPawnOn(Player.BLACK, E7)
                .build();
        game = new Game(board);
        assertThat(game.canCapture(new Move(Position.A3, Position.E7))).isTrue();
    }

    @Test
    public void cannotCaptureOnEmptySquare() {
        assertThat(game.canCapture(new Move(Position.B1, Position.C3))).isFalse();
    }

    @Test
    public void cannotCaptureOwnPieces() throws Exception {
        Game game = new Game(new BoardBuilder()
                .withBothKings()
                .withRookOn(Player.WHITE, A4)
                .withPawnOn(Player.WHITE, A6)
                .build()
            );

        assertThat(game.canCapture(new Move(A4, A6))).isFalse();
    }

    @Test
    public void cannotCaptureWithOpponentPiece() {
                Game game = new Game(new BoardBuilder()
                .withBothKings()
                .withRookOn(Player.WHITE, A4)
                .withRookOn(Player.BLACK, A6)
                .build()
            );

        game.setCurrentPlayer(Player.BLACK);
        assertThat(game.canCapture(new Move(A4, A6))).isFalse();
        assertThat(game.canCapture(new Move(A6, A4))).isTrue();
    }

    @Test
    public void pawnCapturesDiagonallyForwards() {
        board = new BoardBuilder()
                .withBothKings()
                .withPawnOn(Player.WHITE, D4)
                .withPawnOn(Player.BLACK, E5)
                .build();
        game = new Game(board);
        assertThat(game.canCapture(new Move(Position.D4, Position.E5))).isTrue();
    }

    @Test
    public void pawnCannotCaptureDiagonallyBackwards() {
        board = new BoardBuilder()
                .withBothKings()
                .withPawnOn(Player.WHITE, D5)
                .withPawnOn(Player.BLACK, E4)
                .build();
        game = new Game(board);
        assertThat(game.canCapture(new Move(Position.D5, Position.E4))).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void pawnCannotCaptureForwards() {
        Game game = new Game(new BoardBuilder().withBothKings()
                .withPawnOn(Player.WHITE, G3)
                .withPawnOn(Player.BLACK, G4)
                .build());
        game.move(new Move(Position.G3, Position.G4));
    }

    /***********************
     * CHECK MATE TESTS    *
     ***********************/
    @Test
    public void playerIsInCheckWhenThreatenedByEnemyPiece() {
        game = GameMother.shortestGameEverRecorded();

        assertThat(game.isCurrentPlayerInCheck()).isTrue();
    }

    @Test
    public void playerCannotMoveKingToPutItselfInMate() {
        Game game = new Game(new BoardBuilder()
                .withKingOn(Player.WHITE, D1)
                .withKingOn(Player.BLACK, D8)
                .withQueenOn(Player.WHITE, C4)
                .build());

        game.setCurrentPlayer(Player.BLACK);
        assertThat(game.canMove(new Move(D8, C8))).isFalse();
    }

    @Test
    public void playerCannotMoveInterposingPieceToPlaceItselfInMate() {
        Game game = new Game(new BoardBuilder()
                .withKingOn(Player.WHITE, D1)
                .withKingOn(Player.BLACK, D8)
                .withRookOn(Player.BLACK, D6)
                .withQueenOn(Player.WHITE, D4)
                .build());

        assertThat(game.canMove(new Move(D4, C4))).isFalse();
    }

    @Test
    public void playerIsCheckMateWhenKingIsThreatenedAndHasNoEscape() {
        game = GameMother.shortestGameEverRecorded();

        assertThat(game.isPlayerInCheckMate(Player.BLACK)).isTrue();
    }

    /***********************
     * CHECK TESTS         *
     ***********************/
    @Test
    public void playerIsCheckWhenKingIsThreatenedAndCanEscape() {
        game = new Game(new BoardBuilder()
                .withKingOn(Player.WHITE, E1)
                .withPawnOn(Player.WHITE, E4)
                .withQueenOn(Player.WHITE, E5)
                .withKingOn(Player.BLACK, E7)
                .withPawnOn(Player.BLACK, D6)
                .build());

        game.setCurrentPlayer(Player.BLACK);
        assertThat(game.isCurrentPlayerInCheck()).isTrue();
        assertThat(game.isPlayerInCheckMate(Player.BLACK)).isFalse();
    }

    @Test
    public void playerIsNotInCheckWhenGameStarts() {
        game = new Game(Board.createInitial());
        assertThat(game.isCurrentPlayerInCheck()).isFalse();
    }

    @Test
    public void pawnCannotCaptureToPutCurrentPlayerInCheck() throws Exception {
        Game game = new Game(new BoardBuilder()
                        .withKingOn(Player.BLACK, C8)
                        .withPawnOn(Player.BLACK, C7)
                        .withKingOn(Player.WHITE, A1)
                        .withRookOn(Player.WHITE, C5)
                        .withPawnOn(Player.WHITE, D6)

                        .build()
        );
        game.setCurrentPlayer(Player.BLACK);

        assertThat(game.canMove(new Move(C7, D6))).isFalse();
    }

    /***********************
     * MISC TESTS          *
     ***********************/
    @Test
    public void shouldFindPiecesInPath() {
        board = new BoardBuilder()
                .withKingOn(Player.WHITE, D1)
                .withKingOn(Player.BLACK, D8)
                .withRookOn(Player.BLACK, D6)
                .withQueenOn(Player.WHITE, D4)
                .build();
        Game game = new Game(board);
        assertThat(board.piecesInPath(new Move(D1, D8)))
                .contains(board.getPieceOn(D4))
                .contains(board.getPieceOn(D6))
                .excludes(board.getPieceOn(D5));
    }

    @Test
    public void gameIsOverWhenAPlayerIsInCheckMate() {
        Game game = GameMother.shortestGameEverRecorded();

        assertThat(game.isCurrentPlayerInCheckMate()).isTrue();
        assertThat(game.getWinningColor()).isEqualTo(Player.WHITE);
    }
}