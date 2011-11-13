package no.miles.chess.model;

public class Piece {

    private final Player player;
    private final PieceType type;
    private boolean moved;

    public Piece(Player player, PieceType type) {
        this(player, type, false);
    }

    private Piece(Player player, PieceType type, boolean moved) {
        this.player = player;
        this.type = type;
        this.moved = moved;
    }

    Piece copy() {
        return new Piece(player, type, moved);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }

    public PieceType getType() {
        return type;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public String toString() {
        return "{" +
                "player=" + player +
                ", type=" + type +
                ", moved=" + moved +
                '}';
    }

    //This maps to the filename for the image of the piece.
    public String getName() {
        return player + "_" + type;
    }
}