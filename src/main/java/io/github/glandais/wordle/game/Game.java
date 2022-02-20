package io.github.glandais.wordle.game;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.Matcher;
import io.github.glandais.wordle.engine.Words;

public class Game {

    private final Matcher matcher;

    private final String solution;

    private int tries;

    public Game(Words words) {
        this(words, words.getRandomDrawable());
    }

    public Game(Words words, String solution) {
        this.matcher = new Matcher(words);
        this.solution = solution;
        this.tries = 0;
    }

    public String getSolution() {
        return solution;
    }

    public int getTries() {
        return tries;
    }

    public Answer tryWord(String test) {
        tries++;
        return matcher.getAnswer(solution, test);
    }

}
