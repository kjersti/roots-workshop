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
            currentPlayer = opponent(currentPlayer);
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
        return opponent(currentPlayer);
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
            if (canMovePiece(move, king, board)) {
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
        } else if (piece.belongsTo(opponent(currentPlayer))) {
            // Cannot move other player's pieces
            canMake = false;
        } else if (!canMovePiece(move, piece, board)) {
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
        } else if (attacker.belongsTo(opponent(player))) {
            // Piece belongs to opponent
            validCapture = false;
        } else if(pieceOnDestination.belongsTo(attacker.getPlayer())){
            // Cannot attack own piece
            validCapture = false;
        } else if (attacker.getType() == PieceType.PAWN) {
            // Pawns attack diagonally forwards only
            validCapture = move.getFrom().isDiagonalTo(move.getTo()) && verticalDistanceWithDirection(move, player) == 1;
        } else if (!canMovePiece(new Move(move.getFrom(), move.getTo()), attacker, board)) {
            //Cannot attack position you cannot move to.
            validCapture = false;
        }


        return validCapture;
    }

    boolean isAttackableForOpponent(Player player, Position kingsPosition, Board board) {
        for (Piece opponentPiece : board.getAllPiecesFor(opponent(player))) {
            // Check if each of opponent's pieces can attack the current
            // player's king

            Position opponentPosition = board.getPositionOf(opponentPiece);

            Move move = new Move(opponentPosition, kingsPosition);
            if (isValidCapture(move, opponent(player), board)) {
                return true;
            }
        }
        return false;
    }

    private Player opponent(Player player) {
        if (Player.WHITE.equals(player)) return Player.BLACK;
        else return Player.WHITE;
    }

    //Find all pieces in the path, on the given board
    Set<Piece> piecesInPath(Move move, Board board) {
        Set<Piece> piecesInPath = new HashSet<Piece>();
        for (Position p : calculatePositionsInPath(move)) {
            if (board.hasPieceOn(p)) {
                Piece piece = board.getPieceOn(p);
                piecesInPath.add(piece);
            }
        }
        return piecesInPath;
    }

    private boolean canMovePiece(Move move, Piece piece, Board board) {

        // Cannot move to a new position if there are other pieces in the
        // way - unless when piece is a knight
        Set<Piece> piecesInPath = piecesInPath(move, board);
        switch (piece.getType()) {
            case PAWN: {
                //Pawns can move one square forwards, or two if it hasn't moved before, and no other piece is blocking.
                return piecesInPath.isEmpty()
                        && (move.getFrom().isVerticalTo(move.getTo()) && validDistance(verticalDistanceWithDirection(move, piece.getPlayer()), piece));
            }
            case ROOK: {
                //Rooks can move horizontal or vertical, when no other piece is blocking.
                return piecesInPath.isEmpty() && (move.getFrom().isHorizontalTo(move.getTo()) || move.getFrom().isVerticalTo(move.getTo()));
            }
            case KNIGHT: {
                //Knights can jump in a knightly manner.
                int horizontal = move.getFrom().horizontalDistanceTo(move.getTo());
                int vertical = move.getFrom().verticalDistanceTo(move.getTo());
                return (horizontal == 2 && vertical == 1)
                        || (horizontal == 1 && vertical == 2);
            }
            case BISHOP: {
                //Bishops can move diagonally, when no other piece is blocking.
                return piecesInPath.isEmpty() && move.getFrom().isDiagonalTo(move.getTo());
            }
            case QUEEN: {
                //Queens can move all over, when no other piece is blocking.
                return piecesInPath.isEmpty() &&
                        (move.getFrom().isVerticalTo(move.getTo()) || move.getFrom().isHorizontalTo(move.getTo()) || move.getFrom().isDiagonalTo(move.getTo()));
            }
            case KING: {
                //King can move in all directions, but only one square at a time.
                return (move.getFrom().isVerticalTo(move.getTo()) && validDistance(move.getFrom().verticalDistanceTo(move.getTo()), piece))
                        || (move.getFrom().isDiagonalTo(move.getTo()) && validDistance(move.getFrom().verticalDistanceTo(move.getTo()), piece))
                        || (move.getFrom().isHorizontalTo(move.getTo()) && validDistance(move.getFrom().horizontalDistanceTo(move.getTo()), piece));
            }
            default: {
                throw new IllegalArgumentException("Non-supported piece type: " + piece.getType());
            }
        }
    }

    private boolean validDistance(int distance, Piece piece) {
        // Pawns can move two squares forward when they make their
        // first move in a game; otherwise one square
        if (piece.getType() == PieceType.PAWN) {
            return distance == 1 || (!piece.isMoved() && distance == 2);
        } else if (piece.getType() == PieceType.KING) {
            return distance == 1;
        }
        return false;
    }

    //Pawns can only move forwards, so we need to know the direction as well as the distance
    int verticalDistanceWithDirection(Move move, Player player) {
        if (Player.WHITE.equals(player)) {
            return move.getTo().row - move.getFrom().row;
        } else {
            return move.getFrom().row - move.getTo().row;
        }
    }

    //A path is the set of positions in a straight line from the from position to the to position
    Set<Position> calculatePositionsInPath(Move move) {
        Set<Position> range = new HashSet<Position>();

        //If move is neither vertical, horizontal or diagonal,
        //it isn't really a path, it is more like a jump
        if ((move.getFrom().isVerticalTo(move.getTo())
                || move.getFrom().isHorizontalTo(move.getTo())
                || move.getFrom().isDiagonalTo(move.getTo()))) {

            int totalLength = move.getFrom().isHorizontalTo(move.getTo()) ? move.getFrom().horizontalDistanceTo(move.getTo()) : move.getFrom().verticalDistanceTo(move.getTo());
            int length = totalLength - 1;

            int currentColumnNameIndex = move.getFrom().column - 1;
            int currentRow = move.getFrom().row;
            while (length > 0) {
                if (move.getFrom().row < move.getTo().row) {
                    //If it is a move up, increment the row.
                    currentRow += 1;
                } else if (move.getTo().row < move.getFrom().row) {
                    //If it is a move down, decrement the row.
                    currentRow -= 1;
                }
                if (move.getFrom().column < move.getTo().column) {
                    //If it is a move left, increment the column.
                    currentColumnNameIndex += 1;
                } else if (move.getTo().column < move.getFrom().column) {
                    //If it is a move right, decrement the column.
                    currentColumnNameIndex -= 1;
                }

                //Find the position represented by the column/row given by the above calculation.
                range.add(Position.valueOf(Position.COLUMN_INDICES[currentColumnNameIndex] + currentRow));
                length -= 1;
            }
        }
        return range;
    }
}