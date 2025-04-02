package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.search.SearchType;
import seedu.address.model.search.comparators.PersonListingScoreComparator;
import seedu.address.model.search.predicates.PersonMatchesPropertyPredicate;
import seedu.address.model.search.predicates.PropertyPreferencesMatchesListingPredicate;

/**
 * Lists all {@code Person}(s) that have {@code PropertyPreference}(s) that match the specified {@code Listing} in the
 * address book.
 * The {@code Listing} is identified using it's displayed index.
 * Matching excludes {@code Person}(s) that owns the specified {@code Listing}.
 * A match is determined by whether a {@code PropertyPreference} has the same tags or an overlapping price range as the
 * specified {@code Listing}.
 * The {@code Person}(s) are sorted in descending order based on the {@code PropertyPreference} with
 * the highest number of matching tags and price range.
 */
public class MatchListingCommand extends Command {

    public static final String COMMAND_WORD = "matchListing";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Matches the listing identified by the index number used in the displayed listing list"
            + " to a Persons' property preferences.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MATCH_LISTING_SUCCESS = "Matched Listing: %1$s";

    private final Index targetListingIndex;

    /**
     * Creates a {@code MatchListingCommand} to match {@code Person}s' @code PropertyPreference} to the
     * specified {@code Listing}.
     *
     * @param targetListingIndex The index of the listing in the filtered listing list to match to.
     */
    public MatchListingCommand(Index targetListingIndex) {
        requireNonNull(targetListingIndex);

        this.targetListingIndex = targetListingIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Listing> lastShownList = model.getSortedFilteredListingList();
        if (targetListingIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX, MESSAGE_USAGE));
        }

        Listing toMatch = lastShownList.get(targetListingIndex.getZeroBased());
        matchListing(model, toMatch);

        return new CommandResult(String.format(MESSAGE_MATCH_LISTING_SUCCESS, Messages.format(toMatch)));
    }

    private void matchListing(Model model, Listing listingToMatch) {
        requireAllNonNull(model, listingToMatch);

        model.resetAllLists();

        model.setSearch(listingToMatch.getTags(),
                listingToMatch.getPriceRange(),
                SearchType.PERSON,
                new PropertyPreferencesMatchesListingPredicate(listingToMatch));

        model.updateFilteredPersonList(new PersonMatchesPropertyPredicate(listingToMatch));
        model.updateSortedFilteredPersonList(new PersonListingScoreComparator(listingToMatch));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchListingCommand)) {
            return false;
        }

        MatchListingCommand otherDeleteCommand = (MatchListingCommand) other;
        return targetListingIndex.equals(otherDeleteCommand.targetListingIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetListingIndex", targetListingIndex)
                .toString();
    }
}
