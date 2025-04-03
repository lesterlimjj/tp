package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_ARGUMENTS_EMPTY;
import static seedu.address.logic.Messages.MESSAGE_ONE_INDEX_EXPECTED;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SearchOwnerListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SearchOwnerListingCommand} object.
 */
public class SearchOwnerListingCommandParser implements Parser<SearchOwnerListingCommand> {
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final int EXPECTED_PREAMBLE_PARTS = 1;
    /**
     * Parses the given {@code String} of arguments in the context of the SearchOwnerListingCommand
     * and returns a SearchOwnerListingCommand object for execution.
     *
     * @param args The arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SearchOwnerListingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        checkCommandFormat(argMultimap, args);
        Index index = ParserUtil.parseIndex(args);
        return new SearchOwnerListingCommand(index);
    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap, String args) throws ParseException {
        String preamble = argMultimap.getPreamble().trim();
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_ARGUMENTS_EMPTY,
                    SearchOwnerListingCommand.MESSAGE_USAGE));
        }

        if (preamble.isEmpty() || preamble.split(WHITESPACE_REGEX).length != EXPECTED_PREAMBLE_PARTS) {
            throw new ParseException(String.format(MESSAGE_ONE_INDEX_EXPECTED,
                    SearchOwnerListingCommand.MESSAGE_USAGE));
        }
    }

}
