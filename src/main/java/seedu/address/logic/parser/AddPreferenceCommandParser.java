package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOWER_BOUND_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UPPER_BOUND_PRICE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.price.Price;
import seedu.address.model.price.PriceRange;

/**
 * Adds a person's preference to the address book.
 */
public class AddPreferenceCommandParser implements Parser<AddPreferenceCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonCommand
     * and returns an AddPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPreferenceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LOWER_BOUND_PRICE, PREFIX_UPPER_BOUND_PRICE, PREFIX_TAG,
                        PREFIX_NEW_TAG);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPreferenceCommand.MESSAGE_USAGE), pe);
        }

        boolean hasTags = !(argMultimap.getAllValues(PREFIX_TAG).isEmpty());
        boolean hasNewTags = !(argMultimap.getAllValues(PREFIX_NEW_TAG).isEmpty());
        boolean hasCombinedTags = hasTags || hasNewTags;

        if (!hasCombinedTags) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPreferenceCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LOWER_BOUND_PRICE, PREFIX_UPPER_BOUND_PRICE);
        Price lowerBoundPrice = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_LOWER_BOUND_PRICE).orElse(null));
        Price upperBoundPrice = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_UPPER_BOUND_PRICE).orElse(null));
        PriceRange priceRange = createPriceRange(lowerBoundPrice, upperBoundPrice);
        Set<String> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<String> newTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_NEW_TAG));

        return new AddPreferenceCommand(index, priceRange, tagList, newTagList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static PriceRange createPriceRange(Price lowerBoundPrice, Price upperBoundPrice) {
        if (lowerBoundPrice == null && upperBoundPrice == null) {
            return new PriceRange();
        } else if (lowerBoundPrice == null) {
            return new PriceRange(upperBoundPrice, true);
        } else if (upperBoundPrice == null) {
            return new PriceRange(lowerBoundPrice, false);
        } else {
            return new PriceRange(lowerBoundPrice, upperBoundPrice);
        }
    }
}
