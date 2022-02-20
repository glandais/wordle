package io.github.glandais.wordle.solver;

import io.github.glandais.wordle.game.Answer;
import io.github.glandais.wordle.game.Answers;
import io.github.glandais.wordle.game.LetterAnswer;
import io.github.glandais.wordle.game.Matcher;

import java.util.Set;

public class BestWordFinder {

    public String getBestWord(Matcher matcher, Set<String> possibleWords) {
        if (possibleWords.size() == 1) {
            return possibleWords.iterator().next();
        }
        int min = Integer.MAX_VALUE;
        String best = "";
        int bok = 0;
        int bnok = 0;
        for (String proposition : matcher.getWords().getWordSet()) {
            int ok = 0;
            int nok = 0;
            for (String possibleWord : possibleWords) {
                Answer result = matcher.tryWord(possibleWord, proposition);
                if (result.equals(Answers.NONE)) {
                    nok++;
                } else {
                    ok++;
                }
            }
            int diff = Math.abs(ok - nok);
            if (diff < min) {
                min = diff;
                best = proposition;
                bok = ok;
                bnok = nok;
                System.out.println(best + " " + bok + " " + bnok);
            }
        }
        System.out.println(best + " " + bok + " " + bnok);
        return best;
    }

    public String getBestWord2(Matcher matcher, Set<String> possibleWords) {
        if (possibleWords.size() == 1) {
            return possibleWords.iterator().next();
        }
        int bscore = Integer.MIN_VALUE;
        String best = "";
        for (String proposition : matcher.getWords().getWordSet()) {
            int score = 0;
            for (String possibleWord : possibleWords) {
                Answer result = matcher.tryWord(possibleWord, proposition);
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
