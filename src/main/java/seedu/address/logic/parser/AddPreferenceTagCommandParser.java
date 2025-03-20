package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPreferenceCommand;
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

        // Ensure arguments are not empty
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPreferenceTagCommand.MESSAGE_USAGE));
        }

        String preamble = argMultimap.getPreamble();

        // Ensure an index is provided
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPreferenceTagCommand.MESSAGE_USAGE));
        }

        try {
            List<Index> multipleIndices = ParserUtil.parseMultipleIndices(argMultimap.getPreamble());
            if (multipleIndices.size() != 2) {
                throw new ParseException("Expected 2 indices");
            }
            personIndex = multipleIndices.get(0);
            preferenceIndex = multipleIndices.get(1);

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPreferenceTagCommand.MESSAGE_USAGE),
                    pe);
        }

        boolean hasTags = !(argMultimap.getAllValues(PREFIX_TAG).isEmpty());
        boolean hasNewTags = !(argMultimap.getAllValues(PREFIX_NEW_TAG).isEmpty());
        boolean hasCombinedTags = hasTags || hasNewTags;

        if (!hasCombinedTags) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPreferenceCommand.MESSAGE_USAGE));
        }

        Set<String> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<String> newTagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_NEW_TAG));

        return new AddPreferenceTagCommand(personIndex, preferenceIndex, tagSet, newTagSet);
    }
}
