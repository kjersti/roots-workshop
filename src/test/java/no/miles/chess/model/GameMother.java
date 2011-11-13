package no.miles.chess.model;

public class GameMother {

    public static Game shortestGameEverRecorded() {
        Board board = new BoardBuilder()
                .withQueenOn(Player.WHITE, Position.E5)
                .withKingOn(Player.WHITE, Position.E1)
                .withKingOn(Player.BLACK, Position.E7)
                .withQueenOn(Player.BLACK, Position.D8)
                .withPawnOn(Player.BLACK, Position.D7)
                .withPawnOn(Player.BLACK, Position.F7)
                .withBishopOn(Player.BLACK, Position.F8)
                .build();
        Game game = new Game(board);
        game.setCurrentPlayer(Player.BLACK);
        return game;
    }

}
