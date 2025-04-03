package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.tag.Tag;

/**
 * Deletes {@code Tag}(s) from a {@code Listing} in the address book.
 * The {@code Listing} is identified using it's displayed index.
 */
public class DeleteListingTagCommand extends Command {

    public static final String COMMAND_WORD = "deleteListingTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes tags from a listing."
            + "\nParameters: "
            + "LISTING_INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]..."
            + "\nExample: "
            + COMMAND_WORD + " 3 "
            + PREFIX_TAG + "pet-friendly "
            + PREFIX_TAG + "pool";

    private final Index targetListingIndex;
    private final Set<String> tagsToDelete;

    /**
     * Creates a {@code DeleteListingTagCommand} to delete the specified {@code Tag}(s) from the specified
     * {@code Listing}.
     *
     * @param targetListingIndex The index of the listing in the filtered listing list to delete tags from.
     * @param tagsToDelete The set of tag to be deleted from the listing.
     */
    public DeleteListingTagCommand(Index targetListingIndex, Set<String> tagsToDelete) {
        requireNonNull(targetListingIndex);
        requireAllNonNull(tagsToDelete);

        this.targetListingIndex = targetListingIndex;
        this.tagsToDelete = tagsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Listing listingToEdit = CommandUtil.getValidatedListing(model, targetListingIndex, MESSAGE_USAGE);

        Set<Tag> deletedTags = new HashSet<>();

        // Modify listing and tag registry in place
        for (String tagName : tagsToDelete) {
            Tag tag = new Tag(tagName, new ArrayList<>(), new ArrayList<>());
            if (!model.hasTag(tagName)) {
                throw new CommandException(String.format(Messages.MESSAGE_TAG_DOES_NOT_EXIST, tagName,
                        MESSAGE_USAGE));
            }
            Tag tagToRemove = model.getTag(tagName);
            if (!listingToEdit.getTags().contains(tag)) {
                throw new CommandException(String.format(Messages.MESSAGE_TAG_NOT_FOUND_IN_LISTING, tagName,
                        MESSAGE_USAGE));
            }
            deletedTags.add(tagToRemove);
        }

        for (Tag tag : deletedTags) {
            listingToEdit.removeTag(tag);
            model.setListing(listingToEdit, listingToEdit);
            tag.removeListing(listingToEdit);
            model.setTag(tag, tag);
            deletedTags.add(tag);
        }

        model.resetAllLists();

        return new CommandResult(String.format(Messages.MESSAGE_DELETE_LISTING_TAG_SUCCESS,
                listingToEdit.getPostalCode(), Messages.format(deletedTags)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteListingTagCommand
                && targetListingIndex.equals(((DeleteListingTagCommand) other).targetListingIndex)
                && tagsToDelete.equals(((DeleteListingTagCommand) other).tagsToDelete));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetListingIndex", targetListingIndex)
                .add("tagsToDelete", tagsToDelete)
                .toString();
    }
}
