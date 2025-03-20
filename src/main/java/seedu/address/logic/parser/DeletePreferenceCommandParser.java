package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EXPECTED_TWO_INDICES;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_OR_PREFERENCE_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeletePreferenceCommandParser} object.
 */
public class DeletePreferenceCommandParser implements Parser<DeletePreferenceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePreferenceCommand
     * and returns a DeletePreferenceCommand object for execution.
     *
     * @param args arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePreferenceCommand parse(String args) throws ParseException {
        List<Index> multipleIndices;
        try {
            multipleIndices = ParserUtil.parseMultipleIndices(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_OR_PREFERENCE_DISPLAYED_INDEX,
                    DeletePreferenceCommand.MESSAGE_USAGE),
                    pe);
        }

        try {
            if (multipleIndices.size() != 2) {
                throw new ParseException("Expected 2 indices");
            }
            return new DeletePreferenceCommand(multipleIndices.get(0), multipleIndices.get(1));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_EXPECTED_TWO_INDICES, DeletePreferenceCommand.MESSAGE_USAGE),
                    pe);
        }
    }

}
