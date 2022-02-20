package io.github.glandais.wordle;

import io.github.glandais.wordle.game.*;
import io.github.glandais.wordle.solver.BestWordFinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CliSelf {

    public static void main(String[] args) {
        Words words = new Words();
        Matcher matcher = new Matcher(words);
        BestWordFinder bestWordFinder = new BestWordFinder();

        while (true) {
            Game game = new Game(words, "BOBOS");
            Set<String> possible = new HashSet<>(words.getWordSet());

            boolean solved = false;
            int attempt = 1;
            while (!solved) {
                String bestWord;
                if (attempt == 1) {
                    bestWord = "RAIES";
                } else {
                    bestWord = bestWordFinder.getBestWord2(matcher, possible);
                }
                System.out.println(bestWord);

                Answer result = game.tryWord(bestWord);
                int ok = Util.printResult(result);
                if (ok == 5) {
                    solved = true;
                    System.out.println("************************* " + attempt);
                } else {
                    List<String> matching = matcher.matches(bestWord, result);
                    possible.removeIf(s -> !matching.contains(s));
                    attempt++;
                }
            }
        }
    }

}
