package no.miles.chess.model;

class CannotMakeIllegalPieceMoveRule implements ChessRule {
    public boolean applies(Move move, Player player, Board board) {
        return !board.getPieceOn(move.getFrom()).canMove(move, board.piecesInPath(move));
    }
}
