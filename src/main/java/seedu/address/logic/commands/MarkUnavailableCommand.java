package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;

/**
 * Mark a {@code Listing} as unavailable in the address book.
 * The {@code Listing} is identified using it's displayed index.
 */
public class MarkUnavailableCommand extends Command {
    public static final String COMMAND_WORD = "markUnavailable";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the listing as unavailable. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_MARK_UNAVAILABLE_SUCCESS = "Marked Listing as unavailable: %1$s";

    private final Index targetListingIndex;

    /**
     * Creates a {@code MarkUnavailableCommand} to mark the specified {@code Listing} as unavailable.
     *
     * @param targetListingIndex The index of the listing in the filtered listing list to mark as unavailable.
     */
    public MarkUnavailableCommand(Index targetListingIndex) {
        requireNonNull(targetListingIndex);

        this.targetListingIndex = targetListingIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Listing toMarkUnavailable = CommandUtil.getValidatedListing(model, targetListingIndex, MESSAGE_USAGE);
        toMarkUnavailable.markUnavailable();
        model.setListing(toMarkUnavailable, toMarkUnavailable);

        model.resetAllLists();

        return new CommandResult(String.format(MESSAGE_MARK_UNAVAILABLE_SUCCESS,
                Messages.format(toMarkUnavailable.getAvailability(), toMarkUnavailable)));
    }
}
