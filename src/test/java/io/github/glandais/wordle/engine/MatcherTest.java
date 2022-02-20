package io.github.glandais.wordle.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatcherTest {

    Words words = new Words();

    Matcher matcher = new Matcher(words);

    @Test
    public void test() {
        verify("ACABO", "ABCDZ",
                new Answer(LetterAnswer.OK, LetterAnswer.MISPLACED, LetterAnswer.MISPLACED, LetterAnswer.NOPE, LetterAnswer.NOPE)
        );
        verify("AAAAB", "AACAA",
                new Answer(LetterAnswer.OK, LetterAnswer.OK, LetterAnswer.NOPE, LetterAnswer.OK, LetterAnswer.MISPLACED)
        );
        verify("AABBB", "CCAAA",
                new Answer(LetterAnswer.NOPE, LetterAnswer.NOPE, LetterAnswer.MISPLACED, LetterAnswer.MISPLACED, LetterAnswer.NOPE)
        );
        verify("ABBBB", "CCAAC",
                new Answer(LetterAnswer.NOPE, LetterAnswer.NOPE, LetterAnswer.MISPLACED, LetterAnswer.NOPE, LetterAnswer.NOPE)
        );
        verify("AABBB", "ACAAC",
                new Answer(LetterAnswer.OK, LetterAnswer.NOPE, LetterAnswer.MISPLACED, LetterAnswer.NOPE, LetterAnswer.NOPE)
        );
    }

    private void verify(String solution, String input, Answer answer) {
        Answer answerChecked = matcher.getAnswerChecked(solution, input);
        assertEquals(answer, answerChecked);
    }

}
