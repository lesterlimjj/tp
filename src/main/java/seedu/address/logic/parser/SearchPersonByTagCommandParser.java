package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SearchPersonByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new SearchPersonByTagCommand object.
 */
public class SearchPersonByTagCommandParser implements Parser<SearchPersonByTagCommand> {

    @Override
    public SearchPersonByTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        // If no prefix found or other random input (like empty string)
        if (argMultimap.getPreamble().isEmpty() && argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_ARGUMENTS_EMPTY,
                    SearchPersonByTagCommand.MESSAGE_USAGE));
        }

        Set<String> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (tags.isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_SEARCH_PERSON_TAG_MISSING_PARAMS,
                    SearchPersonByTagCommand.MESSAGE_USAGE));
        }

        return new SearchPersonByTagCommand(tags);
    }

}
