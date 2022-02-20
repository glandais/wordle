package io.github.glandais.wordle;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.Matcher;
import io.github.glandais.wordle.engine.Words;
import io.github.glandais.wordle.game.Game;
import io.github.glandais.wordle.solver.BestWordFinder;

public class CliSelf {

    public static void main(String[] args) {
        Words words = new Words();
        Matcher matcher = new Matcher(words);
        BestWordFinder bestWordFinder = new BestWordFinder(matcher);

//        while (true) {
            Game game = new Game(words, "MINES");

            boolean solved = false;
            int attempt = 1;
            String bestWord = "RAIES";
            while (!solved) {
                System.out.println(bestWord);

                Answer result = game.tryWord(bestWord);
                int ok = Util.printResult(result);
                if (ok == 5) {
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
