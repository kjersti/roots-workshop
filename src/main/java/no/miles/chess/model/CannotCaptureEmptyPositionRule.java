package no.miles.chess.model;

class CannotCaptureEmptyPositionRule implements ChessRule {
    public boolean applies(Move move, Player player, Board board) {
        return board.hasNoPieceOn(move.getTo());
    }
}
