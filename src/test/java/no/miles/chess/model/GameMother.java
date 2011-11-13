package no.miles.chess.model;

public class GameMother {

    public static Game shortestGameEverRecorded() {
        Board board = new BoardBuilder()
                .withPieceOn(Player.WHITE, PieceType.QUEEN, Position.E5)
                .withPieceOn(Player.WHITE, PieceType.KING, Position.E1)
                .withPieceOn(Player.BLACK, PieceType.KING, Position.E7)
                .withPieceOn(Player.BLACK, PieceType.QUEEN, Position.D8)
                .withPieceOn(Player.BLACK, PieceType.PAWN, Position.D7)
                .withPieceOn(Player.BLACK, PieceType.PAWN, Position.F7)
                .withPieceOn(Player.BLACK, PieceType.BISHOP, Position.F8)
                .build();
        Game game = new Game(board);
        game.setCurrentPlayer(Player.BLACK);
        return game;
    }

}
