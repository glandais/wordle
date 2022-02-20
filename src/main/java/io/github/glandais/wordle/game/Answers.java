package io.github.glandais.wordle.game;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Answers {

    public static Answer NONE = new Answer(LetterAnswer.MISPLACED, LetterAnswer.MISPLACED, LetterAnswer.MISPLACED, LetterAnswer.MISPLACED, LetterAnswer.MISPLACED);

    public static Answer OK = new Answer(LetterAnswer.OK, LetterAnswer.OK, LetterAnswer.OK, LetterAnswer.OK, LetterAnswer.OK);

}
