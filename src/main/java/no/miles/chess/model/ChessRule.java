package no.miles.chess.model;

public interface ChessRule {

    public boolean applies(Move move, Player player, Board board);
}
