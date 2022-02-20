package io.github.glandais.wordle.game;

public record Answer(
        LetterAnswer l1,
        LetterAnswer l2,
        LetterAnswer l3,
        LetterAnswer l4,
        LetterAnswer l5
) {
    public Answer(LetterAnswer[] answer) {
        this(
                answer[0],
                answer[1],
                answer[2],
                answer[3],
                answer[4]
        );
    }

    public LetterAnswer[] asArray() {
        return new LetterAnswer[]{
                l1,
                l2,
                l3,
                l4,
                l5
        };
    }
}
