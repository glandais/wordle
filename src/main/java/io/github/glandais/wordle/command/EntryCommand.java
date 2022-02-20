package io.github.glandais.wordle.command;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {GameCommand.class, HelperCommand.class, SelfCommand.class})
public class EntryCommand {
}
