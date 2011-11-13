package no.miles.chess.model;

import java.util.Set;

public class BishopPiece extends Piece {

    public BishopPiece(Player player) {
        this(player, false);
    }

    protected BishopPiece(Player player, boolean moved) {
        super(player, moved);
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

    boolean canCapture(Move move, Set<Piece> piecesInPath) {
        boolean canCapture = true;
        if (!canMove(move, piecesInPath)) {
            //Cannot attack position you cannot move to.
            canCapture = false;
        }
        return canCapture;
    }

    String getSymbol() {
        return belongsTo(Player.WHITE) ? "wB" : "bB";
    }
}
