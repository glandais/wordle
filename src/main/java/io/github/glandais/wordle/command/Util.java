package io.github.glandais.wordle.command;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.LetterAnswer;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Util {

    static BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    @SneakyThrows
    public static String readFiveLetters() {
        String t;
        do {
            t = reader.readLine();
            if (t.length() != 5) {
                System.err.println("5 letters required");
            }
        } while (t.length() != 5);
        return t;
    }

    public static void printResult(Answer result) {
        for (LetterAnswer letterAnswer : result.asArray()) {
            System.out.print(letterAnswer.getSymbol());
        }
        System.out.println();
    }

}
