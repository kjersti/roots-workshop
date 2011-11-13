package no.miles.chess.model;

import java.util.Set;

public class PawnPiece extends Piece {

    public PawnPiece(Player player) {
        this(player, false);
    }

    protected PawnPiece(Player player, boolean moved) {
        super(player, moved);
    }

    @Override
    Piece copy() {
        return new PawnPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        //Pawns can move one square forwards, or two if it hasn't moved before, and no other piece is blocking.
        int distance = move.verticalDistanceWithDirection(player);
        return piecesInPath.isEmpty()
                && (move.isVertical() && (distance == 1 || (!moved && distance == 2)));
    }

    boolean canCapture(Move move, Set<Piece> piecesInPath) {
        return move.isDiagonal() && move.verticalDistanceWithDirection(player) == 1;
    }

    String getSymbol() {
        return belongsTo(Player.WHITE) ? "wx" : "bx";
    }
}
