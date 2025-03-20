package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_ADD_TAG_PREAMBLE_FOUND;
import static seedu.address.logic.Messages.MESSAGE_DELETE_TAG_PREAMBLE_FOUND;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_TAG_OR_NEW_TAG_PREFIX_EMPTY_VALUE;
import static seedu.address.logic.Messages.MESSAGE_TAG_REQUIRED_FOR_DELETE;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns a DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        checkCommandFormat(argMultimap);

        Set<String> deleteTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new DeleteTagCommand(deleteTagList);
    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasTags = !(argMultimap.getAllValues(PREFIX_TAG).isEmpty());

        if (!hasTags) {
            throw new ParseException(String.format(MESSAGE_TAG_REQUIRED_FOR_DELETE,
                    DeleteTagCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
           throw new ParseException(String.format(MESSAGE_DELETE_TAG_PREAMBLE_FOUND,
                   DeleteTagCommand.MESSAGE_USAGE));
        }
    }

}
