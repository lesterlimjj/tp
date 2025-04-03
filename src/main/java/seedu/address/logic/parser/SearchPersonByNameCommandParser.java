package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_MISSING_KEYWORD;
import static seedu.address.logic.commands.SearchPersonByNameCommand.VALID_NAME_PATTERN;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SearchPersonByNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SearchPersonByNameCommand} object.
 */
public class SearchPersonByNameCommandParser implements Parser<SearchPersonByNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchPersonByNameCommand
     * and returns a SearchPersonByNameCommand object for execution.
     *
     * @param args The arguments to be parsed.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public SearchPersonByNameCommand parse(String args) throws ParseException {
        checkCommandFormat(args);
        List<String> keywords = Arrays.stream(args.trim().split("\\s+"))
                .collect(Collectors.toList());

        return new SearchPersonByNameCommand(keywords);
    }

    private static void checkCommandFormat(String args) throws ParseException {
        List<String> keywords = Arrays.stream(args.trim().split("\\s+"))
                .collect(Collectors.toList());

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_KEYWORD,
                    SearchPersonByNameCommand.MESSAGE_USAGE));
        }

        if (keywords.isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_MISSING_KEYWORD,
                    SearchPersonByNameCommand.MESSAGE_USAGE));
        }

        for (String keyword : keywords) {
            if (!VALID_NAME_PATTERN.matcher(keyword).matches()) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_KEYWORD, keyword));
            }
        }


    }
}
