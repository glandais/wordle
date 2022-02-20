package io.github.glandais.wordle.engine;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Words {

    // solution is in this list
    List<String> drawable;

    // words accepted for play
    List<String> playable;

    @SneakyThrows
    public Words() {
        drawable = new ArrayList<>();
        playable = new ArrayList<>();

        InputStream is = Words.class.getResourceAsStream("/words.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        while (line != null && !line.equals("")) {
            drawable.add(line);
            playable.add(line);
            line = br.readLine();
        }
        br.close();
    }

    public String getRandomDrawable() {
        return drawable.get(ThreadLocalRandom.current().nextInt(drawable.size()));
    }

}
