package no.miles.chess.model;

public class BoardBuilder {

    private Board board = Board.createEmpty();

    public BoardBuilder withPieceOn(Player player, PieceType type, Position position) {
        switch (type) {
            case PAWN: {
                withPawnPieceOn(player, position);
                break;
            }
            case ROOK: {
                withRookOn(player, position);
                break;
            }
            case KNIGHT: {
                withKnightOn(player, position);
                break;
            }
            case BISHOP: {
                withBishopOn(player, position);
                break;
            }
            case QUEEN: {
                withQueenOn(player, position);
                break;
            }
            case KING: {
                withKingOn(player, position);
                break;
            }
            default:{
                board.setPieceOn(new Piece(player, type), position);
            }
        }
        return this;
    }

    private void withKingOn(Player player, Position position) {
        board.setPieceOn(new KingPiece(player), position);
    }

    private void withQueenOn(Player player, Position position) {
        board.setPieceOn(new QueenPiece(player), position);
    }

    private void withBishopOn(Player player, Position position) {
        board.setPieceOn(new BishopPiece(player), position);
    }

    private void withKnightOn(Player player, Position position) {
        board.setPieceOn(new KnightPiece(player), position);
    }

    private void withRookOn(Player player, Position position) {
        board.setPieceOn(new RookPiece(player), position);
    }

    private void withPawnPieceOn(Player player, Position position) {
        board.setPieceOn(new PawnPiece(player), position);
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
