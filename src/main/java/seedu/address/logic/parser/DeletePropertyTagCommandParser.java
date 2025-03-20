package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_ARGUMENTS_EMPTY;
import static seedu.address.logic.Messages.MESSAGE_INDEX_REQUIRED;
import static seedu.address.logic.Messages.MESSAGE_PROPERTY_TAG_REQUIRED_FOR_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePropertyTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeletePropertyTagCommandParser} object.
 */
public class DeletePropertyTagCommandParser implements Parser<DeletePropertyTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePropertyTagCommand
     * and returns a DeletePropertyTagCommand object for execution.
     *
     * @param args arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePropertyTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        // Ensure arguments are not empty
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_ARGUMENTS_EMPTY,
                    DeletePropertyTagCommand.MESSAGE_USAGE));
        }

        String preamble = argMultimap.getPreamble();

        // Ensure an index is provided
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INDEX_REQUIRED,
                    DeletePropertyTagCommand.MESSAGE_USAGE));
        }

        try {
            // Extract index from the preamble
            Index index = ParserUtil.parseIndex(preamble);

            // Extract tags
            Set<String> tags = argMultimap.getAllValues(PREFIX_TAG)
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.toSet());

            // Validate at least one tag is provided
            if (tags.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_PROPERTY_TAG_REQUIRED_FOR_DELETE,
                        DeletePropertyTagCommand.MESSAGE_USAGE));
            }

            return new DeletePropertyTagCommand(index, tags);

        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }
}
