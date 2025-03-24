package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SearchPropertyByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SearchPropertyByTagCommand} object.
 */
public class SearchPropertyByTagCommandParser implements Parser<SearchPropertyByTagCommand> {

    @Override
    public SearchPropertyByTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        // Check if tag prefix present but empty tag
        if (argMultimap.getAllValues(PREFIX_TAG).stream().anyMatch(String::isBlank)) {
            throw new ParseException(Messages.MESSAGE_SEARCH_PROPERTY_TAG_PREFIX_EMPTY);
        }

        // Check if no input or no prefixes provided
        if (args.trim().isEmpty() || argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, SearchPropertyByTagCommand.MESSAGE_USAGE));
        }

        Set<String> tags = argMultimap.getAllValues(PREFIX_TAG).stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        if (tags.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_SEARCH_PROPERTY_TAG_MISSING_PARAMS);
        }

        return new SearchPropertyByTagCommand(tags);
    }
}
