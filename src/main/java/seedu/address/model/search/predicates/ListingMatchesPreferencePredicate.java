package seedu.address.model.search.predicates;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;


/**
 * Tests if a {@code Listing} matches a {@code PropertyPreference}.
 * Used for {@code MatchPreferenceCommand}.
 */
public class ListingMatchesPreferencePredicate implements Predicate<Listing> {
    private final PropertyPreference preferenceToMatch;

    /**
     * Constructs a {@code ListingMatchesPreferencePredicate} with the given {@code PropertyPreference}.
     *
     * @param preferenceToMatch The {@code PropertyPreference} to match.
     */
    public ListingMatchesPreferencePredicate(PropertyPreference preferenceToMatch) {
        requireNonNull(preferenceToMatch);

        this.preferenceToMatch = preferenceToMatch;
    }

    @Override
    public boolean test(Listing listing) {
        requireNonNull(listing);

        Set<Tag> tagsToMatch = preferenceToMatch.getTags();

        if (!listing.getAvailability() || listing.getOwners().contains(preferenceToMatch.getPerson())) {
            return false;
        }

        boolean priceRangeOverlaps = preferenceToMatch.getPriceRange().doPriceRangeOverlap(listing.getPriceRange());
        if (priceRangeOverlaps) {
            return true;
        }

        Set<Tag> listingTags = listing.getTags();
        return !Collections.disjoint(listingTags, tagsToMatch);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListingMatchesPreferencePredicate
                && preferenceToMatch.equals(((ListingMatchesPreferencePredicate) other).preferenceToMatch));
    }
}
