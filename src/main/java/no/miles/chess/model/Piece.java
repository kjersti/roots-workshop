package no.miles.chess.model;

import java.util.Set;

public abstract class Piece {

    public static final Piece NONE = new NullPiece();

    protected final Player player;
    protected boolean moved;

    protected Piece(Player player, boolean moved) {
        this.player = player;
        this.moved = moved;
    }

    abstract Piece copy();

    public Player getPlayer() {
        return player;
    }

    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public String toString() {
        return "{" +
                "player=" + player +
                ", type=" + this.getClass().getSimpleName() +
                ", moved=" + moved +
                '}';
    }

    //This maps to the filename for the image of the piece.
    public String getName() {
        return player + "_" + this.getClass().getSimpleName().replace("Piece", "").toLowerCase();
    }

    abstract boolean canMove(Move move, Set<Piece> piecesInPath);

    abstract boolean canCapture(Move move, Set<Piece> piecesInPath);

    abstract String getSymbol();
}