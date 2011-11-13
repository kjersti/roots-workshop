package no.miles.chess.model;

public class CheckRule implements ChessRule {

    private ChessRule captureRule;

    public CheckRule() {
        captureRule = new IllegalCaptureRule();
    }

    public boolean isInCheck(Player player, Board board) {
        return applies(null, player, board);
    }

    public boolean applies(Move move, Player player, Board board) {
        Piece king = board.findKingForPlayer(player);
        Position kingsPosition = board.getPositionOf(king);
        for (Piece opponentPiece : board.getAllPiecesFor(player.opponent())) {
            Position opponentPosition = board.getPositionOf(opponentPiece);
            Move opponentMove = new Move(opponentPosition, kingsPosition);
            if (!captureRule.applies(opponentMove, player.opponent(), board)) {
                return true;
            }
        }
        return false;
    }

}
