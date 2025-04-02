package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.search.predicates.ListingContainsOwnerPredicate;

/**
 * Lists all {@code Listing}(s) that are owned by a specified {@code Person}.
 * The {@code Person} is identified using it's displayed index.
 */
public class SearchOwnerListingCommand extends Command {

    public static final String COMMAND_WORD = "searchOwnerListing";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all properties owned by a person identified by the index number "
            + "used in the displayed person list.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SEARCH_SELLER_PROPERTY_SUCCESS = "Searched Properties of Person: %1$s";

    private final Index targetPersonIndex;

    /**
     * Creates a {@code SearchOwnerListingCommand} to list {@code Listing}(s) that are owned by a specified
     * {@code Person}.
     *
     * @param targetPersonIndex The index of the person in the filtered person list to search by.
     */
    public SearchOwnerListingCommand(Index targetPersonIndex) {
        requireNonNull(targetPersonIndex);

        this.targetPersonIndex = targetPersonIndex;
    }

    /**
     * Executes the search command and updates the filtered listing list in the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult with the number of matches found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getSortedFilteredPersonList();
        System.out.println(lastShownList);

        Person targetPerson = CommandUtil.getValidatedPerson(model, targetIndex, MESSAGE_USAGE);

        searchSellerProperty(model, targetPerson);

        return new CommandResult(String.format(MESSAGE_SEARCH_SELLER_PROPERTY_SUCCESS, Messages.format(targetPerson)));
    }

    private void searchSellerProperty(Model model, Person targetPerson) {
        requireAllNonNull(model, targetPerson);
        model.resetAllLists();

        Predicate<Listing> predicate = new ListingContainsOwnerPredicate(targetPerson);
        model.updateFilteredListingList(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePersonCommand)) {
            return false;
        }

        SearchOwnerListingCommand otherDeletePersonCommand = (SearchOwnerListingCommand) other;
        return targetPersonIndex.equals(otherDeletePersonCommand.targetPersonIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonIndex", targetPersonIndex)
                .toString();
    }
}
