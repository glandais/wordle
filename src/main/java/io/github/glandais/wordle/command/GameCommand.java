package io.github.glandais.wordle.command;

import io.github.glandais.wordle.engine.*;
import io.github.glandais.wordle.game.Game;
import picocli.CommandLine;

@CommandLine.Command(name = "game", description = "Play game")
public class GameCommand implements Runnable {

    @CommandLine.Option(names = {"-l", "--locale"}, defaultValue = "FR",
            description = "Locale for word list")
    Locale locale = Locale.FR;

    @Override
    public void run() {
        Words words = new Words(locale);
        Matcher matcher = new Matcher(words);
        Game game = new Game(matcher);
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
