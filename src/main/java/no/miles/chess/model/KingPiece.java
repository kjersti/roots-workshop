package no.miles.chess.model;

import java.util.Set;

public class KingPiece extends Piece {

    public KingPiece(Player player) {
        this(player, false);
    }

    protected KingPiece(Player player, boolean moved) {
        super(player, moved);
    }

    @Override
    Piece copy() {
        return new KingPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        //King can move in all directions, but only one square at a time.
        return (move.isVertical() && move.verticalDistance() == 1)
                || (move.isDiagonal() && move.verticalDistance() == 1)
                || (move.isHorizontal() && move.horizontalDistance() == 1);

    }

    boolean canCapture(Move move, Set<Piece> piecesInPath) {
        boolean canCapture = true;
        if (!canMove(move, piecesInPath)) {
            //Cannot attack position you cannot move to.
            canCapture = false;
        }
        return canCapture;
    }

    String getSymbol() {
        return belongsTo(Player.WHITE) ? "wK" : "bK";
    }
}
