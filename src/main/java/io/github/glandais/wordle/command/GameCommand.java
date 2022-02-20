package io.github.glandais.wordle.command;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.Answers;
import io.github.glandais.wordle.engine.Words;
import io.github.glandais.wordle.game.Game;
import picocli.CommandLine;

import javax.inject.Inject;

@CommandLine.Command(name = "game", description = "Play game")
public class GameCommand implements Runnable {

    @Inject
    Words words;

    @Override
    public void run() {
        Game game = new Game(words);
        System.out.println("Enter try : ");
        boolean solved = false;
        while (!solved) {
            String test = Util.readFiveLetters();
            Answer result = game.tryWord(test);
            Util.printResult(result);
            if (result.equals(Answers.OK)) {
                solved = true;
            }
        }
    }
}
