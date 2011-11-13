package no.miles.chess.model;

import java.util.Set;

public class Piece {

    public static final Piece NONE = new NullPiece();

    protected final Player player;
    protected final PieceType type;
    protected boolean moved;

    public Piece(Player player, PieceType type) {
        this(player, type, false);
    }

    protected Piece(Player player, PieceType type, boolean moved) {
        this.player = player;
        this.type = type;
        this.moved = moved;
    }

    Piece copy() {
        return new Piece(player, type, moved);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }

    public PieceType getType() {
        return type;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public String toString() {
        return "{" +
                "player=" + player +
                ", type=" + type +
                ", moved=" + moved +
                '}';
    }

    //This maps to the filename for the image of the piece.
    public String getName() {
        return player + "_" + type;
    }

    boolean validDistance(int distance) {
        // Pawns can move two squares forward when they make their
        // first move in a game; otherwise one square
        if (type == PieceType.PAWN) {
            return distance == 1 || (!moved && distance == 2);
        } else if (type == PieceType.KING) {
            return distance == 1;
        }
        return false;
    }

    boolean canMove(Move move, Set<Piece> piecesInPath) {

        // Cannot move to a new position if there are other pieces in the
        // way - unless when piece is a knight
        switch (type) {
            default: {
                throw new IllegalArgumentException("Non-supported piece type: " + getType());
            }
        }
    }

    boolean canCapture(Move move, Set<Piece> piecesInPath) {
        boolean canCapture = true;
        if (type == PieceType.PAWN) {
            // Pawns attack diagonally forwards only
            canCapture = move.isDiagonal() && move.verticalDistanceWithDirection(player) == 1;
        } else {
            if (!canMove(move, piecesInPath)) {
                //Cannot attack position you cannot move to.
                canCapture = false;
            }
        }
        return canCapture;
    }

}