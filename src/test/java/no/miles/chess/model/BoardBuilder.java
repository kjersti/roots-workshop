package no.miles.chess.model;

public class BoardBuilder {

    private Board board = Board.createEmpty();

    BoardBuilder withKingOn(Player player, Position position) {
        board.setPieceOn(new KingPiece(player), position);
        return this;
    }

    BoardBuilder withQueenOn(Player player, Position position) {
        board.setPieceOn(new QueenPiece(player), position);
        return this;
    }

    BoardBuilder withBishopOn(Player player, Position position) {
        board.setPieceOn(new BishopPiece(player), position);
        return this;
    }

    BoardBuilder withKnightOn(Player player, Position position) {
        board.setPieceOn(new KnightPiece(player), position);
        return this;
    }

    BoardBuilder withRookOn(Player player, Position position) {
        board.setPieceOn(new RookPiece(player), position);
        return this;
    }

    BoardBuilder withPawnOn(Player player, Position position) {
        board.setPieceOn(new PawnPiece(player), position);
        return this;
    }

    public BoardBuilder withBothKings() {
        withKingOn(Player.WHITE, Position.E1);
        withKingOn(Player.BLACK, Position.E8);
        return this;
    }

    public Board build() {
        return board;
    }
}
