package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePreferenceTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a DeletePreferenceTagCommand object.
 */
public class DeletePreferenceTagCommandParser implements Parser<DeletePreferenceTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePreferenceTagCommand
     * and returns an DeletePreferenceTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePreferenceTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Index personIndex;
        Index preferenceIndex;

        // Ensure arguments are not empty
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePreferenceTagCommand.MESSAGE_USAGE));
        }

        String preamble = argMultimap.getPreamble();

        // Ensure an index is provided
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePreferenceTagCommand.MESSAGE_USAGE));
        }

        try {
            List<Index> multipleIndices = ParserUtil.parseMultipleIndices(argMultimap.getPreamble());
            personIndex = multipleIndices.get(0);
            preferenceIndex = multipleIndices.get(1);

            // Extract tags
            Set<String> tags = argMultimap.getAllValues(PREFIX_TAG)
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.toSet());

            // Validate at least one tag is provided
            if (tags.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeletePreferenceTagCommand.MESSAGE_USAGE));
            }

            return new DeletePreferenceTagCommand(personIndex, preferenceIndex, tags);

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePreferenceTagCommand.MESSAGE_USAGE),
                    pe);
        }

    }
}
