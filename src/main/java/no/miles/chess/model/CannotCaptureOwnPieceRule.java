package no.miles.chess.model;

class CannotCaptureOwnPieceRule implements ChessRule {
    public boolean applies(Move move, Player player, Board board) {
        return board.getPieceOn(move.getTo()).belongsTo(player);
    }
}
