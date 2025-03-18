package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag from a property (listing).
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

        Set<Tag> updatedTags = new HashSet<>(listingToEdit.getTags());
        Set<Tag> removedTags = new HashSet<>();
        Set<String> removedTagNames = new HashSet<>(); // Preserve original case for output

        for (Tag tag : listingToEdit.getTags()) {
            for (String tagToDelete : tagsToDelete) {
                if (tagToDelete.equalsIgnoreCase(tag.getTagName())) {
                    removedTags.add(tag);
                    removedTagNames.add(tagToDelete); // Store original case
                }
            }
        }

        if (removedTags.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_TAG_NOT_FOUND, tagsToDelete));
        }

        updatedTags.removeAll(removedTags);

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

        // Use the original case versions of the tag names
        return new CommandResult(String.format(Messages.MESSAGE_DELETE_PROPERTY_TAG_SUCCESS,
                listingToEdit.getPostalCode(), removedTagNames));
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
