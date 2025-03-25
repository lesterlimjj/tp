package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagRegistry;

/**
 * Matches a {@code Listing} identified using it's displayed index in the address book to
 * {@code Person}s' @code PropertyPreference}.
 */
public class MatchListingCommand extends Command {

    public static final String COMMAND_WORD = "matchProperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Matches the listing identified by the index number used in the displayed listing list"
            + " to a Persons' property preferences.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MATCH_LISTING_SUCCESS = "Matched Listing: %1$s";

    private final Index targetIndex;

    /**
     * Creates a {@code MatchListingCommand} to match the specified {@code Listing}
     * to {@code Person}s' @code PropertyPreference}.
     *
     * @param targetIndex of the listing in the filtered listing list to delete
     */
    public MatchListingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Listing> lastShownList = model.getSortedFilteredListingList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }

        Listing toMatch = lastShownList.get(targetIndex.getZeroBased());

        matchListing(model, toMatch);

        return new CommandResult(String.format(MESSAGE_MATCH_LISTING_SUCCESS, Messages.format(toMatch)));
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
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

    private void matchListing(Model model, Listing listingToMatch) {
        HashMap<Person, Integer> personScores = new HashMap<>();

        model.updateFilteredPersonList(model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateSortedFilteredPersonList(model.COMPARATOR_SHOW_ALL_PERSONS);

        //The predicate is reset so it's ok to use model.getFilteredPersonList() instead of
        //model.getSortedFilteredPersonList()
        for (Person person : model.getFilteredPersonList()) {
            int score = 0;

            for (PropertyPreference preference : person.getPropertyPreferences()) {
                int preferenceScore = 0;

                if (listingToMatch.getPriceRange().doPriceRangeOverlap(preference.getPriceRange())) {
                    preferenceScore += 1;
                }

                for (Tag tag : preference.getTags()) {
                    if (listingToMatch.getTags().contains(tag)) {
                        preferenceScore += 1;
                    }
                }

                if (preferenceScore == 0) {
                    continue;
                }

                if (preferenceScore > score) {
                    score = preferenceScore;
                }
            }

            if (score == 0 || listingToMatch.getOwners().contains(person)) {
                continue;
            }

            personScores.put(person, score);
        }

        Predicate<Person> predicate = personScores::containsKey;

        Comparator<Person> comparator = (person1, person2) -> {
            int score1 = personScores.getOrDefault(person1, 0);
            int score2 = personScores.getOrDefault(person2, 0);
            return Integer.compare(score2, score1);
        };

        Tag.setActiveSearchTags(listingToMatch.getTags().stream().toList());
        model.updateFilteredPersonList(predicate);
        model.updateSortedFilteredPersonList(comparator);
    }


    private void removeListingFromTags(Listing toDelete) {

        TagRegistry tagRegistry = TagRegistry.of();
        Set<Tag> tags = new HashSet<>(toDelete.getTags());

        for (Tag tag: tags) {
            tag.removeListing(toDelete);
            tagRegistry.setTag(tag, tag);
        }
    }
}
