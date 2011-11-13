package no.miles.chess.model;

import java.util.Set;

public class NullPiece extends Piece {

    public NullPiece() {
        super(null, false);
    }

    @Override
    Piece copy() {
        return this;
    }

    @Override
    public boolean belongsTo(Player player) {
        return false;
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        return false;
    }

    boolean canCapture(Move move, Set<Piece> piecesInPath) {
        return false;
    }

    String getSymbol() {
        return "  ";
    }
}
