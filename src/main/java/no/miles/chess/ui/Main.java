package no.miles.chess.ui;

import no.miles.chess.model.Board;
import no.miles.chess.model.Game;

public class Main {

    public static void main(String[] args) {

        Board board = Board.createInitial();
        Game game = new Game(board);

        ChessApplicationFrame applicationFrame = new ChessApplicationFrame(game, board);
        applicationFrame.setVisible(true);
    }

}
