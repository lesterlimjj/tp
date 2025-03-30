package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INDEX_REQUIRED;
import static seedu.address.logic.Messages.MESSAGE_PROPERTY_TAG_REQUIRED_FOR_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteListingTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeletePropertyTagCommandParser} object.
 */
public class DeletePropertyTagCommandParser implements Parser<DeleteListingTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteListingTagCommand
     * and returns a DeleteListingTagCommand object for execution.
     *
     * @param args arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteListingTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        // Ensure arguments are not empty
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(
                    seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteListingTagCommand.MESSAGE_USAGE));
        }

        String preamble = argMultimap.getPreamble();

        // Ensure an index is provided
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INDEX_REQUIRED,
                    DeleteListingTagCommand.MESSAGE_USAGE));
        }

        try {
            // Extract index from the preamble
            Index index = ParserUtil.parseIndex(preamble);

            // Extract tags
            Set<String> tags = argMultimap.getAllValues(PREFIX_TAG)
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.toSet());

            // Validate at least one tag is provided and no tag is blank
            if (tags.isEmpty() || tags.stream().anyMatch(String::isBlank)) {
                throw new ParseException(String.format(MESSAGE_PROPERTY_TAG_REQUIRED_FOR_DELETE,
                        DeleteListingTagCommand.MESSAGE_USAGE));
            }

            return new DeleteListingTagCommand(index, tags);

        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }
}
