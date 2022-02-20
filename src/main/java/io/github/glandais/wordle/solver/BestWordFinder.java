package io.github.glandais.wordle.solver;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.LetterAnswer;
import io.github.glandais.wordle.engine.Matcher;
import io.github.glandais.wordle.engine.Words;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BestWordFinder {

    private final Matcher matcher;
    private final Words words;
    private final Set<String> drawable;
    private Knowledge knowledge;

    public BestWordFinder(Matcher matcher) {
        this.matcher = matcher;
        this.words = matcher.getWords();
        this.drawable = new HashSet<>(words.getDrawable());
        this.knowledge = new Knowledge();
    }

    public String getBestWord(String input, Answer answer) {
        if (input != null && answer != null) {
            int previousSize = drawable.size();
            // remove invalid words from answer
            drawable.removeIf(candidate -> {
                Answer candidateAnswer = matcher.getAnswer(candidate, input);
                return !candidateAnswer.equals(answer);
            });
//            log.debug(previousSize + " -> " + drawable.size());
//            System.out.println(previousSize + " -> " + drawable.size());
            this.knowledge = this.knowledge.learn(input, answer);
        }
        if (drawable.size() == 1) {
            return drawable.iterator().next();
        }
        int bscore = Integer.MIN_VALUE;
        String best = "";
        for (String proposition : words.getPlayable()) {
//            for (String proposition : drawable) {
            int score = getScoreForDrawable(proposition);
            if (score > bscore) {
                bscore = score;
                best = proposition;
            }
        }
        return best;
    }

    private int getScoreForDrawable(String proposition) {
        int score = 0;
        for (String possibleWord : drawable) {
            Answer possibleAnswer = matcher.getAnswer(possibleWord, proposition);
            Knowledge possibleKnowledge = this.knowledge.learn(proposition, possibleAnswer);
//                score = score + getScore(possibleAnswer);
            score = score + getScore(knowledge, possibleKnowledge);
        }
        return score;
    }

    private int getScore(Knowledge knowledge, Knowledge possibleKnowledge) {
        int s = 0;
        for (int i = 0; i < possibleKnowledge.ok.length; i++) {
            if (possibleKnowledge.ok[i] != knowledge.ok[i]) {
                s = s + 5;
            }
        }
        for (int i = 0; i < possibleKnowledge.contains.length; i++) {
            if (possibleKnowledge.contains[i] != knowledge.contains[i]) {
                s = s + 3;
            }
        }
        for (int i = 0; i < possibleKnowledge.nope.length; i++) {
            if (possibleKnowledge.nope[i] != knowledge.nope[i]) {
                s = s + 1;
            }
        }
        return s;
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
