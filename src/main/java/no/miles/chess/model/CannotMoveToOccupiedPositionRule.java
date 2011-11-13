package no.miles.chess.model;

class CannotMoveToOccupiedPositionRule implements ChessRule {
    public boolean applies(Move move, Player player, Board board) {
        return board.hasPieceOn(move.getTo());
    }
}
