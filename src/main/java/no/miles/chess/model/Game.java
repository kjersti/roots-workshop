package no.miles.chess.model;

public class Game {

    private Player currentPlayer;
    private Board board;

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
                && !isPlayerInCheck(currentPlayer, Board.simulateBoardAfterMove(move, board));
    }

    public Player getWinningColor() {
        return currentPlayer.opponent();
    }

    public boolean isCurrentPlayerInCheckMate() {
        return isPlayerInCheckMate(currentPlayer);
    }

    public boolean isCurrentPlayerInCheck() {
        return isPlayerInCheck(currentPlayer, board);
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    boolean isPlayerInCheckMate(Player player) {
        return isCurrentPlayerInCheck() && kingCannotEscape(player);
    }

    boolean canMake(Move move) {
        return canMake(move, board, currentPlayer);
    }

    boolean canCapture(Move move) {
        return canCapture(move, currentPlayer, board);
    }

    private boolean isPlayerInCheck(Player player, Board board) {
        Piece king = board.findKingForPlayer(player);
        Position kingsPosition = board.getPositionOf(king);
        for (Piece opponentPiece : board.getAllPiecesFor(player.opponent())) {
            Position opponentPosition = board.getPositionOf(opponentPiece);
            Move move = new Move(opponentPosition, kingsPosition);
            if (canCapture(move, player.opponent(), board)) {
                return true;
            }
        }
        return false;
    }

    private boolean kingCannotEscape(Player player) {
        boolean kingCannotEscape = true;
        Piece king = board.findKingForPlayer(player);
        Position position = board.getPositionOf(king);

        // Try out all the positions on the board to see if it is possible for the
        // king to move here
        for (Position possibleDestination : Position.values()) {
            Move move = new Move(position, possibleDestination);
            if (canMake(move, board, currentPlayer)
                    && !isPlayerInCheck(player, Board.simulateBoardAfterMove(move, board))) {
                kingCannotEscape = false;
            }
        }

        return kingCannotEscape;
    }

    private boolean canMake(Move move, Board board, Player player) {
        Piece piece = board.getPieceOn(move.getFrom());

        boolean canMake = true;

        if (piece == null) {
            // Cannot move from an empty square
            canMake = false;
        } else if (piece.belongsTo(player.opponent())) {
            // Cannot move other player's pieces
            canMake = false;
        } else if (!piece.canMove(move, board.piecesInPath(move))) {
            canMake = false;
        } else if (board.getPieceOn(move.getTo()) != null) {
            // Cannot move to an occupied square
            canMake = false;
        }
        return canMake;
    }

    private boolean canCapture(Move move, Player player, Board board) {
        Piece attacker = board.getPieceOn(move.getFrom());
        Piece pieceOnDestination = board.getPieceOn(move.getTo());

        boolean validCapture = true;

        if (board.hasNoPieceOn(move.getTo()) || attacker == null) {
            // Cannot attack empty squares or from empty square
            validCapture = false;
        } else if (attacker.belongsTo(player.opponent())) {
            // Piece belongs to opponent
            validCapture = false;
        } else if(pieceOnDestination.belongsTo(attacker.getPlayer())){
            // Cannot attack own piece
            validCapture = false;
        } else if (!attacker.canCapturePiece(move, board.piecesInPath(move))) {
            validCapture = false;
        }


        return validCapture;
    }

}