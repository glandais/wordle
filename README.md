# wordle

A command-line Wordle game and solver, in French and English, built with [Quarkus](https://quarkus.io/) and [Picocli](https://picocli.info/).

## Requirements

- Java 17+
- Maven is not needed: use the bundled wrapper `./mvnw`

## Build & run

```shell
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar <command> [-l FR|EN]
```

Every command accepts `-l` / `--locale` to pick the word list (`FR` by default).

| Command  | Description |
|----------|-------------|
| `game`   | Play against a random word. Type a 5-letter guess and get the feedback. |
| `helper` | Solver assistant for a game played elsewhere: type your guess, then the feedback you got, and it suggests the next word to try. |
| `self`   | The solver plays a random word by itself. |
| `stats`  | The solver plays every possible solution and prints the number of words, the total attempts and the average attempts per word. Words that needed more than 6 attempts are printed too. |

Words are **uppercase**, without accents (e.g. `RAIES`, `SOARE`).

### Feedback format

| Symbol | Meaning |
|--------|---------|
| `O`    | right letter, right spot |
| `.`    | letter in the word, wrong spot |
| `X`    | letter not in the word |

Example (`self -l EN`):

```
SOARE
.OXXO
PLUSH
XXOOX
MOUSE
OOOOO
```

With `helper`, enter the guess and its feedback on two separate lines (e.g. `SOARE` then `.OXXO`). Entering `OOOOO` ends the session.

## How the solver works

- **Word lists** (`src/main/resources`): `words-<locale>-drawable.txt` holds the possible solutions, and `words-<locale>-playable.txt` holds extra words accepted as guesses. The French lists come from the [Lexique 3.83](http://www.lexique.org/) database.
- **Opening word**: fixed per language, `RAIES` (FR) or `SOARE` (EN).
- **Next guess**: after each feedback, the solver drops the solutions that don't match it. Then it scores every playable word by how much it would reveal across the remaining solutions (+5 per newly known position, +3 per newly known present letter, +1 per newly known absent letter). The highest-scoring word is played. Once only one solution remains, it plays that word.

## Development

```shell
./mvnw compile quarkus:dev -Dquarkus.args='self -l EN'   # dev mode with live reload
./mvnw verify                                           # build + tests (run by CI on pull requests)
./mvnw test -Dtest=MatcherTest                          # single test
```

## Native executable

```shell
./mvnw package -Pnative
# or, without GraalVM installed:
./mvnw package -Pnative -Dquarkus.native.container-build=true

./target/wordle-1.0.0-SNAPSHOT-runner self
```

Dockerfiles for JVM and native images are in `src/main/docker`.
