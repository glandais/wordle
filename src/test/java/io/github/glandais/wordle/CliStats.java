package io.github.glandais.wordle;

import io.github.glandais.wordle.engine.*;
import io.github.glandais.wordle.game.Game;
import io.github.glandais.wordle.solver.BestWordFinder;

public class CliStats {

    public static void main(String[] args) {
        Locale locale = Locale.FR;
        Words words = new Words(locale);
        Matcher matcher = new Matcher(words);

        long c = words.getDrawable().size();
        long attempts = words.getDrawable().parallelStream()
                .map(solution -> {
                    BestWordFinder bestWordFinder = new BestWordFinder(matcher);
                    Game game = new Game(matcher, solution);
                    int attempt = 1;
                    String bestWord = locale.getStartWord();
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
