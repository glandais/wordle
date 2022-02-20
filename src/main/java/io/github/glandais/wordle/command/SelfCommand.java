package io.github.glandais.wordle.command;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.Answers;
import io.github.glandais.wordle.engine.Matcher;
import io.github.glandais.wordle.engine.Words;
import io.github.glandais.wordle.game.Game;
import io.github.glandais.wordle.solver.BestWordFinder;
import picocli.CommandLine;

import javax.inject.Inject;

@CommandLine.Command(name = "self", description = "Plays against himself")
public class SelfCommand implements Runnable {

    @Inject
    Words words;

    @Inject
    Matcher matcher;

    @Override
    public void run() {
        Game game = new Game(words);
        BestWordFinder bestWordFinder = new BestWordFinder(matcher);

        boolean solved = false;
        String bestWord = "RAIES";
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
