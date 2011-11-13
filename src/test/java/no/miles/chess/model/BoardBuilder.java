package no.miles.chess.model;

public class BoardBuilder {

    private Board board = Board.createEmpty();

    public BoardBuilder withPieceOn(Player player, PieceType type, Position position) {
        board.setPieceOn(new Piece(player, type), position);
        return this;
    }

    public BoardBuilder withBothKings() {
        board.setPieceOn(new Piece(Player.WHITE, PieceType.KING), Position.E1);
        board.setPieceOn(new Piece(Player.BLACK, PieceType.KING), Position.E8);
        return this;
    }

    public Board build() {
        return board;
    }
}
