package no.miles.chess.model;

import java.util.Set;

public class QueenPiece extends Piece {

    public QueenPiece(Player player) {
        this(player, false);
    }

    protected QueenPiece(Player player, boolean moved) {
        super(player, moved);
    }

    @Override
    Piece copy() {
        return new QueenPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        //Queens can move all over, when no other piece is blocking.
        return piecesInPath.isEmpty() &&
                (move.isVertical() || move.isHorizontal() || move.isDiagonal());

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
        return belongsTo(Player.WHITE) ? "wQ" : "bQ";
    }
}
