package io.github.glandais.wordle.game;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.Matcher;

public class Game {

    private final Matcher matcher;

    private final String solution;

    private int tries;

    public Game(Matcher matcher) {
        this(matcher, matcher.getWords().getRandomDrawable());
    }

    public Game(Matcher matcher, String solution) {
        this.matcher = matcher;
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
