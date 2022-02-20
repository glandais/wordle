package io.github.glandais.wordle;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.*;

public class PrepareData {

    @SneakyThrows
    public static void main(String[] args) {
        String s = "/ 874";
        for (int i = 0; i < s.length(); i++) {
            System.out.print((char) (s.charAt(i) ^ 'u'));
        }
        System.out.println();

        test();
/*
        Scanner scanner = new Scanner(new FileInputStream("Lexique383/Lexique383.tsv"));
        int i = 0;
        int c = 0;
        Map<String, Word> words = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (i > 0) {
                String[] tokens = line.split("\t");
                if (isWord(tokens[0])) {
                    String clean = clean(tokens[0]);
                    if ("1".equals(tokens[13])
                            && clean.length() == 5
                    ) {

                        long freq = (long) (100 * (Double.parseDouble(tokens[6]) + Double.parseDouble(tokens[7])));
                        Word word = words.get(clean);
                        if (word != null) {
                            word = new Word(clean, word.getFreq() + freq);
                        } else {
                            word = new Word(clean, freq);
                        }
                        words.put(clean, word);
                        c++;
                    }
                }
            }
            i++;
        }

        List<Word> wordsByFrequency = new ArrayList<>(words.values());
        wordsByFrequency.sort(Comparator.comparing(Word::getFreq));

        List<Word> wordsClass = new ArrayList<>(wordsByFrequency.size());
        i = 0;
        for (Word word : wordsByFrequency) {
            long clazz = 1 + ((10L * i) / wordsByFrequency.size());
            wordsClass.add(new Word(word.getWord(), clazz));
            i++;
        }

        for (Word word : wordsClass) {
            System.out.println(word);
        }
        System.out.println(c);

 */
    }

    @SneakyThrows
    private static void test() {

        BufferedWriter bw = new BufferedWriter(new FileWriter("words.txt"));

        Scanner scanner = new Scanner(new FileInputStream("Lexique383/five"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String w = "";
            for (int i = 0; i < 5; i++) {
                w = w + (char) (line.charAt(i) ^ 'u');
            }
            System.out.print(w + " " + line);
            System.out.println(line);
            bw.write(w);
            bw.newLine();
        }

        bw.close();
    }

    private static String clean(String token) {
        return StringUtils.stripAccents(token);
    }

    private static boolean isWord(String token) {
        if (token.contains(" ") || token.contains("-") || token.contains("'")) {
            return false;
        }
        return true;
    }

}
