package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_TAG_OR_NEW_TAG_REQUIRED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddListingTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and adds tags to a listing
 */
public class AddListingTagCommandParser implements Parser<AddListingTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonCommand
     * and returns an AddPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddListingTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_NEW_TAG);
        Index index;

        checkCommandFormat(argMultimap);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }

        Set<String> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<String> newTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_NEW_TAG));

        return new AddListingTagCommand(index, tagList, newTagList);
    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasTags = !(argMultimap.getAllValues(PREFIX_TAG).isEmpty());
        boolean hasNewTags = !(argMultimap.getAllValues(PREFIX_NEW_TAG).isEmpty());
        boolean hasCombinedTags = hasTags || hasNewTags;

        if (!hasCombinedTags) {
            throw new ParseException(String.format(MESSAGE_TAG_OR_NEW_TAG_REQUIRED, AddListingTagCommand.MESSAGE_USAGE));
        }

    }

}
