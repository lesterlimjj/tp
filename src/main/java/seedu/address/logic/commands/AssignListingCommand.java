package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PropertyPreference;

/**
 * Assigns a listing to a person.
 */
public class AssignListingCommand extends Command {
    public static final String COMMAND_WORD = "assignListing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a listing (to sell) to a person "
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + "LISTING_INDEX (must be a positive integer)";

    public static final String MESSAGE_SUCCESS = "Listing %1$s";

    private final Index personIndex;
    private final Index listingIndex;
    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AssignListingCommand(Index personIndex, Index propertyIndex) {
        this.personIndex = personIndex;
        this.listingIndex = propertyIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToAddListing = lastShownPersonList.get(personIndex.getZeroBased());

        List<Listing> lastShownListingList = model.getFilteredListingList();
        if (listingIndex.getZeroBased() >= lastShownListingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Listing listing = lastShownListingList.get(listingIndex.getZeroBased());

        Person personWithListingAdded = createPersonWithAddedListing(personToAddListing, listing);

        model.setPerson(personToAddListing, personWithListingAdded);
        model.setListing(listing, listing);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personWithListingAdded, listing)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignListingCommand)) {
            return false;
        }

        AssignListingCommand otherAssignListingCommand = (AssignListingCommand) other;
        return personIndex.equals(otherAssignListingCommand.personIndex)
                && listingIndex.equals(otherAssignListingCommand.listingIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createPersonWithAddedListing(Person person, Listing listing) {
        assert person != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        List<PropertyPreference> propertyPreferences = new ArrayList<>(person.getPropertyPreferences());
        List<Listing> listings = new ArrayList<>(person.getListings());
        listings.add(listing);

        return new Person(name, phone, email, propertyPreferences, listings);
    }

}
