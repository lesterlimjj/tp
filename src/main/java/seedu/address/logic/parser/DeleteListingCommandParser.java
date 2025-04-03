package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_ARGUMENTS_EMPTY;
import static seedu.address.logic.Messages.MESSAGE_ONE_INDEX_EXPECTED;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteListingCommand} object.
 */
public class DeleteListingCommandParser implements Parser<DeleteListingCommand> {
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final int EXPECTED_PREAMBLE_PARTS = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteListingCommand
     * and returns a DeleteListingCommand object for execution.
     *
     * @param args The arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteListingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        checkCommandFormat(argMultimap, args);
        Index index = ParserUtil.parseIndex(args);
        return new DeleteListingCommand(index);
    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap, String args) throws ParseException {
        String preamble = argMultimap.getPreamble().trim();
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_ARGUMENTS_EMPTY,
                    DeleteListingCommand.MESSAGE_USAGE));
        }

        if (preamble.isEmpty() || preamble.split(WHITESPACE_REGEX).length != EXPECTED_PREAMBLE_PARTS) {
            throw new ParseException(String.format(MESSAGE_ONE_INDEX_EXPECTED,
                    DeleteListingCommand.MESSAGE_USAGE));
        }
    }

}
