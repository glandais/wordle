# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Wordle game + solver as a Quarkus/Picocli command-line app (Java 17, Lombok). Supports French and English word lists.

## Commands

```shell
./mvnw verify                                   # build + tests (what CI runs: ./mvnw -B -ntp verify)
./mvnw test -Dtest=MatcherTest                  # single test class
./mvnw test -Dtest=MatcherTest#test             # single test method
./mvnw compile quarkus:dev -Dquarkus.args='self -l EN'   # dev mode with CLI args
./mvnw package && java -jar target/quarkus-app/quarkus-run.jar stats -l FR
./mvnw package -Pnative                         # native build (add -Dquarkus.native.container-build=true without GraalVM)
```

Subcommands (all take `-l/--locale FR|EN`, default `FR`): `game` (play vs. random word), `helper` (reads your guess then the feedback line, prints next suggested word), `self` (solver plays a random word), `stats` (solver plays every drawable word in parallel, prints count / total attempts / average).

## Architecture

- `engine/` — core rules. `Words` loads `words-<locale>-drawable.txt` (possible solutions) and `words-<locale>-playable.txt` (extra accepted guesses; `playable` = union of both, sorted) from classpath resources. `Matcher.getAnswer(solution, input)` computes Wordle feedback with correct duplicate-letter handling; it returns `Answers.NONE` for words not in the playable set, while `getAnswerChecked` skips that check (used by `MatcherTest`). `Locale` holds the fixed opening word per language (`RAIES` / `SOARE`).
- `solver/` — `BestWordFinder` is **stateful per game**: each `getBestWord(lastGuess, answer)` prunes its internal candidate set of drawable words and updates a `Knowledge` (known positions, present letters, absent letters). It then scores every playable word by summing, over remaining candidates, how much `Knowledge` would gain (5 per new position, 3 per new present letter, 1 per new absent letter). Create a new instance for each game.
- `game/Game` — holds a solution and counts tries.
- `command/` — Picocli subcommands wired through `EntryCommand` (`@TopCommand`). `Util` reads 5-letter lines from stdin and prints answers.

Feedback symbols (`LetterAnswer`): `O` = correct spot, `.` = misplaced, `X` = absent — this is also the input format for the `helper` command. Words are uppercase A–Z only (no accents; `Knowledge` indexes letters as `c - 'A'`).

## Notes

- Word list files must end at the first empty line (`Words.readWords` stops there). Native builds need them listed via `quarkus.native.resources.includes` in `application.properties`.
- `src/test/java/.../Cli*.java` and `PrepareData.java` are standalone `main()` scratch programs, not JUnit tests. `PrepareData` generated the French lists from the `Lexique383/` lexicon (local, not tracked in git). `MatcherTest` is the only real test.
