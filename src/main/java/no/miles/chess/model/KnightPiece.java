package no.miles.chess.model;

import java.util.Set;

public class KnightPiece extends Piece {

    public KnightPiece(Player player) {
        this(player, false);
    }

    protected KnightPiece(Player player, boolean moved) {
        super(player, PieceType.KNIGHT, moved);
    }

    @Override
    Piece copy() {
        return new KnightPiece(player, moved);
    }

    @Override
    boolean canMove(Move move, Set<Piece> piecesInPath) {
        //Knights can jump in a knightly manner.
        int horizontal = move.horizontalDistance();
        int vertical = move.verticalDistance();
        return (horizontal == 2 && vertical == 1)
                || (horizontal == 1 && vertical == 2);

    }
}
