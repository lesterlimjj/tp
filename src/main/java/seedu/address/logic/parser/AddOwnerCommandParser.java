package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_ARGUMENTS_EMPTY;
import static seedu.address.logic.Messages.MESSAGE_EXPECTED_TWO_INDICES;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddOwnerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddOwnerCommand} object.
 */
public class AddOwnerCommandParser implements Parser<AddOwnerCommand> {
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final int EXPECTED_PREAMBLE_PARTS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the AddOwnerCommand
     * and returns an AddOwnerCommand object for execution.
     *
     * @param args The arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddOwnerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);
        Index personIndex;
        Index listingIndex;

        checkCommandFormat(argMultimap, args);
        List<Index> multipleIndices = ParserUtil.parseMultipleIndices(argMultimap.getPreamble());
        personIndex = multipleIndices.get(0);
        listingIndex = multipleIndices.get(1);
        return new AddOwnerCommand(personIndex, listingIndex);

    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap, String args) throws ParseException {
        String preamble = argMultimap.getPreamble().trim();

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_ARGUMENTS_EMPTY,
                    AddOwnerCommand.MESSAGE_USAGE));
        }

        if (preamble.isEmpty() || preamble.split(WHITESPACE_REGEX).length != EXPECTED_PREAMBLE_PARTS) {
            throw new ParseException(String.format(MESSAGE_EXPECTED_TWO_INDICES,
                    AddOwnerCommand.MESSAGE_USAGE));
        }

    }
}
