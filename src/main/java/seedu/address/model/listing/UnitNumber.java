package seedu.address.model.listing;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Listing's unit number in the real estate system.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnitNumber(String)}
 */
public class UnitNumber {
    public static final String MESSAGE_CONSTRAINTS =
            "Unit numbers must follow the given format and constraints: "
                    + "<optional B/R prefix><floor_number>-<apartment_number><optional_subunit>, where:\n"
                    + "1. The optional prefix must be either 'B' (Basement) or 'R' (Roof), or it can be omitted.\n"
                    + "2. The floor number must be a 2-digit number and cannot start with 0 for 1st digit.\n"
                    + "3. The apartment number must be between 2 to 5 digits and cannot start with 0 for 1st digit.\n"
                    + "4. The optional subunit must be a single capital letter, excluding 'I' and 'O'.\n"
                    + "5. A house number cannot be specified with a unit number.";

    /** Validation regex for unit numbers */
    public static final String VALIDATION_REGEX = "^(B|R)?[1-9][0-9]-[1-9][0-9]{1,4}([A-HJ-NP-Z])?$";

    public final String unitNumber;

    /**
     * Constructs an {@code UnitNumber}.
     *
     * @param unitNumber A valid unit number.
     */
    public UnitNumber(String unitNumber) {
        requireNonNull(unitNumber);
        checkArgument(isValidUnitNumber(unitNumber), MESSAGE_CONSTRAINTS);
        this.unitNumber = unitNumber;
    }

    /**
     * Returns true if a given string is a valid unit number.
     */
    public static boolean isValidUnitNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return unitNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnitNumber)) {
            return false;
        }

        UnitNumber otherUnitNumber = (UnitNumber) other;
        return unitNumber.equals(otherUnitNumber.unitNumber);
    }

    @Override
    public int hashCode() {
        return unitNumber.hashCode();
    }

}