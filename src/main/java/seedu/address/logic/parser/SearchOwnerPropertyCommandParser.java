package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SearchOwnerPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SearchOwnerPropertyCommandParser} object.
 */
public class SearchOwnerPropertyCommandParser implements Parser<SearchOwnerPropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchOwnerPropertyCommand
     * and returns a SearchOwnerPropertyCommand object for execution.
     *
     * @param args arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchOwnerPropertyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SearchOwnerPropertyCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            SearchOwnerPropertyCommand.MESSAGE_USAGE), pe);
        }
    }

}
