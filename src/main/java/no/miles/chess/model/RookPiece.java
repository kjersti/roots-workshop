package no.miles.chess.model;

import java.util.Set;

public class RookPiece extends Piece {

    public RookPiece(Player player) {
        this(player, false);
    }

    protected RookPiece(Player player, boolean moved) {
        super(player, PieceType.ROOK, moved);
    }

    @Override
    Piece copy() {
        return new RookPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        return piecesInPath.isEmpty() && (move.isHorizontal() || move.isVertical());
    }
}
