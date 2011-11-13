package no.miles.chess.model;

import java.util.HashSet;
import java.util.Set;

public class Move {
    private final Position from;
    private final Position to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    //A path is the set of positions in a straight line from the from position to the to position
    Set<Position> calculatePositionsInPath() {
        Set<Position> range = new HashSet<Position>();

        //If move is neither vertical, horizontal or diagonal,
        //it isn't really a path, it is more like a jump
        if ((isVertical() || isHorizontal() || isDiagonal())) {

            int totalLength = isHorizontal() ? horizontalDistance() : verticalDistance();
            int length = totalLength - 1;

            int column = from.column - 1;
            int row = from.row;
            while (length > 0) {

                if (from.isBelow(to)) {
                    row += 1;
                } else if (to.isBelow(from)) {
                    row -= 1;
                }

                if (from.isRightOf(to)) {
                    column += 1;
                } else if (to.isRightOf(from)) {
                    column -= 1;
                }

                //Find the position represented by the column/row given by the above calculation.
                range.add(Position.valueOf(Position.COLUMN_INDICES[column] + row));
                length -= 1;
            }
        }
        return range;
    }

    //Pawns can only move forwards, so we need to know the direction as well as the distance
    int verticalDistanceWithDirection(Player player) {
        if (Player.WHITE.equals(player)) {
            return to.row - from.row;
        } else {
            return from.row - to.row;
        }
    }

    int horizontalDistance() {
        return from.horizontalDistanceTo(to);
    }

    int verticalDistance() {
        return from.verticalDistanceTo(to);
    }

    boolean isHorizontal() {
        return from.isHorizontalTo(to);
    }

    boolean isVertical() {
        return from.isVerticalTo(to);
    }

    boolean isDiagonal() {
        return from.isDiagonalTo(to);
    }
}
