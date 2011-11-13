package no.miles.chess.ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static no.miles.chess.ui.BoardComponent.STANDARD_SQUARE_SIZE;

class SquareRenderer {

    void drawSingleSquare(Graphics2D g, int x, int y, Color ofColor) {
        Rectangle2D.Double square = new Rectangle2D.Double(x, y, STANDARD_SQUARE_SIZE, STANDARD_SQUARE_SIZE);
        g.setColor(ofColor);
        g.fill(square);
    }
}
