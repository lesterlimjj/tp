package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.search.SearchType;
import seedu.address.model.search.comparators.ListingPreferenceScoreComparator;
import seedu.address.model.search.predicates.ListingMatchesPreferencePredicate;

/**
 * Lists all {@code Listing}(s) that match the specified {@code PropertyPreference} of a {@code Person} in the address
 * book.
 * The {@code PropertyPreference} is identified using it's displayed index within the {@code Person}'s preferences,
 * and the {@code Person} is identified using it's displayed index.
 * Matching excludes {@code Listings}(s) that are owned by the specified {@code Person} or are unavailable.
 * A match is determined by whether a {@code Listing} has the same tags or an overlapping price range as the
 * specified {@code PropertyPreference}.
 * The {@code Listing}(s) are sorted in descending order based on the number of matching tags and price range.
 */
public class MatchPreferenceCommand extends Command {

    public static final String COMMAND_WORD = "matchPreference";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds listings matching a person's preference."
            + "\nParameters: "
            + "PERSON_INDEX (must be a positive integer) "
            + "PREFERENCE_INDEX (must be a positive integer)"
            + "\nExample: "
            + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_MATCH_PERSON_SUCCESS = "Matched Listings for %1$s's Preference - "
            + "Price Range: %2$s, Tags: %3$s";

    private final Index targetPersonIndex;
    private final Index targetPreferenceIndex;

    /**
     * Creates a {@code MatchPreferenceCommand} to match {@code Listing}s to the specified {@code PropertyPreference}.
     *
     * @param targetPersonIndex The index of the person in the filtered person list that the preference is located in.
     * @param targetPreferenceIndex The index of the preference to match to.
     */
    public MatchPreferenceCommand(Index targetPersonIndex, Index targetPreferenceIndex) {
        requireAllNonNull(targetPersonIndex, targetPreferenceIndex);

        this.targetPersonIndex = targetPersonIndex;
        this.targetPreferenceIndex = targetPreferenceIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.getSearchContext().setActivePriceRange(null);
        model.getSearchContext().setActiveSearchTags(new HashSet<>());
        Person targetPerson = CommandUtil.getValidatedPerson(model, targetPersonIndex, MESSAGE_USAGE);

        List<PropertyPreference> targetPreferenceList = targetPerson.getPropertyPreferences()
                .stream()
                .filter(preference -> model.getSearchContext().matches(preference))
                .toList();

        if (targetPreferenceIndex.getZeroBased() >= targetPreferenceList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PREFERENCE_DISPLAYED_INDEX,
                    MESSAGE_USAGE));
        }

        PropertyPreference preferenceToMatch = targetPreferenceList.get(targetPreferenceIndex.getZeroBased());
        matchPreference(model, preferenceToMatch);

        String successMessage = String.format(MESSAGE_MATCH_PERSON_SUCCESS,
                targetPerson.getName(),
                preferenceToMatch.getPriceRange(),
                Messages.format(preferenceToMatch.getTags()));

        return new CommandResult(successMessage);
    }

    private void matchPreference(Model model, PropertyPreference preferenceToMatch) {
        requireAllNonNull(model, preferenceToMatch);

        model.resetAllLists();

        model.setSearch(preferenceToMatch.getTags(),
                preferenceToMatch.getPriceRange(),
                SearchType.LISTING,
                Model.PREDICATE_SHOW_ALL_PROPERTY_PREFERENCES);

        model.updateFilteredListingList(new ListingMatchesPreferencePredicate(preferenceToMatch));
        model.updateSortedFilteredListingList(new ListingPreferenceScoreComparator(preferenceToMatch));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchPreferenceCommand)) {
            return false;
        }

        MatchPreferenceCommand otherDeleteCommand = (MatchPreferenceCommand) other;
        return targetPersonIndex.equals(otherDeleteCommand.targetPersonIndex)
                && targetPreferenceIndex.equals(otherDeleteCommand.targetPreferenceIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonIndex", targetPersonIndex)
                .add("targetPreferenceIndex", targetPreferenceIndex)
                .toString();
    }
}
