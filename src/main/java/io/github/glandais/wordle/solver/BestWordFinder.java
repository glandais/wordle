package io.github.glandais.wordle.solver;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.LetterAnswer;
import io.github.glandais.wordle.engine.Matcher;
import io.github.glandais.wordle.engine.Words;

import java.util.HashSet;
import java.util.Set;

public class BestWordFinder {

    private final Matcher matcher;
    private final Words words;
    private final Set<String> drawable;

    public BestWordFinder(Matcher matcher) {
        this.matcher = matcher;
        this.words = matcher.getWords();
        this.drawable = new HashSet<>(words.getDrawable());
    }

    public String getBestWord(String input, Answer answer) {
        if (input != null && answer != null) {
            // remove invalid words from answer
            drawable.removeIf(candidate -> {
                Answer candidateAnswer = matcher.getAnswer(candidate, input);
                return !candidateAnswer.equals(answer);
            });
        }
        if (drawable.size() == 1) {
            return drawable.iterator().next();
        }
        int bscore = Integer.MIN_VALUE;
        String best = "";
        for (String proposition : words.getPlayable()) {
            int score = 0;
            for (String possibleWord : drawable) {
                Answer result = matcher.getAnswer(possibleWord, proposition);
                score = score + getScore(result);
            }
            if (score > bscore) {
                bscore = score;
                best = proposition;
            }
        }
        return best;
    }

    private int getScore(Answer result) {
        int s = 0;
        for (LetterAnswer letterAnswer : result.asArray()) {
            if (letterAnswer == LetterAnswer.OK) {
                s = s + 2;
            } else if (letterAnswer == LetterAnswer.MISPLACED) {
                s = s + 1;
            }
        }
        return s;
    }

}
