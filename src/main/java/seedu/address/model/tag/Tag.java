package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; tag is valid as declared in {@link #isValidTagName(String)}; all details are present and not
 * null.
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags must be between 2 and 50 characters long and can only "
            + "contain letters, numbers, apostrophes, spaces, periods, hyphens, underscores, plus signs,"
            + " and ampersands. The tag cannot be blank and must not already exist.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9' ._+&-]{2,50}$";

    // Identity fields
    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireAllNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);

        this.tagName = tagName.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getTagName() {
        return tagName;
    }

    /**
     * Checks if two tags have the same unique identifiers.
     * This defines a weaker notion of equality between two tags.
     *
     * @param otherTag tag to be compared with.
     * @return true if both tags have the same tag name. false otherwise.
     */
    public boolean isSameTag(Tag otherTag) {
        if (otherTag == this) {
            return true;
        }

        return otherTag != null
                && tagName.equals(otherTag.tagName);
    }

    /**
     * Checks if two tag have the same identity and data fields and associations.
     * This defines a stronger notion of equality between two tags.
     *
     * @param other Object to be compared with.
     * @return true if both tags have the same identity and data fields and associations. false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tagName);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return new ToStringBuilder(this)
                .add("tag name", tagName)
                .toString();
    }

}
