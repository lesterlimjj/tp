package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;

/**
 * Deletes a {@code Person} from a {@code Listing}, where the {@code Person} is an owner of the {@code Listing}, in the
 * address book.
 * The {@code Person} is identified using it's displayed index within the {@code Listing}'s owners,
 * and the {@code Listing} is identified using it's displayed index.
 */
public class DeleteOwnerCommand extends Command {

    public static final String COMMAND_WORD = "deleteOwner";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an owner from a listing."
            + "\nParameters: "
            + "LISTING_INDEX (must be a positive integer) OWNER_INDEX (must be a positive integer)"
            + "\nExample: "
            + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_OWNER_SUCCESS = "Deleted Owner: %1$s";

    private final Index targetListingIndex;
    private final Index targetOwnerIndex;

    /**
     * Creates a {@code DeleteOwnerCommand} to delete the specified {@code Person} from the specified {@code Listing}.
     *
     * @param targetListingIndex The index of the listing in the filtered listing list to delete the owner from.
     * @param targetOwnerIndex The index of the owner in the listing's owner list to delete.
     */
    public DeleteOwnerCommand(Index targetListingIndex, Index targetOwnerIndex) {
        requireAllNonNull(targetListingIndex, targetOwnerIndex);

        this.targetListingIndex = targetListingIndex;
        this.targetOwnerIndex = targetOwnerIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Listing targetListing = CommandUtil.getValidatedListing(model, targetListingIndex, MESSAGE_USAGE);
        Person ownerToDelete = CommandUtil.getValidatedOwner(targetListing, targetOwnerIndex, MESSAGE_USAGE);

        targetListing.removeOwner(ownerToDelete);
        ownerToDelete.removeListing(targetListing);

        model.setPerson(ownerToDelete, ownerToDelete);
        model.setListing(targetListing, targetListing);
        model.resetAllLists();

        return new CommandResult(String.format(MESSAGE_DELETE_OWNER_SUCCESS, Messages.format(ownerToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteOwnerCommand)) {
            return false;
        }

        DeleteOwnerCommand otherDeleteCommand = (DeleteOwnerCommand) other;
        return targetListingIndex.equals(otherDeleteCommand.targetListingIndex)
                && targetOwnerIndex.equals(otherDeleteCommand.targetOwnerIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetListingIndex", targetListingIndex)
                .add("targetOwnerIndex", targetOwnerIndex)
                .toString();
    }
}
