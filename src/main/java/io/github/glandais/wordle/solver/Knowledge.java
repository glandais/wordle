package io.github.glandais.wordle.solver;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.LetterAnswer;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class Knowledge {

    // letters known in word (0 = no knowledge)
    char[] ok = new char[5];

    // letters somewhere (false = no knowledge)
    boolean[] contains = new boolean[26];

    // letters not in word (false = no knowledge)
    boolean[] nope = new boolean[26];

    public Knowledge learn(String input, Answer answer) {
        Knowledge result = new Knowledge();
        result.ok = Arrays.copyOf(ok, ok.length);
        result.contains = Arrays.copyOf(contains, contains.length);
        result.nope = Arrays.copyOf(nope, nope.length);

        char[] inputChars = input.toCharArray();
        LetterAnswer[] letterAnswers = answer.asArray();
        for (int i = 0; i < 5; i++) {
            char inputChar = inputChars[i];
            int index = index(inputChar);
            if (letterAnswers[i] == LetterAnswer.OK) {
                result.ok[i] = inputChar;
                result.contains[index] = true;
            } else if (letterAnswers[i] == LetterAnswer.MISPLACED) {
                result.contains[index] = true;
            } else if (letterAnswers[i] == LetterAnswer.NOPE) {
                result.nope[index] = true;
            }
        }

        return result;
    }

    private int index(char inputChar) {
        return inputChar - 'A';
    }

    private char charOf(int index) {
        return (char) (index + 'A');
    }

    @Override
    public String toString() {
        String containsString = getString(this.contains);
        String nopeString = getString(this.nope);
        return Arrays.toString(ok) + " (contains " + containsString + ", not contains " + nopeString + ")";
    }

    private String getString(boolean[] array) {
        String s = "";
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                s = s + charOf(i);
            }
        }
        return s;
    }
}
