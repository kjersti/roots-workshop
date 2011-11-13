package no.miles.chess.ui;

import no.miles.chess.model.Piece;

import java.awt.*;

import static no.miles.chess.ui.BoardComponent.STANDARD_SQUARE_SIZE;

class PieceRenderer {

    private final ImageLoader imageLoader;

    public PieceRenderer(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    void drawPieceOnCanvas(Graphics2D drawer, int x, int y, Piece piece) {
        Image pieceImage = imageLoader.getImageForPiece(piece);
        drawPiece(drawer, x, y, pieceImage);
    }

    private void drawPiece(Graphics2D drawer, int x, int y, Image pieceImage) {
        drawer.drawImage(pieceImage, x, y, STANDARD_SQUARE_SIZE - 5, STANDARD_SQUARE_SIZE - 5, null);
    }

}
