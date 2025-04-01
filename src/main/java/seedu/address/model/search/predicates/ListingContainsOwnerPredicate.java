package seedu.address.model.search.predicates;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;


/**
 * Tests if a {@code Listing} contains the specified owner.
 */
public class ListingContainsOwnerPredicate implements Predicate<Listing> {
    private final Person personToMatch;

    /**
     * Creates a predicate that tests if a {@code Listing} contains the specified owner.
     *
     * @param personToMatch The owner to match.
     */
    public ListingContainsOwnerPredicate(Person personToMatch) {
        requireNonNull(personToMatch);

        this.personToMatch = personToMatch;
    }

    @Override
    public boolean test(Listing listing) {
        requireNonNull(listing);

        return listing.getOwners().contains(personToMatch);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListingContainsOwnerPredicate
                && personToMatch.equals(((ListingContainsOwnerPredicate) other).personToMatch));
    }
}
