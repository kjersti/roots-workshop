package no.miles.chess.model;

import java.util.Set;

public class KingPiece extends Piece {

    public KingPiece(Player player) {
        this(player, false);
    }

    protected KingPiece(Player player, boolean moved) {
        super(player, PieceType.KING, moved);
    }

    @Override
    Piece copy() {
        return new KingPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        //King can move in all directions, but only one square at a time.
        return (move.isVertical() && validDistance(move.verticalDistance()))
                || (move.isDiagonal() && validDistance(move.verticalDistance()))
                || (move.isHorizontal() && validDistance(move.horizontalDistance()));

    }
}
