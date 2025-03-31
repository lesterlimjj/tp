package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SearchPersonByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchPersonByTagCommand object.
 */
public class SearchPersonByTagCommandParser implements Parser<SearchPersonByTagCommand> {

    @Override
    public SearchPersonByTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        argMultimap.verifyNoDuplicateTagValues(SearchPersonByTagCommand.MESSAGE_USAGE);
        checkCommandFormat(argMultimap);

        Set<String> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new SearchPersonByTagCommand(tags);
    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getPreamble().isEmpty() && argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_ARGUMENTS_EMPTY,
                    SearchPersonByTagCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)).isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_SEARCH_PERSON_TAG_MISSING_PARAMS,
                    SearchPersonByTagCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_SEARCH_PERSON_TAG_PREAMBLE_FOUND,
                    SearchPersonByTagCommand.MESSAGE_USAGE));
        }
    }

}
