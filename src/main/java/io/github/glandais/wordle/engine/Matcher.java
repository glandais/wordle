package io.github.glandais.wordle.engine;

import java.util.HashSet;
import java.util.Set;

public class Matcher {

    final Words words;
    final Set<String> playableSet;

    public Matcher(Words words) {
        this.words = words;
        this.playableSet = new HashSet<>(words.getPlayable());
    }

    public Words getWords() {
        return words;
    }

    public Answer getAnswer(String solution, String input) {
        if (!playableSet.contains(input)) {
            System.err.println("Invalid word");
            return Answers.NONE;
        }
        return getAnswerChecked(solution, input);
    }

    protected Answer getAnswerChecked(String solution, String input) {
        int size = input.length();

        LetterAnswer[] answer = new LetterAnswer[size];
        char[] solutionChars = solution.toCharArray();
        char[] inputChars = input.toCharArray();

        for (int i = 0; i < size; i++) {

            if (inputChars[i] == solutionChars[i]) {
                // easy part
                answer[i] = LetterAnswer.OK;
            } else {
                char searchedChar = inputChars[i];

                // count misplaced searchedChar
                int count = 0;
                // available searchedChar in input up to i
                int misplacedIndex = 0;
                for (int j = 0; j < size; j++) {
                    // discard correct char
                    if (inputChars[j] != solutionChars[j]) {
                        // misplaced char
                        if (searchedChar == solutionChars[j]) {
                            count++;
                        }
                        // available char
                        if (j <= i && searchedChar == inputChars[j]) {
                            misplacedIndex++;
                        }
                    }
                }

                if (count == 0 || misplacedIndex > count) {
                    answer[i] = LetterAnswer.NOPE;
                } else {
                    answer[i] = LetterAnswer.MISPLACED;
                }
            }
        }
        return new Answer(answer);
    }

}
