package no.miles.chess.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    private Map<Position, Piece> pieces;

    public static Board createInitial() {
        Board board = new Board();
        board.placeInitialPieces();
        return board;
    }

    static Board createEmpty() {
        return new Board();
    }

    public static Board simulateBoardAfterMove(Move move, Board board) {
        Board newBoard = new Board(board);
        newBoard.makeMove(move);
        return newBoard;
    }

    private Board() {
        pieces = new HashMap<Position, Piece>();
    }

    private void placeInitialPieces() {
        // Always start with initial board!
        placeInitialWhitePiece(Position.A1, PieceType.ROOK);
        placeInitialWhitePiece(Position.B1, PieceType.KNIGHT);
        placeInitialWhitePiece(Position.C1, PieceType.BISHOP);
        placeInitialWhitePiece(Position.D1, PieceType.QUEEN);
        placeInitialWhitePiece(Position.E1, PieceType.KING);
        placeInitialWhitePiece(Position.F1, PieceType.BISHOP);
        placeInitialWhitePiece(Position.G1, PieceType.KNIGHT);
        placeInitialWhitePiece(Position.H1, PieceType.ROOK);
        placeInitialWhitePiece(Position.A2, PieceType.PAWN);
        placeInitialWhitePiece(Position.B2, PieceType.PAWN);
        placeInitialWhitePiece(Position.C2, PieceType.PAWN);
        placeInitialWhitePiece(Position.D2, PieceType.PAWN);
        placeInitialWhitePiece(Position.E2, PieceType.PAWN);
        placeInitialWhitePiece(Position.F2, PieceType.PAWN);
        placeInitialWhitePiece(Position.G2, PieceType.PAWN);
        placeInitialWhitePiece(Position.H2, PieceType.PAWN);

        placeInitialBlackPiece(Position.A8, PieceType.ROOK);
        placeInitialBlackPiece(Position.B8, PieceType.KNIGHT);
        placeInitialBlackPiece(Position.C8, PieceType.BISHOP);
        placeInitialBlackPiece(Position.D8, PieceType.QUEEN);
        placeInitialBlackPiece(Position.E8, PieceType.KING);
        placeInitialBlackPiece(Position.F8, PieceType.BISHOP);
        placeInitialBlackPiece(Position.G8, PieceType.KNIGHT);
        placeInitialBlackPiece(Position.H8, PieceType.ROOK);
        placeInitialBlackPiece(Position.A7, PieceType.PAWN);
        placeInitialBlackPiece(Position.B7, PieceType.PAWN);
        placeInitialBlackPiece(Position.C7, PieceType.PAWN);
        placeInitialBlackPiece(Position.D7, PieceType.PAWN);
        placeInitialBlackPiece(Position.E7, PieceType.PAWN);
        placeInitialBlackPiece(Position.F7, PieceType.PAWN);
        placeInitialBlackPiece(Position.G7, PieceType.PAWN);
        placeInitialBlackPiece(Position.H7, PieceType.PAWN);
    }

    private Board(Board board) {

        Map<Position, Piece> newPieces = new HashMap<Position, Piece>();

        for (Position position : Position.values()) {
            Piece piece = board.getPieceOn(position);
            if (piece != null) {
                newPieces.put(position, piece.copy());
            } else {
                newPieces.put(position, null);
            }
        }
        pieces = newPieces;
    }

    public Piece findKingForPlayer(Player player) {
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            Piece piece = entry.getValue();
            if (piece != null && piece.getType() == PieceType.KING && piece.belongsTo(player)) {
                return piece;
            }
        }

        throw new IllegalArgumentException("If king is not present on board while" +
                " game is in progress, something is horribly wrong");
    }

    public Set<Piece> getAllPiecesFor(Player player) {
        Set<Piece> playerPieces = new HashSet<Piece>();

        for (Piece piece : pieces.values()) {
            if (piece != null && piece.belongsTo(player)) {
                playerPieces.add(piece);
            }
        }

        return playerPieces;
    }

    public boolean hasPieceOn(Position position) {
        return pieces.get(position) != null;
    }

    public boolean hasNoPieceOn(Position position) {
        return !hasPieceOn(position);
    }

    public void makeMove(Move move) {
        Piece piece = pieces.get(move.getFrom());

        pieces.put(move.getFrom(), null);
        pieces.put(move.getTo(), piece);
        piece.setMoved(true);
    }

    public Position getPositionOf(Piece piece) {
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            if (piece.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Piece " + piece + " not on board.");
    }

    public Piece getPieceOn(Position position) {
        return pieces.get(position);
    }

    public void setPieceOn(Piece piece, Position position) {
        pieces.put(position, piece);
    }

    // Helper methods...
    private void placeInitialWhitePiece(Position position, PieceType pieceType) {
        pieces.put(position, new Piece(Player.WHITE, pieceType));
    }

    private void placeInitialBlackPiece(Position position, PieceType pieceType) {
        pieces.put(position, new Piece(Player.BLACK, pieceType));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n");

        builder.append("    A   B   C   D   E   F   G   H\n");
        for (int row = 0; row < 8; row++) {
            builder.append("  --------------------------------\n");
            builder.append((8 - row) + " |");
            for (int column = 0; column < 8; column++) {
                Position position = toPosition(row, column);

                String symbol = "";
                if(hasPieceOn(position)) {
                    Piece piece = getPieceOn(position);
                    switch(piece.getType()){
                        case BISHOP: symbol = "B"; break;
                        case KNIGHT: symbol = "N"; break;
                        case ROOK: symbol = "R"; break;
                        case KING: symbol = "K"; break;
                        case QUEEN: symbol = "Q"; break;
                        case PAWN: symbol = "x"; break;
                    }

                    symbol = piece.belongsTo(Player.WHITE) ? "w" + symbol : "b" + symbol;
                    builder.append(symbol + " |");
                } else {
                    builder.append("   |");
                }
            }
            builder.append("\n");
        }
        builder.append("  --------------------------------\n");

        return builder.toString();
    }

    private Position toPosition(int row, int column) {
        return Position.valueOf(Position.COLUMN_INDICES[column] + (8 - row));
    }

    //Find all pieces in the path, on the given board
    Set<Piece> piecesInPath(Move move) {
        Set<Piece> piecesInPath = new HashSet<Piece>();
        for (Position p : move.calculatePositionsInPath()) {
            if (hasPieceOn(p)) {
                Piece piece = getPieceOn(p);
                piecesInPath.add(piece);
            }
        }
        return piecesInPath;
    }
}
