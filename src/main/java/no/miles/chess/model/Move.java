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
        if ((from.isVerticalTo(to)
                || from.isHorizontalTo(to)
                || from.isDiagonalTo(to))) {

            int totalLength = from.isHorizontalTo(to) ? from.horizontalDistanceTo(to) : from.verticalDistanceTo(to);
            int length = totalLength - 1;

            int currentColumnNameIndex = from.column - 1;
            int currentRow = from.row;
            while (length > 0) {
                if (from.row < to.row) {
                    //If it is a move up, increment the row.
                    currentRow += 1;
                } else if (to.row < from.row) {
                    //If it is a move down, decrement the row.
                    currentRow -= 1;
                }
                if (from.column < to.column) {
                    //If it is a move left, increment the column.
                    currentColumnNameIndex += 1;
                } else if (to.column < from.column) {
                    //If it is a move right, decrement the column.
                    currentColumnNameIndex -= 1;
                }

                //Find the position represented by the column/row given by the above calculation.
                range.add(Position.valueOf(Position.COLUMN_INDICES[currentColumnNameIndex] + currentRow));
                length -= 1;
            }
        }
        return range;
    }

    //Pawns can only move forwards, so we need to know the direction as well as the distance
    int verticalDistanceWithDirection(Player player) {
        if (Player.WHITE.equals(player)) {
            return getTo().row - getFrom().row;
        } else {
            return getFrom().row - getTo().row;
        }
    }
}
