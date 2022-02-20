package io.github.glandais.wordle;

import io.github.glandais.wordle.game.*;
import io.github.glandais.wordle.solver.BestWordFinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CliStats {

    public static void main(String[] args) {
        Words words = new Words();
        Matcher matcher = new Matcher(words);
        BestWordFinder bestWordFinder = new BestWordFinder();

        long c = words.getWordList().size();
        long attempts = words.getWordList().parallelStream()
                .map(word -> {
                    Game game = new Game(words, word);
                    Set<String> possible = new HashSet<>(words.getWordSet());
                    int attempt = 1;
                    while (true) {
                        String bestWord;
                        if (attempt == 1) {
                            bestWord = "RAIES";
                        } else {
                            bestWord = bestWordFinder.getBestWord2(matcher, possible);
                        }
                        Answer result = game.tryWord(bestWord);
                        if (result.equals(Answers.OK)) {
                            System.out.println(word + " " + attempt);
                            return attempt;
                        } else {
                            List<String> matching = matcher.matches(bestWord, result);
                            possible.removeIf(s -> !matching.contains(s));
                            attempt++;
                        }
                    }
                })
                .mapToInt(i -> i)
                .sum();

        System.out.println(c);
        System.out.println(attempts);
        System.out.println((1.0 * attempts) / (1.0 * c));
    }

}
