package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.price.PriceRange;

/**
 * Represents a Person's property preference in the real estate system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PropertyPreference {

    // Data fields
    private final PriceRange priceRange;

    /**
     * Constructs a {@code PropertyPreference}.
     * Every field must be present and not null.
     *
     * @param priceRange A valid price range.
     */
    public PropertyPreference(PriceRange priceRange) {
        requireAllNonNull(priceRange);
        this.priceRange = priceRange;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PropertyPreference)) {
            return false;
        }

        PropertyPreference otherPropertyPreference = (PropertyPreference) other;
        return this.priceRange.equals(otherPropertyPreference.priceRange);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(priceRange);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("price range", priceRange)
                .toString();
    }
}
