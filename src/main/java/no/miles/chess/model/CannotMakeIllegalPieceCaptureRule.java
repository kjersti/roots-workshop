package no.miles.chess.model;

class CannotMakeIllegalPieceCaptureRule implements ChessRule {
    public boolean applies(Move move, Player player, Board board) {
        return !board.getPieceOn(move.getFrom()).canCapture(move, board.piecesInPath(move));
    }
}
