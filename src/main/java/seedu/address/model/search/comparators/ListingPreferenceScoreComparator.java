package seedu.address.model.search.comparators;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashMap;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;

/**
 * Compares two listings based on how well they match a given preference.
 */
public class ListingPreferenceScoreComparator implements Comparator<Listing> {
    private static PropertyPreference preferenceToScore;

    /**
     * Creates a comparator that compares two listings based on how well they match the given preference.
     *
     * @param preferenceToScore The preference to score the listings against.
     */
    public ListingPreferenceScoreComparator(PropertyPreference preferenceToScore) {
        requireNonNull(preferenceToScore);

        this.preferenceToScore = preferenceToScore;
    }

    @Override
    public int compare(Listing o1, Listing o2) {
        requireAllNonNull(o1, o2);

        HashMap<Listing, Integer> listingScores = new HashMap<>();

        listingScores.put(o1, 0);
        listingScores.put(o2, 0);
        for (Listing listing : listingScores.keySet()) {
            int score = 0;

            if (preferenceToScore.getPriceRange().doPriceRangeOverlap(listing.getPriceRange())) {
                score += 1;
            }

            for (Tag tag : preferenceToScore.getTags()) {
                if (listing.getTags().contains(tag)) {
                    score += 1;
                }
            }

            if (score == 0) {
                continue;
            }

            listingScores.put(listing, score);
        }

        return Integer.compare(listingScores.get(o2), listingScores.get(o1));
    }
}
