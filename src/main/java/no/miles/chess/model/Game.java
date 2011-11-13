package no.miles.chess.model;

import java.util.HashSet;
import java.util.Set;

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
        boolean isValidMove = canMake(move) || canCapture(move);
        if (isValidMove) {
            //We cannot put ourselves in check. If we do this move, will the
            //current player put herself in check?
            Board simulatedBoardAfterMove = Board.simulateBoardAfterMove(move, board);
            Piece kingForPlayer = simulatedBoardAfterMove.findKingForPlayer(currentPlayer);
            Position kingsPosition = simulatedBoardAfterMove.getPositionOf(kingForPlayer);
            if (isAttackableForOpponent(currentPlayer, kingsPosition, simulatedBoardAfterMove)) {
                isValidMove = false;
            }
        }
        return isValidMove;
    }

    public Player getWinningColor() {
        return currentPlayer.opponent();
    }

    public boolean isCurrentPlayerInCheckMate() {
        return isPlayerInCheckMate(currentPlayer);
    }

    public boolean isCurrentPlayerInCheck() {
        return isPlayerInCheck(currentPlayer);
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    private boolean isPlayerInCheck(Player player) {
        Piece king = board.findKingForPlayer(player);
        Position kingsPosition = board.getPositionOf(king);
        return isAttackableForOpponent(player, kingsPosition, board);
    }

    void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    boolean isPlayerInCheckMate(Player player) {

        boolean kingCannotEscape = true;
        Piece king = board.findKingForPlayer(player);
        Position position = board.getPositionOf(king);
        Set<Position> possibleDestinationsForKing = new HashSet<Position>();

        // Try out all the positions on the board to see if it is possible for the
        // king to move here
        for (Position possibleDestination : Position.values()) {
            boolean isValidMove;
            Move move = new Move(position, possibleDestination);
            if (king.canMove(move, board.piecesInPath(move))) {
                Piece pieceOnDestination = board.getPieceOn(possibleDestination);
                if (pieceOnDestination != null) {
                    // Cannot attack a destination occupied by your own piece
                    isValidMove = !(pieceOnDestination.belongsTo(king.getPlayer()));
                    if (isValidMove) {
                        possibleDestinationsForKing.add(possibleDestination);
                    }
                } else {
                    // No piece on this position, ok to move.
                    possibleDestinationsForKing.add(possibleDestination);
                }
            }
        }

        for (Position destination : possibleDestinationsForKing) {
            // For each possible position the king can move to, create a new
            // board we can simulate this movement on and check if it can be attacked
            Move move = new Move(board.getPositionOf(king), destination);
            Board simulatedBoardAfterMove = Board.simulateBoardAfterMove(move, board);
            if (!isAttackableForOpponent(player, destination, simulatedBoardAfterMove)) {
                kingCannotEscape = false;
            }
        }
        return isCurrentPlayerInCheck() && kingCannotEscape;
    }

    boolean canMake(Move move) {
        Piece piece = board.getPieceOn(move.getFrom());

        boolean canMake = true;

        if (piece == null) {
            // Cannot move from an empty square
            canMake = false;
        } else if (piece.belongsTo(currentPlayer.opponent())) {
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

    boolean canCapture(Move move) {
        return isValidCapture(move, currentPlayer, board);
    }

    boolean isValidCapture(Move move, Player player, Board board) {
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
        } else if (attacker.getType() == PieceType.PAWN) {
            // Pawns attack diagonally forwards only
            validCapture = move.getFrom().isDiagonalTo(move.getTo()) && move.verticalDistanceWithDirection(player) == 1;
        } else if (!attacker.canMove(move, board.piecesInPath(move))) {
            //Cannot attack position you cannot move to.
            validCapture = false;
        }


        return validCapture;
    }

    boolean isAttackableForOpponent(Player player, Position kingsPosition, Board board) {
        for (Piece opponentPiece : board.getAllPiecesFor(player.opponent())) {
            // Check if each of opponent's pieces can attack the current
            // player's king

            Position opponentPosition = board.getPositionOf(opponentPiece);

            Move move = new Move(opponentPosition, kingsPosition);
            if (isValidCapture(move, player.opponent(), board)) {
                return true;
            }
        }
        return false;
    }

}