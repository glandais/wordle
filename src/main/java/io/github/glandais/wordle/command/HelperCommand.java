package io.github.glandais.wordle.command;

import io.github.glandais.wordle.engine.Answer;
import io.github.glandais.wordle.engine.Answers;
import io.github.glandais.wordle.engine.LetterAnswer;
import io.github.glandais.wordle.engine.Matcher;
import io.github.glandais.wordle.solver.BestWordFinder;
import picocli.CommandLine;

import javax.inject.Inject;

@CommandLine.Command(name = "helper", description = "Help for game")
public class HelperCommand implements Runnable {

    @Inject
    Matcher matcher;

    @Override
    public void run() {
        BestWordFinder bestWordFinder = new BestWordFinder(matcher);

        boolean solved = false;
        while (!solved) {
            String input = Util.readFiveLetters();
            String answer = Util.readFiveLetters();
            LetterAnswer[] result = new LetterAnswer[5];
            for (int i = 0; i < answer.length(); i++) {
                result[i] = LetterAnswer.parse(answer.charAt(i) + "");
            }
            Answer answer1 = new Answer(result);
            if (answer1.equals(Answers.OK)) {
                solved = true;
            } else {
                String bestWord = bestWordFinder.getBestWord(input, answer1);
                System.out.println(bestWord);
            }
        }
    }
}
