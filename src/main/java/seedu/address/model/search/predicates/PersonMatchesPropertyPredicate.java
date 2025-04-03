package seedu.address.model.search.predicates;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s property preferences matches a {@code Listing}.
 * Used for {@code MatchListingCommand}.
 */
public class PersonMatchesPropertyPredicate implements Predicate<Person> {
    private final Listing listingToMatch;

    /**
     * Creates a predicate that tests if a {@code Person}'s property preferences matches a {@code Listing}.
     *
     * @param listingToMatch The listing to match.
     */
    public PersonMatchesPropertyPredicate(Listing listingToMatch) {
        requireNonNull(listingToMatch);

        this.listingToMatch = listingToMatch;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);

        List<PropertyPreference> propertyPreferences = person.getPropertyPreferences();

        if (propertyPreferences.isEmpty()) {
            return false;
        }

        if (listingToMatch.getOwners().contains(person)) {
            return false;
        }

        Set<Tag> tagsToMatch = listingToMatch.getTags();
        for (PropertyPreference pref : propertyPreferences) {
            if (!Collections.disjoint(pref.getTags(), tagsToMatch)) {
                return true;
            }

            if (listingToMatch.getPriceRange().doPriceRangeOverlap(pref.getPriceRange())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PersonMatchesPropertyPredicate
                && listingToMatch.equals(((PersonMatchesPropertyPredicate) other).listingToMatch));
    }
}
