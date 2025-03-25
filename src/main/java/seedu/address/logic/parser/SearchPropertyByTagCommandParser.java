package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SearchPropertyByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code SearchPropertyByTagCommand} object.
 */
public class SearchPropertyByTagCommandParser implements Parser<SearchPropertyByTagCommand> {

    @Override
    public SearchPropertyByTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        // If no prefix or random input found
        if (argMultimap.getPreamble().isEmpty() && argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(SearchPropertyByTagCommand.MESSAGE_USAGE);
        }

        Set<String> tags = argMultimap.getAllValues(PREFIX_TAG).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        if (tags.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_SEARCH_PROPERTY_TAG_MISSING_PARAMS);
        }
        if (tags.stream().anyMatch(String::isBlank)) {
            throw new ParseException(Messages.MESSAGE_SEARCH_PROPERTY_TAG_PREFIX_EMPTY);
        }

        // Validate tag formats
        for (String tagName : tags) {
            if (!Tag.isValidTagName(tagName)) {
                throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
            }
        }

        return new SearchPropertyByTagCommand(tags);
    }
}
