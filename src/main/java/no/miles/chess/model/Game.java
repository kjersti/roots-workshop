package no.miles.chess.model;

public class Game {

    private Player currentPlayer;
    private Board board;

    private final ChessRule illegalMoveRule = new IllegalMoveRule();
    private final ChessRule illegalCaptureRule = new IllegalCaptureRule();
    private final ChessRule checkRule = new CheckRule();
    private final ChessRule checkMateRule = new CheckMateRule();

    public Game(Board board) {
        this.board = board;
        currentPlayer = Player.WHITE;
    }

    public void move(Move move) {
        if (canMove(move)) {
            board.makeMove(move);
            currentPlayer = currentPlayer.opponent();
        } else {
            throw new IllegalArgumentException("Cannot make move " + move);
        }
    }

    public boolean canMove(Move move) {
        return (canMake(move) || canCapture(move))
                && !checkRule.applies(null, currentPlayer, Board.simulateBoardAfterMove(move, board));
    }

    public Player getWinningColor() {
        return currentPlayer.opponent();
    }

    public boolean isCurrentPlayerInCheckMate() {
        return isPlayerInCheckMate(currentPlayer);
    }

    public boolean isCurrentPlayerInCheck() {
        return checkRule.applies(null, currentPlayer, board);
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    boolean isPlayerInCheckMate(Player player) {
        return checkMateRule.applies(null, player, board);
    }

    boolean canMake(Move move) {
        return !illegalMoveRule.applies(move, currentPlayer, board);
    }

    boolean canCapture(Move move) {
        return !illegalCaptureRule.applies(move, currentPlayer, board);
    }

}