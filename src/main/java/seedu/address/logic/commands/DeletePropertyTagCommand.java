package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagRegistry;

/**
 * Deletes tag(s) from a property (listing).
 */
public class DeletePropertyTagCommand extends Command {

    public static final String COMMAND_WORD = "deletePropertyTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes tags from a property identified "
            + "by the index number used in the displayed property list.\n"
            + "Parameters: PROPERTY_INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_TAG + "pet-friendly " + PREFIX_TAG + "pool";

    private final Index propertyIndex;
    private final Set<String> tagsToDelete;

    /**
     * Constructs a {@code DeletePropertyTagCommand}.
     *
     * @param propertyIndex The index of the property from which tags will be removed.
     * @param tagsToDelete  The set of tag names to be deleted.
     */
    public DeletePropertyTagCommand(Index propertyIndex, Set<String> tagsToDelete) {
        requireNonNull(propertyIndex);
        requireNonNull(tagsToDelete);

        this.propertyIndex = propertyIndex;
        this.tagsToDelete = tagsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Listing> lastShownList = model.getFilteredListingList();

        if (propertyIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }

        Listing listingToEdit = lastShownList.get(propertyIndex.getZeroBased());
        Set<Tag> listingTags = listingToEdit.getTags();

        // Check if all specified tags exist in the listing
        Set<String> notFoundTags = tagsToDelete.stream()
                .filter(inputTag -> listingTags.stream()
                        .noneMatch(listingTag -> listingTag.getTagName().equalsIgnoreCase(inputTag)))
                .collect(Collectors.toSet());

        if (!notFoundTags.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_TAG_NOT_FOUND, notFoundTags));
        }

        Set<Tag> updatedTags = new HashSet<>(listingTags);
        Set<Tag> removedTags = listingTags.stream()
                .filter(tag -> tagsToDelete.stream()
                        .anyMatch(inputTag -> inputTag.equalsIgnoreCase(tag.getTagName())))
                .collect(Collectors.toSet());

        updatedTags.removeAll(removedTags);

        // Removal association logic — using TagRegistry
        TagRegistry tagRegistry = TagRegistry.of();
        for (Tag tag : removedTags) {
            removeListingAssociationFromTagRegistry(tagRegistry, tag, listingToEdit);
        }

        Listing editedListing = Listing.of(
                listingToEdit.getPostalCode(),
                listingToEdit.getUnitNumber(),
                listingToEdit.getHouseNumber(),
                listingToEdit.getPriceRange(),
                listingToEdit.getPropertyName(),
                updatedTags,
                listingToEdit.getOwners()
        );

        model.setListing(listingToEdit, editedListing);

        return new CommandResult(String.format(Messages.MESSAGE_DELETE_PROPERTY_TAG_SUCCESS,
                listingToEdit.getPostalCode(), tagsToDelete));
    }

    /**
     * Removes the listing association from a tag by using the TagRegistry.
     *
     * @param tagRegistry    The TagRegistry instance.
     * @param targetTag      The tag whose association is being removed.
     * @param listingToRemove The listing to disassociate.
     */
    private void removeListingAssociationFromTagRegistry(TagRegistry tagRegistry, Tag targetTag, Listing
            listingToRemove) {
        for (Tag tag : tagRegistry) {
            if (tag.getTagName().equalsIgnoreCase(targetTag.getTagName())) {
                List<Listing> updatedListings = new ArrayList<>(tag.getListings());
                updatedListings.remove(listingToRemove);
                Tag editedTag = new Tag(tag.getTagName(), tag.getPropertyPreferences(), updatedListings);
                tagRegistry.setTag(tag, editedTag);
                return;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeletePropertyTagCommand
                && propertyIndex.equals(((DeletePropertyTagCommand) other).propertyIndex)
                && tagsToDelete.equals(((DeletePropertyTagCommand) other).tagsToDelete));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("propertyIndex", propertyIndex)
                .add("tagsToDelete", tagsToDelete)
                .toString();
    }
}
