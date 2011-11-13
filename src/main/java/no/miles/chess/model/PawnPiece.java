package no.miles.chess.model;

import java.util.Set;

public class PawnPiece extends Piece {

    public PawnPiece(Player player) {
        this(player, false);
    }

    protected PawnPiece(Player player, boolean moved) {
        super(player, PieceType.PAWN, moved);
    }

    @Override
    Piece copy() {
        return new PawnPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        //Pawns can move one square forwards, or two if it hasn't moved before, and no other piece is blocking.
        return piecesInPath.isEmpty()
                && (move.isVertical() && validDistance(move.verticalDistanceWithDirection(getPlayer())));
    }
}
