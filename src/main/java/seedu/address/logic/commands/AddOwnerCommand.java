package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;

/**
 * Adds a {@code Person} as an owner of {@code Listing} in the address book.
 * The {@code Person} and {@code Listing} are identified using their respective displayed index.
 */
public class AddOwnerCommand extends Command {
    public static final String COMMAND_WORD = "addOwner";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to a listing as its owner"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + "LISTING_INDEX (must be a positive integer)"
            + "Example: " + COMMAND_WORD + " 2 1";

    public static final String MESSAGE_SUCCESS = "Listing %1$s";
    public static final String MESSAGE_OWNER_ALREADY_IN_LISTING = "This person is already an owner of this listing"
            + "\n%1$s";

    private final Index targetPersonIndex;
    private final Index targetListingIndex;

    /**
     * Creates an {@code AddOwnerCommand} to add the specified {@code Listing} to the specified {@code Person}.
     *
     * @param targetPersonIndex The index of the person in the filtered person list to add as an owner.
     * @param targetListingIndex The index of the listing in the filtered listing list to add to.
     */
    public AddOwnerCommand(Index targetPersonIndex, Index targetListingIndex) {
        this.targetPersonIndex = targetPersonIndex;
        this.targetListingIndex = targetListingIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getSortedFilteredPersonList();
        if (targetPersonIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, MESSAGE_USAGE));
        }
        Person personToAddListing = lastShownPersonList.get(targetPersonIndex.getZeroBased());

        List<Listing> lastShownListingList = model.getSortedFilteredListingList();
        if (targetListingIndex.getZeroBased() >= lastShownListingList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX, MESSAGE_USAGE));
        }
        Listing listing = lastShownListingList.get(targetListingIndex.getZeroBased());

        if (listing.getOwners().contains(personToAddListing)) {
            throw new CommandException(String.format(MESSAGE_OWNER_ALREADY_IN_LISTING, MESSAGE_USAGE));
        }

        listing.addOwner(personToAddListing);
        personToAddListing.addListing(listing);

        model.setPerson(personToAddListing, personToAddListing);
        model.setListing(listing, listing);
        model.resetAllLists();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToAddListing,
                listing)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOwnerCommand)) {
            return false;
        }

        AddOwnerCommand otherAddOwnerCommand = (AddOwnerCommand) other;
        return targetPersonIndex.equals(otherAddOwnerCommand.targetPersonIndex)
                && targetListingIndex.equals(otherAddOwnerCommand.targetListingIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
