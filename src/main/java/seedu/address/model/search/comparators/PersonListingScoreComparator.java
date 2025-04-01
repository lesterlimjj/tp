package seedu.address.model.search.comparators;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashMap;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;

/**
 * Compares two persons based on their how well they match a given listing.
 */
public class PersonListingScoreComparator implements Comparator<Person> {
    private static Listing listingToScore;

    /**
     * Constructs a {@code PersonListingScoreComparator} with the given listing to score.
     *
     * @param listingToScore The listing to score.
     */
    public PersonListingScoreComparator(Listing listingToScore) {
        requireNonNull(listingToScore);

        PersonListingScoreComparator.listingToScore = listingToScore;
    }

    @Override
    public int compare(Person o1, Person o2) {
        requireAllNonNull(o1, o2);

        HashMap<Person, Integer> personScores = new HashMap<>();

        personScores.put(o1, 0);
        personScores.put(o2, 0);

        for (Person person : personScores.keySet()) {
            int score = 0;

            for (PropertyPreference preference : person.getPropertyPreferences()) {
                int preferenceScore = 0;

                if (listingToScore.getPriceRange().doPriceRangeOverlap(preference.getPriceRange())) {
                    preferenceScore += 1;
                }

                for (Tag tag : preference.getTags()) {
                    if (listingToScore.getTags().contains(tag)) {
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

            personScores.put(person, score);
        }

        return Integer.compare(personScores.get(o2), personScores.get(o1));
    }
}
