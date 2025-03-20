package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_ADD_LISTING_PREAMBLE_FOUND;
import static seedu.address.logic.Messages.MESSAGE_ADD_TAG_PREAMBLE_FOUND;
import static seedu.address.logic.Messages.MESSAGE_HOUSE_OR_UNIT_NUMBER_REQUIRED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_POSTAL_CODE_REQUIRED;
import static seedu.address.logic.Messages.MESSAGE_TAG_OR_NEW_TAG_PREFIX_EMPTY_VALUE;
import static seedu.address.logic.Messages.MESSAGE_TAG_OR_NEW_TAG_REQUIRED;
import static seedu.address.logic.commands.AddTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import java.util.Set;

import seedu.address.logic.commands.AddListingCommand;
import seedu.address.logic.commands.AddListingTagCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonCommand
     * and returns an AddPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NEW_TAG);
        checkCommandFormat(argMultimap);

        Set<String> newTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_NEW_TAG));

        return new AddTagCommand(newTagList);
    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasNewTags = !(argMultimap.getAllValues(PREFIX_NEW_TAG).isEmpty());
        boolean hasPreamble = !argMultimap.getPreamble().isEmpty();

        if (!hasNewTags) {
            throw new ParseException(String.format(MESSAGE_TAG_OR_NEW_TAG_PREFIX_EMPTY_VALUE,
                    AddTagCommand.MESSAGE_USAGE));
        }

        if (hasPreamble) {
            throw new ParseException(String.format(MESSAGE_ADD_TAG_PREAMBLE_FOUND, AddTagCommand.MESSAGE_USAGE));
        }
    }
}
