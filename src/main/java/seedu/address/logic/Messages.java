package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_LISTING_DISPLAYED_INDEX = "The listing index provided is invalid";
    public static final String MESSAGE_INVALID_OWNER_DISPLAYED_INDEX = "The owner index provided is invalid";
    public static final String MESSAGE_DELETE_PROPERTY_TAG_SUCCESS = "Tag(s) in property %s deleted: %s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag(s) not found in property: %s";
    public static final String MESSAGE_INVALID_PREFERENCE_DISPLAYED_INDEX = "The property preference index provided "
            + "is invalid";

    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_KEYWORD =
                "ERROR: Invalid keyword '%s'. Keywords can only contain letters, spaces, hyphens, or apostrophes.";
    public static final String MESSAGE_MISSING_KEYWORD =
                "ERROR: Missing parameters. You must provide at least one keyword.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%d persons found matching the keywords.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail());
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Listing listing) {
        final StringBuilder builder = new StringBuilder();
        if (listing.getUnitNumber() == null) {
            builder.append("; Postal Code: ")
                    .append(listing.getPostalCode())
                    .append("; House Number: ")
                    .append(listing.getHouseNumber())
                    .append("; Price Range: ")
                    .append(listing.getPriceRange())
                    .append("; Property Name: ")
                    .append(listing.getPropertyName());
        } else {
            builder.append("; Postal Code: ")
                    .append(listing.getPostalCode())
                    .append("; Unit Number: ")
                    .append(listing.getUnitNumber())
                    .append("; Price Range: ")
                    .append(listing.getPriceRange())
                    .append("; Property Name: ")
                    .append(listing.getPropertyName());
        }
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person, PropertyPreference preference) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName() + ":")
                .append(" Price Range: ")
                .append(preference.getPriceRange())
                .append("; Tags: ");
        preference.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Set<Tag> tags) {
        final StringBuilder builder = new StringBuilder();
        builder.append("; Tags: ");
        tags.forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the assign of listing to Person for display to the user.
     */
    public static String format(Person person, Listing listing) {
        final StringBuilder builder = new StringBuilder();
        if (listing.getUnitNumber() == null) {
            builder.append("<")
                    .append(listing.getPostalCode())
                    .append("> <")
                    .append(listing.getHouseNumber())
                    .append(">")
                    .append(" added to ")
                    .append(person.getName());
        } else {
            builder.append("<")
                    .append(listing.getPostalCode())
                    .append("> <")
                    .append(listing.getUnitNumber())
                    .append(">")
                    .append(" added to ")
                    .append(person.getName());
        }

        return builder.toString();
    }

    /**
     * Formats the assign of listing to Person for display to the user.
     */
    public static String format(Set<Tag> tags, Listing listingWithTags) {
        final StringBuilder builder = new StringBuilder();
        if (listingWithTags.getUnitNumber() == null) {
            builder.append("<")
                    .append(listingWithTags.getPostalCode())
                    .append("> <")
                    .append(listingWithTags.getHouseNumber())
                    .append(">");
            builder.append(": Tags: ");
            tags.forEach(builder::append);
        } else {
            builder.append("<")
                    .append(listingWithTags.getPostalCode())
                    .append("> <")
                    .append(listingWithTags.getUnitNumber())
                    .append(">");
            builder.append(": Tags: ");
            tags.forEach(builder::append);
        }

        return builder.toString();
    }

}
