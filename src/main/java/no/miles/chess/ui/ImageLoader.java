package no.miles.chess.ui;

import no.miles.chess.model.Piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

class ImageLoader {

    Image getImageForPiece(Piece piece) {
        try {
            return ImageIO.read(getClass().getResourceAsStream("images/" + piece.getName().toLowerCase()+ ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not find image file for " + piece);
        }
    }
}