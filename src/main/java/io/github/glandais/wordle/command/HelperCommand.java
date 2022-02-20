package io.github.glandais.wordle.command;

import io.github.glandais.wordle.engine.*;
import io.github.glandais.wordle.solver.BestWordFinder;
import picocli.CommandLine;

@CommandLine.Command(name = "helper", description = "Help for game")
public class HelperCommand implements Runnable {

    @CommandLine.Option(names = {"-l", "--locale"}, defaultValue = "FR",
            description = "Locale for word list")
    Locale locale = Locale.FR;

    @Override
    public void run() {
        Words words = new Words(locale);
        Matcher matcher = new Matcher(words);
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
