package no.miles.chess.model;

import java.util.Set;

public class Piece {

    private final Player player;
    private final PieceType type;
    private boolean moved;

    public Piece(Player player, PieceType type) {
        this(player, type, false);
    }

    private Piece(Player player, PieceType type, boolean moved) {
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

    public boolean isMoved() {
        return moved;
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
        if (getType() == PieceType.PAWN) {
            return distance == 1 || (!isMoved() && distance == 2);
        } else if (getType() == PieceType.KING) {
            return distance == 1;
        }
        return false;
    }

    boolean canMove(Move move, Set<Piece> piecesInPath) {

        // Cannot move to a new position if there are other pieces in the
        // way - unless when piece is a knight
        switch (getType()) {
            case PAWN: {
                //Pawns can move one square forwards, or two if it hasn't moved before, and no other piece is blocking.
                return piecesInPath.isEmpty()
                        && (move.getFrom().isVerticalTo(move.getTo()) && validDistance(move.verticalDistanceWithDirection(getPlayer())));
            }
            case ROOK: {
                //Rooks can move horizontal or vertical, when no other piece is blocking.
                return piecesInPath.isEmpty() && (move.getFrom().isHorizontalTo(move.getTo()) || move.getFrom().isVerticalTo(move.getTo()));
            }
            case KNIGHT: {
                //Knights can jump in a knightly manner.
                int horizontal = move.getFrom().horizontalDistanceTo(move.getTo());
                int vertical = move.getFrom().verticalDistanceTo(move.getTo());
                return (horizontal == 2 && vertical == 1)
                        || (horizontal == 1 && vertical == 2);
            }
            case BISHOP: {
                //Bishops can move diagonally, when no other piece is blocking.
                return piecesInPath.isEmpty() && move.getFrom().isDiagonalTo(move.getTo());
            }
            case QUEEN: {
                //Queens can move all over, when no other piece is blocking.
                return piecesInPath.isEmpty() &&
                        (move.getFrom().isVerticalTo(move.getTo()) || move.getFrom().isHorizontalTo(move.getTo()) || move.getFrom().isDiagonalTo(move.getTo()));
            }
            case KING: {
                //King can move in all directions, but only one square at a time.
                return (move.getFrom().isVerticalTo(move.getTo()) && validDistance(move.getFrom().verticalDistanceTo(move.getTo())))
                        || (move.getFrom().isDiagonalTo(move.getTo()) && validDistance(move.getFrom().verticalDistanceTo(move.getTo())))
                        || (move.getFrom().isHorizontalTo(move.getTo()) && validDistance(move.getFrom().horizontalDistanceTo(move.getTo())));
            }
            default: {
                throw new IllegalArgumentException("Non-supported piece type: " + getType());
            }
        }
    }
}