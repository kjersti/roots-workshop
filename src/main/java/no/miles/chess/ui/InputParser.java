package no.miles.chess.ui;

import no.miles.chess.model.Position;

public class InputParser {

    private String errorText;

    public Position[] parseMove(String command) {

        String[] positions = command.split("-");

        if (positions.length != 2) {
            errorText = "Wrong move format: Should be [from position]-[to position], for instance \"A2-A4\"";
            return null;
        }

        String fromAsString = positions[0].toUpperCase();
        String toAsString = positions[1].toUpperCase();

        try {
            Position from = Position.valueOf(fromAsString);
            Position to = Position.valueOf(toAsString);
            return new Position[] {from, to};
        } catch (IllegalArgumentException e) {
            errorText = "Invalid position(s)";
            return null;
        }

    }

    public String getErrorText() {
        return errorText;
    }
}
