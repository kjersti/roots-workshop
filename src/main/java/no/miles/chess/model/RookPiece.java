package no.miles.chess.model;

import java.util.Set;

public class RookPiece extends Piece {

    public RookPiece(Player player) {
        this(player, false);
    }

    protected RookPiece(Player player, boolean moved) {
        super(player, moved);
    }

    @Override
    Piece copy() {
        return new RookPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        return piecesInPath.isEmpty() && (move.isHorizontal() || move.isVertical());
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
        return belongsTo(Player.WHITE) ? "wR" : "bR";
    }
}
