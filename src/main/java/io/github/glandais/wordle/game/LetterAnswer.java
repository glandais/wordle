package io.github.glandais.wordle.game;

public enum LetterAnswer {

    OK("O"),

    MISPLACED("."),

    NOPE("X");

    String symbol;

    LetterAnswer(String symbol) {
        this.symbol = symbol;
    }

    public static LetterAnswer parse(String s) {
        for (LetterAnswer value : values()) {
            if (value.getSymbol().equals(s)) {
                return value;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getSymbol() {
        return symbol;
    }
}
