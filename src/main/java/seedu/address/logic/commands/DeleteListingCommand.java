package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes a {@code Listing} from the address book.
 * The {@code Listing} is identified using it's displayed index.
 */
public class DeleteListingCommand extends Command {

    public static final String COMMAND_WORD = "deleteListing";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the listing identified by the index number used in the displayed listing list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Listing: %1$s";

    private final Index targetListingIndex;

    /**
     * Creates a {@code DeleteListingCommand} to delete the specified {@code Listing}.
     *
     * @param targetListingIndex The index of the listing in the filtered listing list to be deleted.
     */
    public DeleteListingCommand(Index targetListingIndex) {
        requireNonNull(targetListingIndex);

        this.targetListingIndex = targetListingIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Listing toDelete = CommandUtil.getValidatedListing(model, targetListingIndex, MESSAGE_USAGE);

        removeListingOwners(toDelete, model);
        removeListingFromTags(toDelete, model);

        model.deleteListing(toDelete);
        model.resetAllLists();

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(toDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteListingCommand)) {
            return false;
        }

        DeleteListingCommand otherDeleteCommand = (DeleteListingCommand) other;
        return targetListingIndex.equals(otherDeleteCommand.targetListingIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetListingIndex", targetListingIndex)
                .toString();
    }

    private void removeListingOwners(Listing toDelete, Model model) {
        List<Person> owners = new ArrayList<>(toDelete.getOwners());
        for (Person owner : owners) {
            owner.removeListing(toDelete);
            model.setPerson(owner, owner);
        }
    }

    private void removeListingFromTags(Listing toDelete, Model model) {

        Set<Tag> tags = new HashSet<>(toDelete.getTags());

        for (Tag tag: tags) {
            tag.removeListing(toDelete);
            model.setTag(tag, tag);
        }
    }
}
