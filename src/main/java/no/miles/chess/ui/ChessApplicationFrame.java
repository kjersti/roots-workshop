package no.miles.chess.ui;

import no.miles.chess.model.Board;
import no.miles.chess.model.Game;

import javax.swing.*;
import java.awt.*;

class ChessApplicationFrame extends JFrame {

    ChessApplicationFrame(Game game, Board board) throws HeadlessException {

        setTitle("Chess HARDCORE EDITION");
        setSize(500,600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BoardComponent boardComponent = new BoardComponent(board, new PieceRenderer(new ImageLoader()), new SquareRenderer());
        getContentPane().add(boardComponent);
        getContentPane().add(new ControlsPanel(this, game, boardComponent), BorderLayout.SOUTH);
    }


}
