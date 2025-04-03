package seedu.address.model.search.comparators;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;

/**
 * Compares two listings based on how well they match a given preference.
 */
public class ListingPreferenceScoreComparator implements Comparator<Listing> {
    private static final int INITIAL_SCORE = 0;
    private static final int PRICE_MATCH_SCORE = 1;
    private static final int TAG_MATCH_SCORE = 1;

    private static PropertyPreference preferenceToScore;

    /**
     * Creates a comparator that compares two listings based on how well they match the given preference.
     *
     * @param preferenceToScore The preference to score the listings against.
     */
    public ListingPreferenceScoreComparator(PropertyPreference preferenceToScore) {
        requireNonNull(preferenceToScore);

        ListingPreferenceScoreComparator.preferenceToScore = preferenceToScore;
    }

    @Override
    public int compare(Listing listing1, Listing listing2) {
        requireAllNonNull(listing1, listing2);

        int listing1Score = getMatchScore(listing1);
        int listing2Score = getMatchScore(listing2);

        return Integer.compare(listing2Score, listing1Score);
    }

    private int getMatchScore(Listing listing) {
        int score = INITIAL_SCORE;

        if (preferenceToScore.getPriceRange().doPriceRangeOverlap(listing.getPriceRange())) {
            score += PRICE_MATCH_SCORE;
        }

        for (Tag tag : preferenceToScore.getTags()) {
            if (listing.getTags().contains(tag)) {
                score += TAG_MATCH_SCORE;
            }
        }

        return score;
    }
}
