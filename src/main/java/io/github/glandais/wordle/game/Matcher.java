package io.github.glandais.wordle.game;

import java.util.ArrayList;
import java.util.List;

public class Matcher {

    final Words words;

    public Matcher(Words words) {
        this.words = words;
    }

    public Words getWords() {
        return words;
    }

    public Answer tryWord(String solution, String test) {
        if (!words.getWordSet().contains(test)) {
            System.err.println("Invalid word");
            return Answers.NONE;
        }
        LetterAnswer[] answer = new LetterAnswer[test.length()];
        List<Character> chars = new ArrayList<>();
        for (int i = 0; i < test.length(); i++) {
            chars.add(solution.charAt(i));
        }

        for (int i = 0; i < test.length(); i++) {
            Character t = test.charAt(i);
            Character w = solution.charAt(i);
            if (t.equals(w)) {
                answer[i] = LetterAnswer.OK;
                chars.remove(w);
            } else if (!solution.contains("" + t)) {
                answer[i] = LetterAnswer.NOPE;
            } else {
                answer[i] = LetterAnswer.MISPLACED;
            }
        }

        for (int i = 0; i < test.length(); i++) {
            if (answer[i] == LetterAnswer.MISPLACED) {
                Character t = test.charAt(i);
                if (chars.contains(t)) {
                    chars.remove(t);
                } else {
                    answer[i] = LetterAnswer.NOPE;
                }
            }
        }

        return new Answer(answer);
    }

    public List<String> matches(String input, Answer answer) {
        List<String> result = new ArrayList<>();
        for (String test : words.getWordList()) {
            Answer attempt = tryWord(test, input);
            if (attempt.equals(answer)) {
                result.add(test);
            }
        }
        return result;
    }

}
