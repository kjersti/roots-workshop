package no.miles.chess.model;

import java.util.HashSet;
import java.util.Set;

public class IllegalCaptureRule implements ChessRule {

    private Set<ChessRule> captureRules = new HashSet<ChessRule>();

    public IllegalCaptureRule() {
        captureRules.add(new CannotMoveEnemyPieceRule());
        captureRules.add(new CannotCaptureEmptyPositionRule());
        captureRules.add(new CannotCaptureOwnPieceRule());
        captureRules.add(new CannotMakeIllegalPieceCaptureRule());
    }

    public boolean applies(Move move, Player player, Board board) {
        for (ChessRule rule : captureRules) {
            if (rule.applies(move, player, board)) {
                return true;
            }
        }
        return false;
    }
}