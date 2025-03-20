package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EXPECTED_TWO_INDICES;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_OR_PREFERENCE_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_TAG_OR_NEW_TAG_REQUIRED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddListingTagCommand;
import seedu.address.logic.commands.AddPreferenceTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a AddPreferenceTagCommand object.
 */
public class AddPreferenceTagCommandParser implements Parser<AddPreferenceTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePreferenceTagCommand
     * and returns an DeletePreferenceTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPreferenceTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_NEW_TAG);
        Index personIndex;
        Index preferenceIndex;

        checkCommandFormat(argMultimap, args);
        List<Index> multipleIndices;

        try {
            multipleIndices = ParserUtil.parseMultipleIndices(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_OR_PREFERENCE_DISPLAYED_INDEX,
                    AddPreferenceTagCommand.MESSAGE_USAGE),
                    pe);
        }

        try {
            if (multipleIndices.size() != 2) {
                throw new ParseException("Expected 2 indices");
            }
            personIndex = multipleIndices.get(0);
            preferenceIndex = multipleIndices.get(1);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_EXPECTED_TWO_INDICES,
                    AddPreferenceTagCommand.MESSAGE_USAGE),
                    pe);
        }

        Set<String> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<String> newTagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_NEW_TAG));

        return new AddPreferenceTagCommand(personIndex, preferenceIndex, tagSet, newTagSet);
    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap, String args) throws ParseException {
        String preamble = argMultimap.getPreamble();
        boolean hasTags = !(argMultimap.getAllValues(PREFIX_TAG).isEmpty());
        boolean hasNewTags = !(argMultimap.getAllValues(PREFIX_NEW_TAG).isEmpty());
        boolean hasCombinedTags = hasTags || hasNewTags;

        if (!hasCombinedTags) {
            throw new ParseException(String.format(MESSAGE_TAG_OR_NEW_TAG_REQUIRED, AddPreferenceTagCommand.MESSAGE_USAGE));
        }

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPreferenceTagCommand.MESSAGE_USAGE));
        }

        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPreferenceTagCommand.MESSAGE_USAGE));
        }

    }
}
