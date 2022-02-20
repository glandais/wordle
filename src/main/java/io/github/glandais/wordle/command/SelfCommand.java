package io.github.glandais.wordle.command;

import io.github.glandais.wordle.engine.*;
import io.github.glandais.wordle.game.Game;
import io.github.glandais.wordle.solver.BestWordFinder;
import picocli.CommandLine;

@CommandLine.Command(name = "self", description = "Plays against himself")
public class SelfCommand implements Runnable {

    @CommandLine.Option(names = {"-l", "--locale"}, defaultValue = "FR",
            description = "Locale for word list")
    Locale locale = Locale.FR;

    @Override
    public void run() {
        Words words = new Words(locale);
        Matcher matcher = new Matcher(words);
        Game game = new Game(matcher);
        BestWordFinder bestWordFinder = new BestWordFinder(matcher);

        boolean solved = false;
        String bestWord = locale.getStartWord();
        while (!solved) {
            System.out.println(bestWord);
            Answer result = game.tryWord(bestWord);
            Util.printResult(result);
            if (result.equals(Answers.OK)) {
                solved = true;
            } else {
                bestWord = bestWordFinder.getBestWord(bestWord, result);
            }
        }
    }
}
