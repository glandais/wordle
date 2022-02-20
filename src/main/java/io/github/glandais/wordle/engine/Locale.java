package io.github.glandais.wordle.engine;

public enum Locale {

    FR("RAIES"),

    EN("SOARE");

    final String startWord;

    Locale(String startWord) {
        this.startWord = startWord;
    }

    public String getStartWord() {
        return startWord;
    }
}
