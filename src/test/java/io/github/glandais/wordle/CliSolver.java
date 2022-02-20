package io.github.glandais.wordle;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.LetterAnswer;
import io.github.glandais.wordle.engine.Matcher;
import io.github.glandais.wordle.engine.Words;
import io.github.glandais.wordle.solver.BestWordFinder;
import lombok.SneakyThrows;

public class CliSolver {

    private static Words words;
    private static Matcher matcher;
    private static BestWordFinder bestWordFinder;

    @SneakyThrows
    public static void main(String[] args) {
        words = new Words();
        matcher = new Matcher(words);
        bestWordFinder = new BestWordFinder(matcher);
        while (true) {
            System.out.println("test string then result :");
            String input = Util.readFiveLetters();
            if (input.equals("RESET")) {
                bestWordFinder = new BestWordFinder(matcher);
                System.out.println("test string then result :");
                input = Util.readFiveLetters();
            }
            String answer = Util.readFiveLetters();
            LetterAnswer[] result = new LetterAnswer[5];
            for (int i = 0; i < answer.length(); i++) {
                result[i] = LetterAnswer.parse(answer.charAt(i) + "");
            }
            String bestWord = CliSolver.bestWordFinder.getBestWord(input, new Answer(result));
            System.out.println(bestWord);
        }
    }

}
