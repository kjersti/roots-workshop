package no.miles.chess.model;

class CheckMateRule implements ChessRule {

    private IllegalMoveRule illegalMoveRule = new IllegalMoveRule();
    private CheckRule checkRule = new CheckRule();

    public boolean applies(Move move, Player player, Board board) {

        boolean kingCannotEscape = true;
        Piece king = board.findKingForPlayer(player);
        Position position = board.getPositionOf(king);

        // Try out all the positions on the board to see if it is possible for the
        // king to move here
        for (Position possibleDestination : Position.values()) {
            Move opponentMove = new Move(position, possibleDestination);
            if (!illegalMoveRule.applies(opponentMove, player, board)
                    && !checkRule.applies(null, player, Board.simulateBoardAfterMove(opponentMove, board))) {
                kingCannotEscape = false;
            }
        }

        return checkRule.applies(move, player, board) && kingCannotEscape;
    }
}
