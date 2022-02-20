package io.github.glandais.wordle.game;

import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private final Matcher matcher;

    private final String word;

    public Game(Words words) {
        this(words, words.getWordList().get(ThreadLocalRandom.current().nextInt(words.getWordList().size())));
    }

    public Game(Words words, String word) {
        this.matcher = new Matcher(words);
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public Answer tryWord(String test) {
        return matcher.tryWord(word, test);
    }

}
