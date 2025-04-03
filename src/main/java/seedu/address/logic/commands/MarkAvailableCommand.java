package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;

/**
 * Mark a {@code Listing} as available in the address book.
 * The {@code Listing} is identified using it's displayed index.
 */
public class MarkAvailableCommand extends Command {
    public static final String COMMAND_WORD = "markAvailable";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks Listing as available."
            + "\nParameters: "
            + "LISTING_INDEX (must be a positive integer) "
            + "Example: "
            + COMMAND_WORD + " 1";
    public static final String MESSAGE_MARK_AVAILABLE_SUCCESS = "Marked Listing as available: %1$s";

    private final Index targetListingIndex;

    /**
     * Creates a {@code MarkAvailableCommand} to mark the specified {@code Listing} as available.
     *
     * @param targetListingIndex The index of the listing in the filtered listing list to mark as available.
     */
    public MarkAvailableCommand(Index targetListingIndex) {
        requireNonNull(targetListingIndex);

        this.targetListingIndex = targetListingIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Listing toMarkAvailable = CommandUtil.getValidatedListing(model, targetListingIndex, MESSAGE_USAGE);
        toMarkAvailable.markAvailable();
        model.setListing(toMarkAvailable, toMarkAvailable);

        model.resetAllLists();

        return new CommandResult(String.format(MESSAGE_MARK_AVAILABLE_SUCCESS,
                Messages.format(toMarkAvailable.getAvailability(), toMarkAvailable)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetListingIndex", targetListingIndex)
                .toString();
    }
}
