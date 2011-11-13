package no.miles.chess.model;

import java.util.HashSet;
import java.util.Set;

public class IllegalMoveRule implements ChessRule {

    private  Set<ChessRule> moveRules = new HashSet<ChessRule>();

    public IllegalMoveRule() {
        moveRules.add(new CannotMoveEnemyPieceRule());
        moveRules.add(new CannotMakeIllegalPieceMoveRule());
        moveRules.add(new CannotMoveToOccupiedPositionRule());
    }

    public boolean applies(Move move, Player player, Board board) {
        for (ChessRule rule : moveRules) {
            if (rule.applies(move, player, board)) {
                return true;
            }
        }
        return false;
    }
}