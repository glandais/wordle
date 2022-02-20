package io.github.glandais.wordle.command;

import io.github.glandais.wordle.engine.*;
import io.github.glandais.wordle.game.Game;
import io.github.glandais.wordle.solver.BestWordFinder;
import picocli.CommandLine;

@CommandLine.Command(name = "stats", description = "Test all words")
public class StatsCommand implements Runnable {

    @CommandLine.Option(names = {"-l", "--locale"}, defaultValue = "FR",
            description = "Locale for word list")
    Locale locale = Locale.FR;

    @Override
    public void run() {
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
                            if (attempt > 6) {
                                System.out.println(solution + " " + attempt);
                            }
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
