package no.miles.chess.ui;

import no.miles.chess.model.Game;
import no.miles.chess.model.Player;
import no.miles.chess.model.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControlsPanel extends JPanel {

    private ChessApplicationFrame frame;
    private final Game game;
    private final BoardComponent boardComponent;
    private JTextField commandField;
    private JPanel currentPlayerIndicator;
    private JButton submit;

    public ControlsPanel(ChessApplicationFrame frame, Game game, BoardComponent boardComponent) {
        this.frame = frame;
        this.game = game;
        this.boardComponent = boardComponent;

        init();
    }

    private void init() {
        JLabel commandLabel = new JLabel("Move: ");
        commandField = new JTextField();
        commandField.setColumns(10);

        submit = new JButton("Bust a move!");

        currentPlayerIndicator = new JPanel();
        currentPlayerIndicator.setMinimumSize(new Dimension(30, 30));
        currentPlayerIndicator.setPreferredSize(new Dimension(30, 30));
        currentPlayerIndicator.setMaximumSize(new Dimension(30, 30));
        currentPlayerIndicator.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        updatePlayerColor();

        add(currentPlayerIndicator);
        add(commandLabel);
        add(commandField);
        add(submit);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parseCommandAndDoMove();
            }
        });
        commandField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    parseCommandAndDoMove();
                }
            }
        });
    }

    private void parseCommandAndDoMove() {
        String command = commandField.getText();

        InputParser parser = new InputParser();
        Position[] move = parser.parseMove(command);

        if (move == null) {
            showMessage(parser.getErrorText());
        } else {
            if (game.canMove(move[0], move[1])) {
                game.move(move[0], move[1]);
                updatePlayerColor();
                if (game.isCurrentPlayerInCheckMate()) {
                    commandField.setEnabled(false);
                    submit.setEnabled(false);
                    String message = "Game over! Player " + game.getWinningColor() + " won.";
                    showMessage(message);
                } else if (game.isCurrentPlayerInCheck()) {
                    showMessage("Player " + game.currentPlayer() + " is in check.");
                } else {
                    commandField.setText("");
                }
            } else {
                showMessage("Illegal move!");
            }
            boardComponent.repaint();
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    private void updatePlayerColor() {
        currentPlayerIndicator.setBackground(getColorForPlayer(Player.WHITE.equals(game.currentPlayer())));
    }

    private Color getColorForPlayer(boolean whitePlayer) {
        return whitePlayer ? Color.WHITE : Color.BLACK;
    }

}
