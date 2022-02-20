package io.github.glandais.wordle;

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

    public static int printResult(Answer result) {
        int ok = 0;
        for (LetterAnswer letterAnswer : result.asArray()) {
            System.out.print(letterAnswer.getSymbol());
            if (letterAnswer == LetterAnswer.OK) {
                ok++;
            }
        }
        System.out.println();
        return ok;
    }

}
