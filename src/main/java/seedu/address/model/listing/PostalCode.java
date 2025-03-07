package seedu.address.model.listing;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Listing's postal code in the real estate system.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {
    public static final String MESSAGE_CONSTRAINTS =
            "Postal Code must be exactly 6 digits, where each digit must be between 0 and 9";

    /*
     * Exactly 6 digits for valid postal code, where each digit must be between 0 and 9.
     */
    public static final String VALIDATION_REGEX = "^\\d{6}$";

    public final String postalCode;

    /**
     * Constructs an {@code PostalCode}.
     *
     * @param postalCode A valid postal code.
     */
    public PostalCode(String postalCode) {
        requireNonNull(postalCode);
        checkArgument(isValidPostalCode(postalCode), MESSAGE_CONSTRAINTS);
        this.postalCode = postalCode;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return postalCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PostalCode)) {
            return false;
        }

        PostalCode otherPostalCode = (PostalCode) other;
        return postalCode.equals(otherPostalCode.postalCode);
    }

    @Override
    public int hashCode() {
        return postalCode.hashCode();
    }

}