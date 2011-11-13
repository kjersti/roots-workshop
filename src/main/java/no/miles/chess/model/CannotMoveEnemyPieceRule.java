package no.miles.chess.model;

class CannotMoveEnemyPieceRule implements ChessRule {
    public boolean applies(Move move, Player player, Board board) {
        return board.getPieceOn(move.getFrom()).belongsTo(player.opponent());
    }
}
