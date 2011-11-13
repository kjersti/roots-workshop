package no.miles.chess.model;

public enum Player {
    WHITE, BLACK;

    Player opponent() {
        if (WHITE.equals(this)) return BLACK;
        else return WHITE;
    }
}