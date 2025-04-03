package seedu.address.model.search.comparators;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;

/**
 * Compares two persons based on their how well they match a given listing.
 */
public class PersonListingScoreComparator implements Comparator<Person> {
    private static final int INITIAL_SCORE = 0;
    private static final int PRICE_MATCH_SCORE = 1;
    private static final int TAG_MATCH_SCORE = 1;

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
    public int compare(Person person1, Person person2) {
        requireAllNonNull(person1, person2);

        int person1Score = getBestMatchScore(person1);
        int person2Score = getBestMatchScore(person2);

        return Integer.compare(person2Score, person1Score);
    }

    private int getBestMatchScore(Person person) {
        int bestScore = INITIAL_SCORE;

        for (PropertyPreference preference : person.getPropertyPreferences()) {
            int currentScore = getPreferenceScore(preference);
            if (currentScore > bestScore) {
                bestScore = currentScore;
            }
        }

        return bestScore;
    }

    private int getPreferenceScore(PropertyPreference preference) {
        int score = INITIAL_SCORE;

        if (listingToScore.getPriceRange().doPriceRangeOverlap(preference.getPriceRange())) {
            score += PRICE_MATCH_SCORE;
        }

        for (Tag tag : preference.getTags()) {
            if (listingToScore.getTags().contains(tag)) {
                score += TAG_MATCH_SCORE;
            }
        }

        return score;
    }
}
