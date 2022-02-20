package io.github.glandais.wordle;

import io.github.glandais.wordle.command.Util;
import io.github.glandais.wordle.engine.*;
import io.github.glandais.wordle.game.Game;
import io.github.glandais.wordle.solver.BestWordFinder;

public class CliSelf {

    public static void main(String[] args) {
        Locale locale = Locale.EN;
        Words words = new Words(locale);
        Matcher matcher = new Matcher(words);
        BestWordFinder bestWordFinder = new BestWordFinder(matcher);

//        while (true) {
            Game game = new Game(matcher);

            boolean solved = false;
            int attempt = 1;
            String bestWord = locale.getStartWord();
            while (!solved) {
                System.out.println(bestWord);

                Answer result = game.tryWord(bestWord);
                Util.printResult(result);
                if (result.equals(Answers.OK)) {
                    solved = true;
                    System.out.println("************************* " + attempt);
                    bestWordFinder = new BestWordFinder(matcher);
                } else {
                    bestWord = bestWordFinder.getBestWord(bestWord, result);
                    attempt++;
                }
            }
//        }
    }

}
