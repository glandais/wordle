package io.github.glandais.wordle.engine;

import lombok.Getter;
import lombok.SneakyThrows;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
@Getter
public class Words {

    // solution is in this list
    List<String> drawable;

    // words accepted for play
    List<String> playable;

    @SneakyThrows
    public Words() {
        drawable = readWords("/words-drawable.txt");
        List<String> extraPlayable = readWords("/words-playable.txt");
        Set<String> allPlayable = new TreeSet<>(drawable);
        allPlayable.addAll(extraPlayable);
        playable = new ArrayList<>(allPlayable);
    }

    private List<String> readWords(String wordsResource) throws IOException {
        List<String> words = new ArrayList<>();
        InputStream is = Words.class.getResourceAsStream(wordsResource);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        while (line != null && !line.equals("")) {
            words.add(line);
            line = br.readLine();
        }
        br.close();
        return words;
    }

    public String getRandomDrawable() {
        return drawable.get(ThreadLocalRandom.current().nextInt(drawable.size()));
    }

}
