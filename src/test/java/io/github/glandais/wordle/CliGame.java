package io.github.glandais.wordle;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.game.Game;
import io.github.glandais.wordle.engine.Words;
import lombok.SneakyThrows;

public class CliGame {

    @SneakyThrows
    public static void main(String[] args) {
        Words words = new Words();
        while (true) {
            Game game = new Game(words);
            System.out.println("Enter try : ");
            boolean solved = false;
            while (!solved) {
                String test = Util.readFiveLetters();
                Answer result = game.tryWord(test);
                int ok = Util.printResult(result);
                if (ok == 5) {
                    solved = true;
                }
            }
        }
    }

}
