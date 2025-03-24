package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SearchSellerPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SearchSellerPropertyCommandParser} object.
 */
public class SearchSellerPropertyCommandParser implements Parser<SearchSellerPropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchSellerPropertyCommand
     * and returns a SearchSellerPropertyCommand object for execution.
     *
     * @param args arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchSellerPropertyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SearchSellerPropertyCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            SearchSellerPropertyCommand.MESSAGE_USAGE), pe);
        }
    }

}
