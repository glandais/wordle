package io.github.glandais.wordle;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
public class Word {

    String word;

    @EqualsAndHashCode.Exclude
    long freq;

}
