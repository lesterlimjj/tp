package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a FindCommand object.
     *
     * @param args User input arguments.
     * @return FindCommand instance with extracted keywords.
     * @throws ParseException if no keywords are provided.
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException("ERROR: Missing parameters. You must provide at least one keyword.");
        }

        List<String> keywords = Arrays.stream(args.trim().split("\\s+"))
                .collect(Collectors.toList());

        try {
            return new FindCommand(keywords);
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
