package no.miles.chess.model;

import java.util.Set;

public class BishopPiece extends Piece {

    public BishopPiece(Player player) {
        this(player, false);
    }

    protected BishopPiece(Player player, boolean moved) {
        super(player, PieceType.BISHOP, moved);
    }

    @Override
    Piece copy() {
        return new BishopPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        //Bishops can move diagonally, when no other piece is blocking.
        return piecesInPath.isEmpty() && move.isDiagonal();

    }
}
