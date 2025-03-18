package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOWER_BOUND_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UPPER_BOUND_PRICE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddListingTagCommand;
import seedu.address.logic.commands.AddPreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.price.Price;
import seedu.address.model.price.PriceRange;

/*
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

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddListingTagCommand.MESSAGE_USAGE), pe);
        }

        boolean hasTags = !(argMultimap.getAllValues(PREFIX_TAG).isEmpty());
        boolean hasNewTags = !(argMultimap.getAllValues(PREFIX_NEW_TAG).isEmpty());
        boolean hasCombinedTags = hasTags || hasNewTags;

        if (!hasCombinedTags) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPreferenceCommand.MESSAGE_USAGE));
        }

        Set<String> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<String> newTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_NEW_TAG));

        return new AddListingTagCommand(index, tagList, newTagList);
    }

}
