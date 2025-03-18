package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.HouseNumber;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.PostalCode;
import seedu.address.model.listing.PropertyName;
import seedu.address.model.listing.UnitNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.price.PriceRange;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagRegistry;

public class AddListingTagCommand extends Command {
    public static final String COMMAND_WORD = "addListingTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tags to listing "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_NEW_TAG + "NEW_TAG]...\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_TAG + "quiet "
            + PREFIX_TAG + "pet-friendly "
            + PREFIX_NEW_TAG + "family-friendly "
            + PREFIX_NEW_TAG + "spacious";

    public static final String MESSAGE_SUCCESS = "Adds tags to listing %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "At least one of the new tags given already exist.";
    public static final String MESSAGE_INVALID_TAGS = "At least one of the tags given does not exist.";
    public static final String MESSAGE_DUPLICATE_TAGS_IN_LISTING = "At least one of the "
            + "tags given already exist in the listing.";

    private final Index index;
    private final Set<String> tagSet;
    private final Set<String> newTagSet;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddListingTagCommand(Index index, Set<String> tags,
                                Set<String> newTags) {
        this.index = index;
        tagSet = tags;
        newTagSet = newTags;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTags(tagSet)) {
            throw new CommandException(MESSAGE_INVALID_TAGS);
        }

        if (model.hasNewTags(newTagSet)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAGS);
        }
        model.addTags(newTagSet);

        List<Listing> lastShownList = model.getFilteredListingList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }

        Listing listingToAddTags = lastShownList.get(index.getZeroBased());

        if (tagExistInListing(listingToAddTags, tagSet, newTagSet)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAGS_IN_LISTING);
        }

        Listing listingWithTags = createListingWithTags(listingToAddTags, tagSet, newTagSet, model);
        Set<String> combinedTags = new HashSet<>(tagSet);
        combinedTags.addAll(newTagSet);
        model.setListing(listingToAddTags, listingWithTags);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(listingWithTags.getTags(), listingWithTags)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddListingTagCommand)) {
            return false;
        }

        AddListingTagCommand otherAddListingTagCommand = (AddListingTagCommand) other;
        return index.equals(otherAddListingTagCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Index", index)
                .toString();
    }

    private boolean tagExistInListing(Listing listing, Set<String> tagSet, Set<String> newTagSet) {
        TagRegistry tagRegistry = TagRegistry.of();
        Set<String> tagNames = new HashSet<>(newTagSet);
        tagNames.addAll(tagSet);
        Set<Tag> tags = new HashSet<>(listing.getTags());

        for (String tagName : tagNames) {
            Tag tag = tagRegistry.get(tagName);
            if (tags.contains(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new {@code PropertyPreference} with the specified tags and new tags.
     * The method combines the existing and new tags, creates {@code Tag} objects from the combined tags,
     * and associates them with a new {@code PropertyPreference}. The preference is then added to the model's
     * tag registry.
     */
    private Listing createListingWithTags(Listing listing, Set<String> tagSet,
                                                        Set<String> newTagSet, Model model) {
        TagRegistry tagRegistry = TagRegistry.of();
        Set<String> tagNames = new HashSet<>(newTagSet);
        tagNames.addAll(tagSet);
        Set<Tag> tags = new HashSet<>(listing.getTags());

        for (String tagName : tagNames) {
            Tag tag = tagRegistry.get(tagName);
            List<Listing> tagListings = new ArrayList<>(tag.getListings());
            tagListings.add(listing);
            List<PropertyPreference> preferences = new ArrayList<>(tag.getPropertyPreferences());
            Tag tagToAdd = new Tag(tagName, preferences, tagListings);
            tagRegistry.setTag(tag, tagToAdd);

            tags.add(tagToAdd);
        }

        UnitNumber unitNumber = listing.getUnitNumber();
        HouseNumber houseNumber = listing.getHouseNumber();
        PostalCode postalCode = listing.getPostalCode();
        PriceRange priceRange = listing.getPriceRange();
        PropertyName propertyName = listing.getPropertyName();

        return Listing.of(postalCode, unitNumber, houseNumber, priceRange,
                propertyName, tags, listing.getOwners());
    }
}
