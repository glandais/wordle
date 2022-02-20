package io.github.glandais.wordle.engine;

import lombok.Value;

@Value
public class Answer {
    LetterAnswer[] answer;

    public Answer(LetterAnswer... answer) {
        this.answer = answer;
    }

    public LetterAnswer[] asArray() {
        return answer;
    }
}
