package io.github.glandais.wordle.game;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Words {

    List<String> wordList;

    Set<String> wordSet;

    @SneakyThrows
    public Words() {
        wordList = new ArrayList<>();
        wordSet = new HashSet<>();

        InputStream is = Words.class.getResourceAsStream("/words.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        while (line != null && !line.equals("")) {
            wordList.add(line);
            wordSet.add(line);
            line = br.readLine();
        }
        br.close();
    }

}
