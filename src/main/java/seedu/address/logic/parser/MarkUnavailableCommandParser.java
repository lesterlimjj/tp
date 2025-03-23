package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteListingCommand;
import seedu.address.logic.commands.MarkUnavailableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code MarkUnavailableCommandParser} object.
 */
public class MarkUnavailableCommandParser implements Parser<MarkUnavailableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkUnavailableCommand
     * and returns a MarkUnavailableCommand object for execution.
     *
     * @param args arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkUnavailableCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkUnavailableCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_LISTING_DISPLAYED_INDEX, DeleteListingCommand.MESSAGE_USAGE), pe);
        }
    }
}
