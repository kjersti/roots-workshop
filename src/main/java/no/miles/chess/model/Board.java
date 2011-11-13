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
        for (Position p : Position.values()) {
            pieces.put(p, Piece.NONE);
        }
    }

    private void placeInitialPieces() {
        // Always start with initial board!
        pieces.put(Position.A1, new Piece(Player.WHITE, PieceType.ROOK));
        pieces.put(Position.B1, new Piece(Player.WHITE, PieceType.KNIGHT));
        pieces.put(Position.C1, new Piece(Player.WHITE, PieceType.BISHOP));
        pieces.put(Position.D1, new Piece(Player.WHITE, PieceType.QUEEN));
        pieces.put(Position.E1, new Piece(Player.WHITE, PieceType.KING));
        pieces.put(Position.F1, new Piece(Player.WHITE, PieceType.BISHOP));
        pieces.put(Position.G1, new Piece(Player.WHITE, PieceType.KNIGHT));
        pieces.put(Position.H1, new Piece(Player.WHITE, PieceType.ROOK));
        pieces.put(Position.A2, new PawnPiece(Player.WHITE));
        pieces.put(Position.B2, new PawnPiece(Player.WHITE));
        pieces.put(Position.C2, new PawnPiece(Player.WHITE));
        pieces.put(Position.D2, new PawnPiece(Player.WHITE));
        pieces.put(Position.E2, new PawnPiece(Player.WHITE));
        pieces.put(Position.F2, new PawnPiece(Player.WHITE));
        pieces.put(Position.G2, new PawnPiece(Player.WHITE));
        pieces.put(Position.H2, new PawnPiece(Player.WHITE));

        pieces.put(Position.A8, new Piece(Player.BLACK, PieceType.ROOK));
        pieces.put(Position.B8, new Piece(Player.BLACK, PieceType.KNIGHT));
        pieces.put(Position.C8, new Piece(Player.BLACK, PieceType.BISHOP));
        pieces.put(Position.D8, new Piece(Player.BLACK, PieceType.QUEEN));
        pieces.put(Position.E8, new Piece(Player.BLACK, PieceType.KING));
        pieces.put(Position.F8, new Piece(Player.BLACK, PieceType.BISHOP));
        pieces.put(Position.G8, new Piece(Player.BLACK, PieceType.KNIGHT));
        pieces.put(Position.H8, new Piece(Player.BLACK, PieceType.ROOK));
        pieces.put(Position.A7, new PawnPiece(Player.BLACK));
        pieces.put(Position.B7, new PawnPiece(Player.BLACK));
        pieces.put(Position.C7, new PawnPiece(Player.BLACK));
        pieces.put(Position.D7, new PawnPiece(Player.BLACK));
        pieces.put(Position.E7, new PawnPiece(Player.BLACK));
        pieces.put(Position.F7, new PawnPiece(Player.BLACK));
        pieces.put(Position.G7, new PawnPiece(Player.BLACK));
        pieces.put(Position.H7, new PawnPiece(Player.BLACK));
    }

    private Board(Board board) {

        Map<Position, Piece> newPieces = new HashMap<Position, Piece>();

        for (Position position : Position.values()) {
            Piece piece = board.getPieceOn(position);
            newPieces.put(position, piece.copy());
        }
        pieces = newPieces;
    }

    public Piece findKingForPlayer(Player player) {
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            Piece piece = entry.getValue();
            if (piece.belongsTo(player) && piece.getType() == PieceType.KING) {
                return piece;
            }
        }

        throw new IllegalArgumentException("If king is not present on board while" +
                " game is in progress, something is horribly wrong");
    }

    public Set<Piece> getAllPiecesFor(Player player) {
        Set<Piece> playerPieces = new HashSet<Piece>();

        for (Piece piece : pieces.values()) {
            if (piece.belongsTo(player)) {
                playerPieces.add(piece);
            }
        }

        return playerPieces;
    }

    public boolean hasPieceOn(Position position) {
        return pieces.get(position) != Piece.NONE;
    }

    public boolean hasNoPieceOn(Position position) {
        return !hasPieceOn(position);
    }

    public void makeMove(Move move) {
        Piece piece = pieces.get(move.getFrom());

        pieces.put(move.getFrom(), Piece.NONE);
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
                if (hasPieceOn(position)) {
                    Piece piece = getPieceOn(position);
                    switch (piece.getType()) {
                        case BISHOP:
                            symbol = "B";
                            break;
                        case KNIGHT:
                            symbol = "N";
                            break;
                        case ROOK:
                            symbol = "R";
                            break;
                        case KING:
                            symbol = "K";
                            break;
                        case QUEEN:
                            symbol = "Q";
                            break;
                        case PAWN:
                            symbol = "x";
                            break;
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
