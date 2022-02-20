package io.github.glandais.wordle;

import io.github.glandais.wordle.game.Answer;
import io.github.glandais.wordle.game.LetterAnswer;
import io.github.glandais.wordle.game.Matcher;
import io.github.glandais.wordle.game.Words;
import io.github.glandais.wordle.solver.BestWordFinder;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CliSolver {

    private static Words words;
    private static Matcher matcher;
    private static Set<String> possible;
    private static BestWordFinder bestWordFinder;

    @SneakyThrows
    public static void main(String[] args) {
        words = new Words();
        matcher = new Matcher(words);
        bestWordFinder = new BestWordFinder();
        reset();
        while (true) {
            System.out.println("test string then result :");
            String test = Util.readFiveLetters();
            if (test.equals("RESET")) {
                reset();
                System.out.println("test string then result :");
                test = Util.readFiveLetters();
            }
            String answer = Util.readFiveLetters();
            LetterAnswer[] result = new LetterAnswer[5];
            for (int i = 0; i < answer.length(); i++) {
                result[i] = LetterAnswer.parse(answer.charAt(i) + "");
            }
            List<String> matching = matcher.matches(test, new Answer(result));
            possible.removeIf(s -> !matching.contains(s));
            String bestWord = CliSolver.bestWordFinder.getBestWord2(matcher, possible);
            System.out.println(bestWord);
        }
    }

    private static void reset() {
        System.out.println("RESET");
//        System.out.println(words.getWordList().get(ThreadLocalRandom.current().nextInt(words.getWordList().size())));
        possible = new HashSet<>(words.getWordSet());
        String bestWord = CliSolver.bestWordFinder.getBestWord2(matcher, possible);
        System.out.println(bestWord);
    }

}
