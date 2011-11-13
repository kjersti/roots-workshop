package no.miles.chess.ui;

import no.miles.chess.model.Board;
import no.miles.chess.model.Piece;
import no.miles.chess.model.Position;

import javax.swing.*;
import java.awt.*;

class BoardComponent extends JComponent {

    public static final int STANDARD_SQUARE_SIZE = 50;

    private static final Point STARTING_POINT = new Point(50, 50);
    private static final int NUMBER_OF_SQUARES_EACH_WAY = 8;
    private static final Color DARK_BOARD_COLOR = new Color(209, 139, 71);
    private static final Color LIGHT_BOARD_COLOR = new Color(255, 206, 158);

    private final Board board;
    private final PieceRenderer pieceRenderer;
    private final SquareRenderer squareRenderer;

    BoardComponent(Board board, PieceRenderer pieceRenderer, SquareRenderer squareRenderer) {
        this.board = board;
        this.pieceRenderer = pieceRenderer;
        this.squareRenderer = squareRenderer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D drawer = (Graphics2D) g;

        drawColumnNames(drawer);
        drawRowNumbers(drawer);
        drawGrid(drawer);
        drawPiecesOnGrid(drawer);
        drawBorder(drawer);
    }

    private void drawColumnNames(Graphics2D drawer) {
        String[] strings = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i = 0, stringsLength = strings.length; i < stringsLength; i++) {
            String column = strings[i];
            drawer.drawString(column, xPosition(i) + 20, 30);
        }
    }

    private void drawRowNumbers(Graphics2D drawer) {
        for (int i = 0; i < NUMBER_OF_SQUARES_EACH_WAY; i++) {
            drawer.drawString(String.valueOf(8 - i), 20, yPosition(i) + 30);
        }
    }

    private void drawGrid(Graphics2D drawer) {
        Color lastColor = DARK_BOARD_COLOR;

        for (int row = 0; row < NUMBER_OF_SQUARES_EACH_WAY; row++) {
            for (int column = 0; column < NUMBER_OF_SQUARES_EACH_WAY; column++) {
                Color color = toggleColor(lastColor);
                squareRenderer.drawSingleSquare(drawer, xPosition(column), yPosition(row), color);
                lastColor = color;
            }
            lastColor = toggleColor(lastColor);
        }
    }

    private void drawPiecesOnGrid(Graphics2D drawer) {
        for (int row = 0; row < NUMBER_OF_SQUARES_EACH_WAY; row++) {
            for (int column = 0; column < NUMBER_OF_SQUARES_EACH_WAY; column++) {
                Position position = translateGridToPosition(row, column);
                Piece pieceFromBoard = getPieceFromBoard(position);
                if (board.hasPieceOn(position)) {
                    pieceRenderer.drawPieceOnCanvas(drawer, xPosition(column), yPosition(row), pieceFromBoard);
                }
            }
        }
    }

    private Piece getPieceFromBoard(Position position) {
        return board.getPieceOn(position);
    }

    private Position translateGridToPosition(int row, int column) {
        return Position.valueOf(Position.COLUMN_INDICES[column] + (8 - row));
    }

    private int yPosition(int row) {
        return STARTING_POINT.x + (row * STANDARD_SQUARE_SIZE);
    }

    private int xPosition(int column) {
        return STARTING_POINT.y + (column * STANDARD_SQUARE_SIZE);
    }

    private void drawBorder(Graphics2D drawer) {

        drawer.setColor(Color.BLACK);
        drawer.draw(new Rectangle(
                STARTING_POINT.x, STARTING_POINT.y,
                STANDARD_SQUARE_SIZE * NUMBER_OF_SQUARES_EACH_WAY,
                STANDARD_SQUARE_SIZE * NUMBER_OF_SQUARES_EACH_WAY));
    }

    private Color toggleColor(Color color) {
        return color == LIGHT_BOARD_COLOR ? DARK_BOARD_COLOR : LIGHT_BOARD_COLOR;
    }
}