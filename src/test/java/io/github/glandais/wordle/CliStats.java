package io.github.glandais.wordle;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.Answers;
import io.github.glandais.wordle.engine.Matcher;
import io.github.glandais.wordle.engine.Words;
import io.github.glandais.wordle.game.Game;
import io.github.glandais.wordle.solver.BestWordFinder;

public class CliStats {

    public static void main(String[] args) {
        Words words = new Words();
        Matcher matcher = new Matcher(words);

        long c = words.getDrawable().size();
        long attempts = words.getDrawable().parallelStream()
                .map(solution -> {
                    BestWordFinder bestWordFinder = new BestWordFinder(matcher);
                    Game game = new Game(words, solution);
                    int attempt = 1;
                    String bestWord = "RAIES";
                    while (true) {
                        Answer result = game.tryWord(bestWord);
                        if (result.equals(Answers.OK)) {
                            System.out.println(solution + " " + attempt);
                            return attempt;
                        } else {
                            bestWord = bestWordFinder.getBestWord(bestWord, result);
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
